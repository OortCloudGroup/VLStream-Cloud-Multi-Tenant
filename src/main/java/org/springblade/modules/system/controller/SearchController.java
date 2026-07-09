package org.springblade.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.Post;
import org.springblade.modules.system.pojo.vo.DeptVO;
import org.springblade.modules.system.pojo.vo.PostVO;
import org.springblade.modules.system.pojo.vo.RoleVO;
import org.springblade.modules.system.pojo.vo.UserVO;
import org.springblade.modules.system.service.IDeptService;
import org.springblade.modules.system.service.IPostService;
import org.springblade.modules.system.service.IRoleService;
import org.springblade.modules.system.service.IUserService;
import org.springblade.modules.system.wrapper.PostWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Query controller
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/search")
@Tag(name = "Information inquiry", description = "Information inquiry")
public class SearchController {

	private final IRoleService roleService;

	private final IDeptService deptService;

	private final IPostService postService;

	private final IUserService userService;

	/**
	 * Role information query
	 */
	@GetMapping("/role")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Role information query", description = "incomingroleNameorparentId")
	public R<List<RoleVO>> roleSearch(String roleName, Long parentId) {
		return R.data(roleService.search(roleName, parentId));
	}

	/**
	 * Department information query
	 */
	@GetMapping("/dept")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Department information query", description = "incomingdeptNameorparentId")
	public R<List<DeptVO>> deptSearch(String deptName, Long parentId) {
		return R.data(deptService.search(deptName, parentId));
	}

	/**
	 * Job information inquiry
	 */
	@GetMapping("/post")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Job information inquiry", description = "incomingpostName")
	public R<IPage<PostVO>> postSearch(String postName, Query query) {
		LambdaQueryWrapper<Post> queryWrapper = Wrappers.<Post>query().lambda();
		if (Func.isNotBlank(postName)) {
			queryWrapper.like(Post::getPostName, postName);
		}
		IPage<Post> pages = postService.page(Condition.getPage(query), queryWrapper);
		return R.data(PostWrapper.build().pageVO(pages));
	}


	/**
	 * User list query
	 */
	@Parameters({
		@Parameter(name = "name", description = "Personnel name", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "deptName", description = "Department name", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "postName", description = "Job title", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "current", description = "Current page number", in = ParameterIn.QUERY, schema = @Schema(type = "int")),
		@Parameter(name = "size", description = "Quantity per page", in = ParameterIn.QUERY, schema = @Schema(type = "int"))
	})
	@ApiOperationSupport(order = 4)
	@Operation(summary = "User list query", description = "User list query")
	@GetMapping("/user")
	public R<IPage<UserVO>> userSearch(@Parameter(hidden = true) UserVO user, @Parameter(hidden = true) Query query) {
		return R.data(userService.selectUserSearch(user, query));
	}

	/**
	 * Get user's supervisor information
	 */
	@GetMapping("/leader-info")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Get user's supervisor information", description = "incominguserId")
	public R<List<UserVO>> leaderInfo(@Parameter(description = "userId", required = true) Long userId) {
		List<UserVO> list = userService.leaderInfo(userId);
		return R.data(list);
	}

	/**
	 * Get department manager information
	 */
	@GetMapping("/dept-leader-info")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Get department manager information", description = "incomingdeptId")
	public R<List<UserVO>> deptLeaderInfo(@Parameter(description = "departmentid", required = true) Long deptId) {
		List<UserVO> list = deptService.deptLeaderInfo(deptId);
		return R.data(list);
	}

	/**
	 * Get list of supervisors
	 */
	@GetMapping("/leader-list")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "Get list of supervisors", description = "Get a list of all supervisor users")
	public R<List<UserVO>> leaderList(@Parameter(description = "Tenant number") String tenantId, @Parameter(description = "Username") String realName) {
		List<UserVO> list = userService.leaderList(tenantId, realName);
		return R.data(list);
	}

}
