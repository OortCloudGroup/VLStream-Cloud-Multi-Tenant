package org.springblade.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.common.cache.SysCache;
import org.springblade.common.cache.UserCache;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.secure.constant.AuthConstant;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.Role;
import org.springblade.modules.system.pojo.entity.User;
import org.springblade.modules.system.pojo.vo.GrantVO;
import org.springblade.modules.system.pojo.vo.RoleVO;
import org.springblade.modules.system.service.IRoleService;
import org.springblade.modules.system.wrapper.RoleWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * controller
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@IsAdmin
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/role")
@Tag(name = "Role", description = "Role")
public class RoleController extends BladeController {

	private final IRoleService roleService;

	/**
	 * Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingrole")
	public R<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return R.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * list
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "roleName", description = "Parameter name", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "roleAlias", description = "role alias", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "list", description = "incomingrole")
	public R<List<RoleVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> role, BladeUser bladeUser) {
		QueryWrapper<Role> queryWrapper = Condition.getQueryWrapper(role, Role.class);
		List<Role> list = roleService.list((!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Role::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(RoleWrapper.build().listNodeVO(list));
	}

	/**
	 * Get the role tree structure
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "tree structure", description = "tree structure")
	public R<List<RoleVO>> tree(String tenantId, BladeUser bladeUser) {
		List<RoleVO> tree = roleService.tree(Func.toStrWithEmpty(tenantId, bladeUser.getTenantId()));
		return R.data(tree);
	}

	/**
	 * Get the tree structure of the specified role
	 */
	@GetMapping("/tree-by-id")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "tree structure", description = "tree structure")
	public R<List<RoleVO>> treeById(Long roleId, BladeUser bladeUser) {
		Role role = SysCache.getRole(roleId);
		List<RoleVO> tree = roleService.tree(Func.notNull(role) ? role.getTenantId() : bladeUser.getTenantId());
		return R.data(tree);
	}

	/**
	 * Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Add or modify", description = "incomingrole")
	public R submit(@Valid @RequestBody Role role) {
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(roleService.submit(role));
	}

	/**
	 * delete
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "delete", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(roleService.removeRole(ids));
	}

	/**
	 * Set role permissions
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "Permission settings", description = "incomingroleIdcollection as wellmenuIdgather")
	public R grant(@RequestBody GrantVO grantVO) {
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		boolean temp = roleService.grant(grantVO.getRoleIds(), grantVO.getMenuIds(), grantVO.getDataScopeIds(), grantVO.getApiScopeIds());
		return R.status(temp);
	}

	/**
	 * Drop down data source
	 */
	@PreAuth(AuthConstant.PERMIT_ALL)
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Drop down data source", description = "incomingidgather")
	public R<List<Role>> select(Long userId, String roleId) {
		if (Func.isNotEmpty(userId)) {
			User user = UserCache.getUser(userId);
			roleId = user.getRoleId();
		}
		List<Role> list = roleService.list(Wrappers.<Role>lambdaQuery().in(Role::getId, Func.toLongList(roleId)));
		return R.data(list);
	}

	/**
	 * Get a list of existing role aliases
	 */
	@GetMapping("/alias")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "Get role alias", description = "Get role alias")
	public R<List<Role>> alias() {
		return R.data(roleService.alias(AuthUtil.getTenantId()));
	}

}
