
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
import org.springblade.common.cache.DictCache;
import org.springblade.common.cache.UserCache;
import org.springblade.common.enums.DictEnum;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.secure.constant.AuthConstant;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.Dept;
import org.springblade.modules.system.pojo.entity.User;
import org.springblade.modules.system.pojo.vo.DeptVO;
import org.springblade.modules.system.pojo.vo.UserVO;
import org.springblade.modules.system.service.IDeptService;
import org.springblade.modules.system.wrapper.DeptWrapper;
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
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/dept")
@Tag(name = "department", description = "department")
public class DeptController extends BladeController {

	private final IDeptService deptService;

	/**
	 * Details
	 */
	@PreAuth(menu = "dept")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingdept")
	public R<DeptVO> detail(Dept dept) {
		Dept detail = deptService.getOne(Condition.getQueryWrapper(dept));
		return R.data(DeptWrapper.build().entityVO(detail));
	}

	/**
	 * list
	 */
	@PreAuth(menu = "dept")
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "deptName", description = "Department name", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "fullName", description = "Full name of department", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "list", description = "incomingdept")
	public R<List<DeptVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> dept, BladeUser bladeUser) {
		QueryWrapper<Dept> queryWrapper = Condition.getQueryWrapper(dept, Dept.class);
		List<Dept> list = deptService.list((!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Dept::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(DeptWrapper.build().listNodeVO(list));
	}

	/**
	 * Lazy loading list
	 */
	@PreAuth(menu = "dept")
	@GetMapping("/lazy-list")
	@Parameters({
		@Parameter(name = "deptName", description = "Department name", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "fullName", description = "Full name of department", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Lazy loading list", description = "incomingdept")
	public R<List<DeptVO>> lazyList(@Parameter(hidden = true) @RequestParam Map<String, Object> dept, Long parentId, BladeUser bladeUser) {
		List<DeptVO> list = deptService.lazyList(bladeUser.getTenantId(), parentId, dept);
		return R.data(DeptWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * Get department tree structure
	 */
	@PreAuth(menu = "dept")
	@GetMapping("/tree")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "tree structure", description = "tree structure")
	public R<List<DeptVO>> tree(String tenantId, BladeUser bladeUser) {
		List<DeptVO> tree = deptService.tree(Func.toStrWithEmpty(tenantId, bladeUser.getTenantId()));
		return R.data(tree);
	}

	/**
	 * Lazy loading to obtain department tree structure
	 */
	@PreAuth(menu = "dept")
	@GetMapping("/lazy-tree")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Lazy loading tree structure", description = "tree structure")
	public R<List<DeptVO>> lazyTree(String tenantId, Long parentId, BladeUser bladeUser) {
		List<DeptVO> tree = deptService.lazyTree(Func.toStrWithEmpty(tenantId, bladeUser.getTenantId()), parentId);
		return R.data(tree);
	}

	/**
	 * Add or modify
	 */
	@IsAdmin
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingdept")
	public R submit(@Valid @RequestBody Dept dept) {
		if (deptService.submit(dept)) {
			CacheUtil.clear(SYS_CACHE);
			CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
			// Return fields required for lazy loading tree update nodes
			Kv kv = Kv.create().set("id", String.valueOf(dept.getId())).set("tenantId", dept.getTenantId())
				.set("deptCategoryName", DictCache.getValue(DictEnum.ORG_CATEGORY, dept.getDeptCategory()));
			return R.data(kv);
		}
		return R.fail("Operation failed");
	}

	/**
	 * delete
	 */
	@IsAdmin
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "delete", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(deptService.removeDept(ids));
	}

	/**
	 * Drop down data source
	 */
	@PreAuth(AuthConstant.PERMIT_ALL)
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Drop down data source", description = "incomingidgather")
	public R<List<Dept>> select(Long userId, String deptId) {
		if (Func.isNotEmpty(userId)) {
			User user = UserCache.getUser(userId);
			deptId = user.getDeptId();
		}
		List<Dept> list = deptService.list(Wrappers.<Dept>lambdaQuery().in(Dept::getId, Func.toLongList(deptId)));
		return R.data(list);
	}

	/**
	 * Get department manager information
	 */
	@IsAdmin
	@GetMapping("/dept-leader-info")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "Get department manager information", description = "incomingdeptId")
	public R<List<UserVO>> deptLeaderInfo(@Parameter(description = "departmentid", required = true) @RequestParam Long deptId) {
		List<UserVO> list = deptService.deptLeaderInfo(deptId);
		return R.data(list);
	}

}
