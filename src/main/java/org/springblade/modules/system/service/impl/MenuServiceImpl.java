/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2018-2099, https://bladex.cn. All rights reserved.
 * <p>
 * Use of this software is governed by the Commercial License Agreement
 * obtained after purchasing a license from BladeX.
 * <p>
 * 1. This software is for development use only under a valid license
 * from BladeX.
 * <p>
 * 2. Redistribution of this software's source code to any third party
 * without a commercial license is strictly prohibited.
 * <p>
 * 3. Licensees may copyright their own code but cannot use segments
 * from this software for such purposes. Copyright of this software
 * remains with BladeX.
 * <p>
 * Using this software signifies agreement to this License, and the software
 * must not be used for illegal purposes.
 * <p>
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY. The author is
 * not liable for any claims arising from secondary or illegal development.
 * <p>
 * Author: Chill Zhuang (bladejava@qq.com)
 */
package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.common.cache.SysCache;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.node.TreeNode;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.system.mapper.MenuMapper;
import org.springblade.modules.system.pojo.dto.MenuDTO;
import org.springblade.modules.system.pojo.entity.*;
import org.springblade.modules.system.pojo.vo.MenuVO;
import org.springblade.modules.system.service.IMenuService;
import org.springblade.modules.system.service.IRoleMenuService;
import org.springblade.modules.system.service.IRoleScopeService;
import org.springblade.modules.system.service.ITopMenuSettingService;
import org.springblade.modules.system.wrapper.MenuWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springblade.common.constant.CommonConstant.API_SCOPE_CATEGORY;
import static org.springblade.common.constant.CommonConstant.DATA_SCOPE_CATEGORY;
import static org.springblade.core.cache.constant.CacheConstant.MENU_CACHE;

/**
 * Service implementation class
 *
 * @author Chill
 */
