package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.CollectionUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.system.pojo.entity.Role;
import org.springblade.modules.system.pojo.entity.RoleMenu;
import org.springblade.modules.system.pojo.entity.RoleScope;
import org.springblade.modules.system.mapper.RoleMapper;
import org.springblade.modules.system.service.IRoleMenuService;
import org.springblade.modules.system.service.IRoleScopeService;
import org.springblade.modules.system.service.IRoleService;
import org.springblade.modules.system.pojo.vo.RoleVO;
import org.springblade.modules.system.wrapper.RoleWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springblade.common.constant.CommonConstant.API_SCOPE_CATEGORY;
import static org.springblade.common.constant.CommonConstant.DATA_SCOPE_CATEGORY;

/**
 * Service implementation class
 *
 * @author Chill
 */
//@Master
@Service
@Validated
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

	private final IRoleMenuService roleMenuService;
	private final IRoleScopeService roleScopeService;

	@Override
	public IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role) {
		return page.setRecords(baseMapper.selectRolePage(page, role));
	}

	@Override
	public List<RoleVO> tree(String tenantId) {
		String userRole = AuthUtil.getUserRole();
		String excludeRole = null;
		if (!CollectionUtil.contains(Func.toStrArray(userRole), RoleConstant.ADMIN) && !CollectionUtil.contains(Func.toStrArray(userRole), RoleConstant.ADMINISTRATOR)) {
			excludeRole = RoleConstant.ADMIN;
		}
		return ForestNodeMerger.merge(baseMapper.tree(tenantId, excludeRole));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean grant(@NotEmpty List<Long> roleIds, List<Long> menuIds, List<Long> dataScopeIds, List<Long> apiScopeIds) {
		return grantRoleMenu(roleIds, menuIds) && grantDataScope(roleIds, dataScopeIds) && grantApiScope(roleIds, apiScopeIds);
	}

	private boolean grantRoleMenu(List<Long> roleIds, List<Long> menuIds) {
		// Prevent unauthorized configuration of super-administrative roles
		Long administratorCount = baseMapper.selectCount(Wrappers.<Role>query().lambda().eq(Role::getRoleAlias, RoleConstant.ADMINISTRATOR).in(Role::getId, roleIds));
		if (!AuthUtil.isAdministrator() && administratorCount > 0L) {
			throw new ServiceException("No permission to configure super management role!");
		}
		// Prevent unauthorized configuration of administrator roles
		Long adminCount = baseMapper.selectCount(Wrappers.<Role>query().lambda().eq(Role::getRoleAlias, RoleConstant.ADMIN).in(Role::getId, roleIds));
		if (!AuthUtil.isAdmin() && adminCount > 0L) {
			throw new ServiceException("No permission to configure administrator role!");
		}
		// Delete the role configuration menu collection
		roleMenuService.remove(Wrappers.<RoleMenu>update().lambda().in(RoleMenu::getRoleId, roleIds));
		// Assembly configuration
		List<RoleMenu> roleMenus = new ArrayList<>();
		roleIds.forEach(roleId -> menuIds.forEach(menuId -> {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenus.add(roleMenu);
		}));
		// New configuration
		roleMenuService.saveBatch(roleMenus);
		// Recursively set subordinate role menu collections
		recursionRoleMenu(roleIds, menuIds);
		return true;
	}

	private void recursionRoleMenu(List<Long> roleIds, List<Long> menuIds) {
		roleIds.forEach(roleId -> baseMapper.selectList(Wrappers.<Role>query().lambda().eq(Role::getParentId, roleId)).forEach(role -> {
			List<RoleMenu> roleMenuList = roleMenuService.list(Wrappers.<RoleMenu>query().lambda().eq(RoleMenu::getRoleId, role.getId()));
			// Child nodes filter out the menu collection deleted by the parent node
			List<Long> collectRoleMenuIds = roleMenuList.stream().map(RoleMenu::getMenuId).filter(menuId -> !menuIds.contains(menuId)).collect(Collectors.toList());
			if (collectRoleMenuIds.size() > 0) {
				// Delete menu collections outside the permissions of child nodes
				roleMenuService.remove(Wrappers.<RoleMenu>update().lambda().eq(RoleMenu::getRoleId, role.getId()).in(RoleMenu::getMenuId, collectRoleMenuIds));
				// Recursively set subordinate role menu collections
				recursionRoleMenu(Collections.singletonList(role.getId()), menuIds);
			}
		}));
	}

	private boolean grantDataScope(List<Long> roleIds, List<Long> dataScopeIds) {
		// Delete the data permission set configured by the role
		roleScopeService.remove(Wrappers.<RoleScope>update().lambda().eq(RoleScope::getScopeCategory, DATA_SCOPE_CATEGORY).in(RoleScope::getRoleId, roleIds));
		// Assembly configuration
		List<RoleScope> roleDataScopes = new ArrayList<>();
		roleIds.forEach(roleId -> dataScopeIds.forEach(scopeId -> {
			RoleScope roleScope = new RoleScope();
			roleScope.setScopeCategory(DATA_SCOPE_CATEGORY);
			roleScope.setRoleId(roleId);
			roleScope.setScopeId(scopeId);
			roleDataScopes.add(roleScope);
		}));
		// New configuration
		roleScopeService.saveBatch(roleDataScopes);
		return true;
	}

	private boolean grantApiScope(List<Long> roleIds, List<Long> apiScopeIds) {
		// Delete the interface permission set configured by the role
		roleScopeService.remove(Wrappers.<RoleScope>update().lambda().eq(RoleScope::getScopeCategory, API_SCOPE_CATEGORY).in(RoleScope::getRoleId, roleIds));
		// Assembly configuration
		List<RoleScope> roleApiScopes = new ArrayList<>();
		roleIds.forEach(roleId -> apiScopeIds.forEach(scopeId -> {
			RoleScope roleScope = new RoleScope();
			roleScope.setScopeCategory(API_SCOPE_CATEGORY);
			roleScope.setScopeId(scopeId);
			roleScope.setRoleId(roleId);
			roleApiScopes.add(roleScope);
		}));
		// New configuration
		roleScopeService.saveBatch(roleApiScopes);
		return true;
	}

	@Override
	public String getRoleIds(String tenantId, String roleNames) {
		List<Role> roleList = baseMapper.selectList(Wrappers.<Role>query().lambda().eq(Role::getTenantId, tenantId).in(Role::getRoleName, Func.toStrList(roleNames)));
		if (roleList != null && roleList.size() > 0) {
			return roleList.stream().map(role -> Func.toStr(role.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getRoleNames(String roleIds) {
		return baseMapper.getRoleNames(Func.toLongArray(roleIds));
	}

	@Override
	public List<String> getRoleAliases(String roleIds) {
		return baseMapper.getRoleAliases(Func.toLongArray(roleIds));
	}

	@Override
	public boolean submit(Role role) {
		if (!AuthUtil.isAdministrator()) {
			if (Func.toStr(role.getRoleAlias()).equals(RoleConstant.ADMINISTRATOR)) {
				throw new ServiceException("No permission to create super management role! ");
			}
		}
		if (Func.isEmpty(role.getParentId())) {
			role.setTenantId(AuthUtil.getTenantId());
			role.setParentId(BladeConstant.TOP_PARENT_ID);
		}
		if (role.getParentId() > 0) {
			Role parent = getById(role.getParentId());
			if (Func.toLong(role.getParentId()) == Func.toLong(role.getId())) {
				throw new ServiceException("The parent node cannot select itself!");
			}
			role.setTenantId(parent.getTenantId());
		}
		role.setIsDeleted(BladeConstant.DB_NOT_DELETED);
		return saveOrUpdate(role);
	}

	@Override
	public List<RoleVO> search(String roleName, Long parentId) {
		String tenantId = AuthUtil.getTenantId();
		LambdaQueryWrapper<Role> queryWrapper = Wrappers.<Role>query().lambda();
		if (Func.isNotEmpty(roleName)) {
			queryWrapper.like(Role::getRoleName, roleName);
		}
		if (Func.isNotEmpty(parentId) && parentId > 0L) {
			queryWrapper.eq(Role::getParentId, parentId);
		}
		if (Func.isNotEmpty(tenantId)) {
			queryWrapper.eq(Role::getTenantId, tenantId);
		}
		List<Role> roleList = baseMapper.selectList(queryWrapper);
		return RoleWrapper.build().listNodeVO(roleList);
	}

	@Override
	public boolean removeRole(String ids) {
		Long cnt = baseMapper.selectCount(Wrappers.<Role>query().lambda().in(Role::getParentId, Func.toLongList(ids)));
		if (cnt > 0L) {
			throw new ServiceException("Please delete child nodes first!");
		}
		return removeByIds(Func.toLongList(ids));
	}

	@Override
	public List<Role> alias(String tenantId) {
		// Get all character data
		List<Role> roles = baseMapper.selectList(Wrappers.<Role>lambdaQuery().eq(Role::getTenantId, tenantId));

		// according to roleAlias Group roles
		Map<String, List<String>> aliasToNamesMap = roles.stream()
			.collect(Collectors.groupingBy(Role::getRoleAlias,
				Collectors.mapping(Role::getRoleName, Collectors.toList())));

		// Create a new role list, for each role roleName yes roleAlias followed by everything in parentheses roleName
		return aliasToNamesMap.entrySet().stream()
			// Filter out super administrator roles
			.filter(entry -> !StringUtil.equals(RoleConstant.ADMINISTRATOR, entry.getKey()))
			.map(entry -> {
				String roleAlias = entry.getKey();
				List<String> names = entry.getValue();
				String namesConcat = names.stream().distinct().collect(Collectors.joining(StringPool.COMMA + StringPool.SPACE));
				Role role = new Role();
				role.setRoleAlias(roleAlias);
				role.setRoleName(roleAlias + StringPool.SPACE + StringPool.LEFT_BRACKET + namesConcat + StringPool.RIGHT_BRACKET);
				return role;
			}).collect(Collectors.toList());
	}
}
