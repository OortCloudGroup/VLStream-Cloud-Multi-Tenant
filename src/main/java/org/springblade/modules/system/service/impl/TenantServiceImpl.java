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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.literule.engine.RuleEngineExecutor;
import org.springblade.core.literule.provider.LiteRuleResponse;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.mp.enums.StatusType;
import org.springblade.core.tenant.TenantId;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.DesUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.mapper.TenantMapper;
import org.springblade.modules.system.pojo.entity.*;
import org.springblade.modules.system.rule.context.TenantContext;
import org.springblade.modules.system.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springblade.common.cache.SysCache.TENANT_TENANT_ID;
import static org.springblade.common.constant.TenantConstant.DES_KEY;
import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;
import static org.springblade.modules.system.rule.constant.TenantRuleConstant.TENANT_CHAIN_ID;

/**
 * 服务实现类
 *
 * @author Chill
 */
//@Master
@Service
@AllArgsConstructor
public class TenantServiceImpl extends BaseServiceImpl<TenantMapper, Tenant> implements ITenantService {

	private final TenantId tenantIdGenerator;
	private final IRoleService roleService;
	private final IMenuService menuService;
	private final IDeptService deptService;
	private final IPostService postService;
	private final IRoleMenuService roleMenuService;
	private final IDictBizService dictBizService;
	private final IUserService userService;
	private final IUserDeptService userDeptService;
	private final RuleEngineExecutor ruleExecutor;