//@Master
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	private final IRoleMenuService roleMenuService;
	private final IRoleScopeService roleScopeService;
	private final ITopMenuSettingService topMenuSettingService;
	private final static String PARENT_ID = "parentId";
	private final static Integer MENU_CATEGORY = 1;

	@Override
	public List<MenuVO> lazyList(Long parentId, Map<String, Object> param) {
		if (Func.isEmpty(Func.toStr(param.get(PARENT_ID)))) {
			parentId = null;
		}
		return baseMapper.lazyList(parentId, param);
	}

	@Override
	public List<MenuVO> lazyMenuList(Long parentId, Map<String, Object> param) {
		if (Func.isEmpty(Func.toStr(param.get(PARENT_ID)))) {
			parentId = null;
		}
		return baseMapper.lazyMenuList(parentId, param);
	}

	@Override
	public List<MenuVO> routes(String roleId, Long topMenuId) {
		if (StringUtil.isBlank(roleId)) {
			return null;
		}
		List<Menu> allMenus = baseMapper.allMenu();
		List<Menu> roleMenus;
		// If the super administrator and the request is not the top menu, the entire menu will be returned.
		if (AuthUtil.isAdministrator() && Func.isEmpty(topMenuId)) {
			roleMenus = allMenus;
		}
		// If the request is not a super administrator and is not the top menu, the corresponding role permission menu will be returned.
		else if (!AuthUtil.isAdministrator() && Func.isEmpty(topMenuId)) {
			// Role configuration corresponding menu
			List<Menu> roleIdMenus = baseMapper.roleMenuByRoleId(Func.toLongList(roleId));
			// Reverse recursive character menu all parents
			List<Menu> routes = new LinkedList<>(roleIdMenus);
			roleIdMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
			roleMenus = tenantPackageMenu(routes);
		}
		// The top menu request returns the corresponding role permissions menu
		else {
			// Role configuration corresponding menu
			List<Menu> roleIdMenus = baseMapper.roleMenuByRoleId(Func.toLongList(roleId));
			// Reverse recursive character menu all parents
			List<Menu> routes = new LinkedList<>(roleIdMenus);
			roleIdMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
			// Top configuration corresponding menu
			List<Menu> topIdMenus = baseMapper.roleMenuByTopMenuId(topMenuId);
			// Filter the permission menu corresponding to matching roles
			roleMenus = tenantPackageMenu(topIdMenus.stream().filter(x ->
				routes.stream().anyMatch(route -> route.getId().longValue() == x.getId().longValue())
			).collect(Collectors.toList()));
		}
		return buildRoutes(allMenus, roleMenus);
	}

	@Override
	public List<MenuVO> routesExt(String roleId, Long topMenuId) {
		if (StringUtil.isBlank(roleId)) {
			return null;
		}
		List<Menu> allMenus = baseMapper.allMenuExt();
		List<Menu> roleMenus = baseMapper.roleMenuExt(Func.toLongList(roleId), topMenuId);
		return buildRoutes(allMenus, roleMenus);
	}

	private List<MenuVO> buildRoutes(List<Menu> allMenus, List<Menu> roleMenus) {
		List<Menu> routes = new LinkedList<>(roleMenus);
		roleMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
		routes.sort(Comparator.comparing(Menu::getSort));
		MenuWrapper menuWrapper = new MenuWrapper();
		List<Menu> collect = routes.stream().filter(x -> Func.equals(x.getCategory(), 1)).collect(Collectors.toList());
		return menuWrapper.listNodeVO(collect);
	}

	private void recursion(List<Menu> allMenus, List<Menu> routes, Menu roleMenu) {
		Optional<Menu> menu = allMenus.stream().filter(x -> Func.equals(x.getId(), roleMenu.getParentId())).findFirst();
		if (menu.isPresent() && !routes.contains(menu.get())) {
			routes.add(menu.get());
			recursion(allMenus, routes, menu.get());
		}
	}

	@Override
	public List<MenuVO> buttons(String roleId) {
		List<Menu> buttons = (AuthUtil.isAdministrator()) ? baseMapper.allButtons() : baseMapper.buttons(Func.toLongList(roleId));
		MenuWrapper menuWrapper = new MenuWrapper();
		return menuWrapper.listNodeVO(buttons);
	}

	@Override
	public List<TreeNode> tree() {
		return ForestNodeMerger.merge(baseMapper.tree());
	}

	@Override
	public List<TreeNode> grantTree(BladeUser user) {
		List<TreeNode> menuTree = user.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? baseMapper.grantTree() : baseMapper.grantTreeByRole(Func.toLongList(user.getRoleId()));
		return ForestNodeMerger.merge(tenantPackageTree(menuTree, user.getTenantId()));
	}

	@Override
	public List<TreeNode> grantTopTree(BladeUser user) {
		List<TreeNode> menuTree = user.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? baseMapper.grantTopTree() : baseMapper.grantTopTreeByRole(Func.toLongList(user.getRoleId()));
		return ForestNodeMerger.merge(tenantPackageTree(menuTree, user.getTenantId()));
	}

	/**
	 * Tenant menu permissions custom filtering
	 */
	private List<TreeNode> tenantPackageTree(List<TreeNode> menuTree, String tenantId) {
		TenantPackage tenantPackage = SysCache.getTenantPackage(tenantId);
		if (!AuthUtil.isAdministrator() && Func.isNotEmpty(tenantPackage) && tenantPackage.getId() > 0L) {
			List<Long> menuIds = Func.toLongList(tenantPackage.getMenuId());
			// Filter out the intersection set of the two menus
			List<TreeNode> collect = menuTree.stream().filter(x -> menuIds.contains(x.getId())).toList();
			// Create a recursive base collection
			List<TreeNode> packageTree = new LinkedList<>(collect);
			// Recursively filter out all parents of a menu collection
			collect.forEach(treeNode -> recursionParent(menuTree, packageTree, treeNode));
			// Recursively filter out all children of a menu collection
			collect.forEach(treeNode -> recursionChild(menuTree, packageTree, treeNode));
			// Combined together to return the final set
			return packageTree;
		}
		return menuTree;
	}

	/**
	 * Parent node recursion
	 */
	public void recursionParent(List<TreeNode> menuTree, List<TreeNode> packageTree, TreeNode treeNode) {
		Optional<TreeNode> node = menuTree.stream().filter(x -> Func.equals(x.getId(), treeNode.getParentId())).findFirst();
		if (node.isPresent() && !packageTree.contains(node.get())) {
			packageTree.add(node.get());
			recursionParent(menuTree, packageTree, node.get());
		}
	}

	/**
	 * Child node recursion
	 */
	public void recursionChild(List<TreeNode> menuTree, List<TreeNode> packageTree, TreeNode treeNode) {
		List<TreeNode> nodes = menuTree.stream().filter(x -> Func.equals(x.getParentId(), treeNode.getId())).collect(Collectors.toList());
		nodes.forEach(node -> {
			if (!packageTree.contains(node)) {
				packageTree.add(node);
				recursionChild(menuTree, packageTree, node);
			}
		});
	}

	/**
	 * Tenant menu permissions custom filtering
	 */
	private List<Menu> tenantPackageMenu(List<Menu> menu) {
		TenantPackage tenantPackage = SysCache.getTenantPackage(AuthUtil.getTenantId());
		if (Func.isNotEmpty(tenantPackage) && Func.isNotEmpty(tenantPackage.getId()) && tenantPackage.getId() > 0L) {
			List<Long> menuIds = Func.toLongList(tenantPackage.getMenuId());
			menu = menu.stream().filter(x -> menuIds.contains(x.getId())).collect(Collectors.toList());
		}
		return menu;
	}

	@Override
	public List<TreeNode> grantDataScopeTree(BladeUser user) {
		return ForestNodeMerger.merge(user.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? baseMapper.grantDataScopeTree() : baseMapper.grantDataScopeTreeByRole(Func.toLongList(user.getRoleId())));
	}

	@Override
	public List<TreeNode> grantApiScopeTree(BladeUser user) {
		return ForestNodeMerger.merge(user.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? baseMapper.grantApiScopeTree() : baseMapper.grantApiScopeTreeByRole(Func.toLongList(user.getRoleId())));
	}

	@Override
	public List<String> roleTreeKeys(String roleIds) {
		List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>query().lambda().in(RoleMenu::getRoleId, Func.toLongList(roleIds)));
		return roleMenus.stream().map(roleMenu -> Func.toStr(roleMenu.getMenuId())).collect(Collectors.toList());
	}

	@Override
	public List<String> topTreeKeys(String topMenuIds) {
		List<TopMenuSetting> settings = topMenuSettingService.list(Wrappers.<TopMenuSetting>query().lambda().in(TopMenuSetting::getTopMenuId, Func.toLongList(topMenuIds)));
		return settings.stream().map(setting -> Func.toStr(setting.getMenuId())).collect(Collectors.toList());
	}

	@Override
	public List<String> dataScopeTreeKeys(String roleIds) {
		List<RoleScope> roleScopes = roleScopeService.list(Wrappers.<RoleScope>query().lambda().eq(RoleScope::getScopeCategory, DATA_SCOPE_CATEGORY).in(RoleScope::getRoleId, Func.toLongList(roleIds)));
		return roleScopes.stream().map(roleScope -> Func.toStr(roleScope.getScopeId())).collect(Collectors.toList());
	}

	@Override
	public List<String> apiScopeTreeKeys(String roleIds) {
		List<RoleScope> roleScopes = roleScopeService.list(Wrappers.<RoleScope>query().lambda().eq(RoleScope::getScopeCategory, API_SCOPE_CATEGORY).in(RoleScope::getRoleId, Func.toLongList(roleIds)));
		return roleScopes.stream().map(roleScope -> Func.toStr(roleScope.getScopeId())).collect(Collectors.toList());
	}

	@Override
	@Cacheable(cacheNames = MENU_CACHE, key = "'auth:routes:' + #user.roleId")
	public List<Kv> authRoutes(BladeUser user) {
		List<MenuDTO> routes = baseMapper.authRoutes(Func.toLongList(user.getRoleId()));
		List<Kv> list = new ArrayList<>();
		routes.forEach(route -> list.add(Kv.create().set(route.getPath(), Kv.create().set("authority", Func.toStrArray(route.getAlias())))));
		return list;
	}

	@Override
	public boolean removeMenu(String ids) {
		Long cnt = baseMapper.selectCount(Wrappers.<Menu>query().lambda().in(Menu::getParentId, Func.toLongList(ids)));
		if (cnt > 0L) {
			throw new ServiceException("Please delete child nodes first!");
		}
		return removeByIds(Func.toLongList(ids));
	}

	@Override
	public boolean submit(Menu menu) {
		boolean isNewMenu = menu.getId() == null;

		// verifycodeandnameuniqueness
		LambdaQueryWrapper<Menu> menuQueryWrapper = Wrappers.<Menu>lambdaQuery()
			.and(!isNewMenu, w -> w.ne(Menu::getId, menu.getId()))
			.and(w -> w.eq(Menu::getCode, menu.getCode())
				.or(o -> o.eq(Menu::getName, menu.getName()).eq(Menu::getCategory, MENU_CATEGORY)));

		if (baseMapper.selectCount(menuQueryWrapper) > 0L) {
			throw new ServiceException("Menu name or number already exists!");
		}

		// verifypathuniqueness
		if (Func.isNotBlank(menu.getPath())) {
			LambdaQueryWrapper<Menu> pathQueryWrapper = Wrappers.<Menu>lambdaQuery()
				.eq(Menu::getPath, menu.getPath())
				.ne(!isNewMenu, Menu::getId, menu.getId());

			if (baseMapper.selectCount(pathQueryWrapper) > 0L) {
				throw new ServiceException("Menu path already exists!");
			}
		}

		// Process parent node
		if (menu.getParentId() == null) {
			menu.setParentId(BladeConstant.TOP_PARENT_ID);
		}

		// Verify parent node type(When a new node is added or the parent node is changed)
		if (isNewMenu || menu.getParentId() != null) {
			Menu parentMenu = baseMapper.selectById(menu.getParentId());
			if (parentMenu != null && parentMenu.getCategory() != 1) {
				throw new ServiceException("The parent node can only select the menu type!");
			}
		}

		menu.setIsDeleted(BladeConstant.DB_NOT_DELETED);
		return saveOrUpdate(menu);
	}

}
