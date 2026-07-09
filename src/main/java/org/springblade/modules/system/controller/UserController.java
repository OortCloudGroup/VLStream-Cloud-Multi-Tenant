package org.springblade.modules.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.system.excel.UserExcel;
import org.springblade.modules.system.excel.UserImporter;
import org.springblade.modules.system.pojo.entity.User;
import org.springblade.modules.system.pojo.vo.UserVO;
import org.springblade.modules.system.service.IUserService;
import org.springblade.modules.system.wrapper.UserWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * controller
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/user")
@Tag(name = "user", description = "user")
public class UserController {

	private final IUserService userService;

	/**
	 * Query single item
	 */
	@IsAdmin
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "check the details", description = "incomingid")
	public R<UserVO> detail(User user) {
		User detail = userService.getOne(Condition.getQueryWrapper(user));
		return R.data(UserWrapper.build().entityVO(detail));
	}

	/**
	 * Query single item
	 */
	@GetMapping("/info")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "check the details", description = "incomingid")
	public R<UserVO> info(BladeUser user) {
		User detail = userService.getById(user.getUserId());
		return R.data(UserWrapper.build().entityVO(detail));
	}

	/**
	 * User list
	 */
	@IsAdmin
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "account", description = "Account name", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "realName", description = "Name", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "list", description = "incomingaccountandrealName")
	public R<IPage<UserVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> user, Query query, BladeUser bladeUser) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		IPage<User> pages = userService.page(Condition.getPage(query), (!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(UserWrapper.build().pageVO(pages));
	}

	/**
	 * Custom user list
	 */
	@IsAdmin
	@GetMapping("/page")
	@Parameters({
		@Parameter(name = "account", description = "Account name", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "realName", description = "Name", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "list", description = "incomingaccountandrealName")
	public R<IPage<UserVO>> page(@Parameter(hidden = true) User user, Query query, Long deptId, BladeUser bladeUser) {
		IPage<User> pages = userService.selectUserPage(Condition.getPage(query), user, deptId, (bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? StringPool.EMPTY : bladeUser.getTenantId()));
		return R.data(UserWrapper.build().pageVO(pages));
	}

	/**
	 * Add or modify
	 */
	@IsAdmin
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Add or modify", description = "incomingUser")
	public R submit(@Valid @RequestBody User user) {
		return R.status(userService.submit(user));
	}

	/**
	 * Revise
	 */
	@IsAdmin
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingUser")
	public R update(@Valid @RequestBody User user) {
		return R.status(userService.updateUser(user));
	}

	/**
	 * delete
	 */
	@IsAdmin
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "delete", description = "incomingidgather")
	public R remove(@RequestParam String ids) {
		return R.status(userService.removeUser(ids));
	}

	/**
	 * Set menu permissions
	 */
	@IsAdmin
	@PostMapping("/grant")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "Permission settings", description = "incomingroleIdcollection as wellmenuIdgather")
	public R grant(@Parameter(description = "userIdgather", required = true) @RequestParam String userIds,
				   @Parameter(description = "roleIdgather", required = true) @RequestParam String roleIds) {
		boolean temp = userService.grant(userIds, roleIds);
		return R.status(temp);
	}

	/**
	 * reset password
	 */
	@IsAdmin
	@PostMapping("/reset-password")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Initialization password", description = "incominguserIdgather")
	public R resetPassword(@Parameter(description = "userIdgather", required = true) @RequestParam String userIds) {
		boolean temp = userService.resetPassword(userIds);
		return R.status(temp);
	}

	/**
	 * Change password
	 */
	@PostMapping("/update-password")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "Change password", description = "Pass in password")
	public R updatePassword(BladeUser user, @Parameter(description = "Old Password", required = true) @RequestParam String oldPassword,
							@Parameter(description = "New Password", required = true) @RequestParam String newPassword,
							@Parameter(description = "New Password", required = true) @RequestParam String newPassword1) {
		boolean temp = userService.updatePassword(user.getUserId(), oldPassword, newPassword, newPassword1);
		return R.status(temp);
	}

	/**
	 * Modify basic information
	 */
	@PostMapping("/update-info")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "Modify basic information", description = "incomingUser")
	public R updateInfo(@Valid @RequestBody User user) {
		return R.status(userService.updateUserInfo(user));
	}

	/**
	 * User list
	 */
	@GetMapping("/user-list")
	@ApiOperationSupport(order = 11)
	@Operation(summary = "User list", description = "incominguser")
	public R<List<UserVO>> userList(User user, BladeUser bladeUser) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user);
		List<User> list = userService.list((!AuthUtil.isAdministrator()) ? queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(UserWrapper.build().listVO(list));
	}

	/**
	 * Import users
	 */
	@IsAdmin
	@PostMapping("import-user")
	@ApiOperationSupport(order = 12)
	@Operation(summary = "Import users", description = "incomingexcel")
	public R importUser(MultipartFile file, Integer isCovered) {
		UserImporter userImporter = new UserImporter(userService, isCovered == 1);
		ExcelUtil.save(file, userImporter, UserExcel.class);
		return R.success("Operation successful");
	}

	/**
	 * Export users
	 */
	@IsAdmin
	@GetMapping("export-user")
	@ApiOperationSupport(order = 13)
	@Operation(summary = "Export users", description = "incominguser")
	public void exportUser(@Parameter(hidden = true) @RequestParam Map<String, Object> user, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		if (!AuthUtil.isAdministrator()) {
			queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId());
		}
		queryWrapper.lambda().eq(User::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<UserExcel> list = userService.exportUser(queryWrapper);
		ExcelUtil.export(response, "User data" + DateUtil.time(), "user data table", list, UserExcel.class);
	}

	/**
	 * Export template
	 */
	@GetMapping("export-template")
	@ApiOperationSupport(order = 14)
	@Operation(summary = "Export template")
	public void exportUser(HttpServletResponse response) {
		List<UserExcel> list = new ArrayList<>();
		ExcelUtil.export(response, "User data template", "user data table", list, UserExcel.class);
	}


	/**
	 * Third-party registered users
	 */
	@PostMapping("/register-guest")
	@ApiOperationSupport(order = 15)
	@Operation(summary = "Third-party registered users", description = "incominguser")
	public R registerGuest(User user, Long oauthId) {
		return R.status(userService.registerGuest(user, oauthId));
	}

	/**
	 * Configure user platform information
	 */
	@PostMapping("/update-platform")
	@ApiOperationSupport(order = 16)
	@Operation(summary = "Configure user platform information", description = "incominguser")
	public R updatePlatform(Long userId, Integer userType, String userExt) {
		return R.status(userService.updatePlatform(userId, userType, userExt));
	}

	/**
	 * View platform details
	 */
	@IsAdmin
	@GetMapping("/platform-detail")
	@ApiOperationSupport(order = 17)
	@Operation(summary = "View platform details", description = "incomingid")
	public R<UserVO> platformDetail(User user) {
		return R.data(userService.platformDetail(user));
	}

	/**
	 * User unlock
	 */
	@IsAdmin
	@PostMapping("/unlock")
	@ApiOperationSupport(order = 18)
	@Operation(summary = "Account unlock", description = "incomingidgather")
	public R unlock(String userIds) {
		return R.status(userService.unlock(userIds));
	}

	/**
	 * Approved
	 */
	@IsAdmin
	@PostMapping("/audit-pass")
	@ApiOperationSupport(order = 19)
	@Operation(summary = "Approved", description = "incomingidgather")
	public R auditPass(String userIds) {
		return R.status(userService.auditPass(userIds));
	}

	/**
	 * Review rejection
	 */
	@IsAdmin
	@PostMapping("/audit-refuse")
	@ApiOperationSupport(order = 20)
	@Operation(summary = "Review rejection", description = "incomingidgather")
	public R auditRefuse(String userIds) {
		return R.status(userService.auditRefuse(userIds));
	}

	/**
	 * Set user as supervisor
	 */
	@IsAdmin
	@PostMapping("/set-leader")
	@ApiOperationSupport(order = 21)
	@Operation(summary = "Set user as supervisor", description = "incominguserId")
	public R setLeader(@Parameter(description = "userId", required = true) @RequestParam Long userId) {
		return R.status(userService.setLeader(userId));
	}

	/**
	 * Get user's supervisor information
	 */
	@IsAdmin
	@GetMapping("/leader-info")
	@ApiOperationSupport(order = 22)
	@Operation(summary = "Get user's supervisor information", description = "incominguserId")
	public R<List<UserVO>> leaderInfo(@Parameter(description = "userId", required = true) @RequestParam Long userId) {
		List<UserVO> list = userService.leaderInfo(userId);
		return R.data(list);
	}

	/**
	 * Get list of supervisors
	 */
	@IsAdmin
	@GetMapping("/leader-list")
	@ApiOperationSupport(order = 23)
	@Operation(summary = "Get list of supervisors", description = "Get a list of all supervisor users")
	public R<List<UserVO>> leaderList(@Parameter(description = "Tenant number") String tenantId, @Parameter(description = "Username") String realName) {
		List<UserVO> list = userService.leaderList(tenantId, realName);
		return R.data(list);
	}
}