	@Override
	public IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant) {
		return page.setRecords(baseMapper.selectTenantPage(page, tenant));
	}

	@Override
	public Tenant getByTenantId(String tenantId) {
		return getOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getTenantId, tenantId));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submitTenant(Tenant tenant) {
		if (Func.isEmpty(tenant.getId())) {
			LiteRuleResponse<TenantContext> resp = ruleExecutor.execute(
				TENANT_CHAIN_ID,
				TenantContext.builder()
					.tenantIdGenerator(tenantIdGenerator)
					.tenant(tenant)
					.menuService(menuService)
					.dictBizService(dictBizService)
					.tenantService(this)
					.build()
			);
			if (resp.isSuccess()) {
				TenantContext tenantContext = resp.getContext();
				Role role = tenantContext.getRole();
				roleService.save(role);

				Long roleId = role.getId();
				List<RoleMenu> roleMenuList = tenantContext.getRoleMenuList();
				roleMenuList.forEach(roleMenu -> roleMenu.setRoleId(roleId));
				roleMenuService.saveBatch(roleMenuList);

				Dept dept = tenantContext.getDept();
				deptService.save(dept);

				Post post = tenantContext.getPost();
				postService.save(post);

				List<DictBiz> dictBizList = tenantContext.getDictBizList();
				dictBizService.saveBatch(dictBizList);

				User user = tenantContext.getUser();
				user.setRoleId(String.valueOf(role.getId()));
				user.setDeptId(String.valueOf(dept.getId()));
				user.setPostId(String.valueOf(post.getId()));
				userService.submit(user);
			} else {
				throw new ServiceException("租户业务数据构建异常");
			}
		}
		CacheUtil.clear(SYS_CACHE, tenant.getTenantId());
		CacheUtil.evict(SYS_CACHE, TENANT_TENANT_ID, tenant.getTenantId(), Boolean.FALSE);
		return super.saveOrUpdate(tenant);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean recycleTenant(List<Long> ids) {
		List<String> tenantIds = this.list(Wrappers.<Tenant>query().lambda().in(Tenant::getId, ids))
			.stream().map(tenant -> Func.toStr(tenant.getTenantId())).distinct().collect(Collectors.toList());
		if (tenantIds.contains(BladeConstant.ADMIN_TENANT_ID)) {
			throw new ServiceException("不可删除管理租户!");
		}
		int disabledType = StatusType.DISABLED.getType();
		int activeType = StatusType.ACTIVE.getType();
		boolean temp = this.changeStatus(ids, disabledType);
		if (temp) {
			// 删除角色至回收站
			roleService.update(Wrappers.<Role>update().lambda().set(Role::getStatus, disabledType).eq(Role::getStatus, activeType).in(Role::getTenantId, tenantIds));
			// 删除部门至回收站
			deptService.update(Wrappers.<Dept>update().lambda().set(Dept::getStatus, disabledType).eq(Dept::getStatus, activeType).in(Dept::getTenantId, tenantIds));
			// 删除岗位至回收站
			postService.update(Wrappers.<Post>update().lambda().set(Post::getStatus, disabledType).eq(Post::getStatus, activeType).in(Post::getTenantId, tenantIds));
			// 删除业务字典至回收站
			dictBizService.update(Wrappers.<DictBiz>update().lambda().set(DictBiz::getStatus, disabledType).eq(DictBiz::getStatus, activeType).in(DictBiz::getTenantId, tenantIds));
			// 获取需要删除的用户主键集合
			List<Long> userIds = userService.list(Wrappers.<User>query().lambda().eq(User::getStatus, activeType).in(User::getTenantId, tenantIds)
				.select(User::getId)).stream().map(User::getId).collect(Collectors.toList());
			// 用户数据不为空则关联数据
			if (!userIds.isEmpty()) {
				// 删除用户部门及拓展部分至回收站
				userService.update(Wrappers.<User>update().lambda().set(User::getStatus, disabledType).in(User::getId, userIds));
				userDeptService.update(Wrappers.<UserDept>update().lambda().set(UserDept::getStatus, disabledType).in(UserDept::getUserId, userIds));
				// 删除用户自定义部分至回收站
				new UserOauth().update(Wrappers.<UserOauth>update().lambda().set(UserOauth::getStatus, disabledType).in(UserOauth::getUserId, userIds));
				new UserWeb().update(Wrappers.<UserWeb>update().lambda().set(UserWeb::getStatus, disabledType).in(UserWeb::getUserId, userIds));
				new UserApp().update(Wrappers.<UserApp>update().lambda().set(UserApp::getStatus, disabledType).in(UserApp::getUserId, userIds));
				new UserOther().update(Wrappers.<UserOther>update().lambda().set(UserOther::getStatus, disabledType).in(UserOther::getUserId, userIds));
			}
			CacheUtil.clear(SYS_CACHE, tenantIds);
			return true;
		} else {
			throw new ServiceException("删除租户失败!");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean passTenant(List<Long> ids) {
		List<String> tenantIds = this.list(Wrappers.<Tenant>query().lambda().in(Tenant::getId, ids))
			.stream().map(tenant -> Func.toStr(tenant.getTenantId())).distinct().collect(Collectors.toList());
		int disabledType = StatusType.DISABLED.getType();
		int activeType = StatusType.ACTIVE.getType();
		boolean temp = this.changeStatus(ids, activeType);
		if (temp) {
			// 恢复角色至正常状态
			roleService.update(Wrappers.<Role>update().lambda().set(Role::getStatus, activeType).eq(Role::getStatus, disabledType).in(Role::getTenantId, tenantIds));
			// 恢复部门至正常状态
			deptService.update(Wrappers.<Dept>update().lambda().set(Dept::getStatus, activeType).eq(Dept::getStatus, disabledType).in(Dept::getTenantId, tenantIds));
			// 恢复岗位至正常状态
			postService.update(Wrappers.<Post>update().lambda().set(Post::getStatus, activeType).eq(Post::getStatus, disabledType).in(Post::getTenantId, tenantIds));
			// 恢复业务字典至正常状态
			dictBizService.update(Wrappers.<DictBiz>update().lambda().set(DictBiz::getStatus, activeType).eq(DictBiz::getStatus, disabledType).in(DictBiz::getTenantId, tenantIds));
			// 获取需要恢复的用户主键集合
			List<Long> userIds = userService.list(Wrappers.<User>query().lambda().eq(User::getStatus, disabledType).in(User::getTenantId, tenantIds)
				.select(User::getId)).stream().map(User::getId).collect(Collectors.toList());
			// 用户数据不为空则关联数据
			if (!userIds.isEmpty()) {
				// 恢复用户部门及拓展部分至正常状态
				userService.update(Wrappers.<User>update().lambda().set(User::getStatus, activeType).in(User::getId, userIds));
				userDeptService.update(Wrappers.<UserDept>update().lambda().set(UserDept::getStatus, activeType).in(UserDept::getUserId, userIds));
				// 恢复用户自定义部分至正常状态
				new UserOauth().update(Wrappers.<UserOauth>update().lambda().set(UserOauth::getStatus, activeType).in(UserOauth::getUserId, userIds));
				new UserWeb().update(Wrappers.<UserWeb>update().lambda().set(UserWeb::getStatus, activeType).in(UserWeb::getUserId, userIds));
				new UserApp().update(Wrappers.<UserApp>update().lambda().set(UserApp::getStatus, activeType).in(UserApp::getUserId, userIds));
				new UserOther().update(Wrappers.<UserOther>update().lambda().set(UserOther::getStatus, activeType).in(UserOther::getUserId, userIds));
			}
			CacheUtil.clear(SYS_CACHE, tenantIds);
			return true;
		} else {
			throw new ServiceException("恢复租户失败!");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeTenant(List<Long> ids) {
		List<String> tenantIds = this.list(Wrappers.<Tenant>query().lambda().in(Tenant::getId, ids))
			.stream().map(tenant -> Func.toStr(tenant.getTenantId())).distinct().collect(Collectors.toList());
		if (tenantIds.contains(BladeConstant.ADMIN_TENANT_ID)) {
			throw new ServiceException("不可删除管理租户!");
		}
		boolean temp = this.deleteLogic(ids);
		if (temp) {
			// 删除角色不可再恢复
			roleService.remove(Wrappers.<Role>query().lambda().in(Role::getTenantId, tenantIds));
			// 删除部门不可再恢复
			deptService.remove(Wrappers.<Dept>query().lambda().in(Dept::getTenantId, tenantIds));
			// 删除岗位不可再恢复
			postService.remove(Wrappers.<Post>query().lambda().in(Post::getTenantId, tenantIds));
			// 删除业务字典不可再恢复
			dictBizService.remove(Wrappers.<DictBiz>query().lambda().in(DictBiz::getTenantId, tenantIds));
			// 获取需要删除的用户主键集合
			List<Long> userIds = userService.list(Wrappers.<User>query().lambda().in(User::getTenantId, tenantIds)
				.select(User::getId)).stream().map(User::getId).collect(Collectors.toList());
			// 用户数据不为空则关联数据
			if (!userIds.isEmpty()) {
				// 删除用户部门及拓展部分不可再恢复
				userService.removeByIds(userIds);
				userDeptService.remove(Wrappers.<UserDept>query().lambda().in(UserDept::getUserId, userIds));
				// 删除用户自定义部分不可再恢复
				new UserOauth().delete(Wrappers.<UserOauth>query().lambda().in(UserOauth::getUserId, userIds));
				new UserWeb().delete(Wrappers.<UserWeb>query().lambda().in(UserWeb::getUserId, userIds));
				new UserApp().delete(Wrappers.<UserApp>query().lambda().in(UserApp::getUserId, userIds));
				new UserOther().delete(Wrappers.<UserOther>query().lambda().in(UserOther::getUserId, userIds));
			}
			CacheUtil.clear(SYS_CACHE, tenantIds);
			return true;
		} else {
			throw new ServiceException("删除租户失败!");
		}
	}

	@Override
	public boolean setting(Integer accountNumber, Date expireTime, String ids) {
		List<Long> idList = Func.toLongList(ids);
		List<String> tenantIds = this.list(Wrappers.<Tenant>query().lambda().in(Tenant::getId, idList))
			.stream().map(tenant -> Func.toStr(tenant.getTenantId())).distinct().toList();
		tenantIds.forEach(tenantId -> {
			CacheUtil.clear(SYS_CACHE, tenantId);
			CacheUtil.evict(SYS_CACHE, TENANT_TENANT_ID, tenantId, Boolean.FALSE);
		});
		idList.forEach(id -> {
			Kv kv = Kv.create().set("accountNumber", accountNumber).set("expireTime", expireTime).set("id", id);
			String licenseKey = DesUtil.encryptToHex(JsonUtil.toJson(kv), DES_KEY);
			update(
				Wrappers.<Tenant>update().lambda()
					.set(Tenant::getAccountNumber, accountNumber)
					.set(Tenant::getExpireTime, expireTime)
					.set(Tenant::getLicenseKey, licenseKey)
					.eq(Tenant::getId, id)
			);
		});
		return true;
	}

}
