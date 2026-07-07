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
 * 控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/user")
@Tag(name = "用户", description = "用户")
public class UserController {

	private final IUserService userService;

	/**
	 * 查询单条
	 */
	@IsAdmin
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "查看详情", description = "传入id")
	public R<UserVO> detail(User user) {
		User detail = userService.getOne(Condition.getQueryWrapper(user));
		return R.data(UserWrapper.build().entityVO(detail));
	}

	/**
	 * 查询单条
	 */
	@GetMapping("/info")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "查看详情", description = "传入id")
	public R<UserVO> info(BladeUser user) {
		User detail = userService.getById(user.getUserId());
		return R.data(UserWrapper.build().entityVO(detail));
	}

	/**
	 * 用户列表
	 */
	@IsAdmin
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "account", description = "账号名", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "realName", description = "姓名", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "列表", description = "传入account和realName")
	public R<IPage<UserVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> user, Query query, BladeUser bladeUser) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		IPage<User> pages = userService.page(Condition.getPage(query), (!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(UserWrapper.build().pageVO(pages));
	}

	/**
	 * 自定义用户列表
	 */
	@IsAdmin
	@GetMapping("/page")
	@Parameters({
		@Parameter(name = "account", description = "账号名", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "realName", description = "姓名", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "列表", description = "传入account和realName")
	public R<IPage<UserVO>> page(@Parameter(hidden = true) User user, Query query, Long deptId, BladeUser bladeUser) {
		IPage<User> pages = userService.selectUserPage(Condition.getPage(query), user, deptId, (bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? StringPool.EMPTY : bladeUser.getTenantId()));
		return R.data(UserWrapper.build().pageVO(pages));
	}

	/**
	 * 新增或修改
	 */
	@IsAdmin
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增或修改", description = "传入User")
	public R submit(@Valid @RequestBody User user) {
		return R.status(userService.submit(user));
	}

	/**
	 * 修改
	 */
	@IsAdmin
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入User")
	public R update(@Valid @RequestBody User user) {
		return R.status(userService.updateUser(user));
	}

	/**
	 * 删除
	 */
	@IsAdmin
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "删除", description = "传入id集合")
	public R remove(@RequestParam String ids) {
		return R.status(userService.removeUser(ids));
	}

	/**
	 * 设置菜单权限
	 */
	@IsAdmin
	@PostMapping("/grant")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "权限设置", description = "传入roleId集合以及menuId集合")
	public R grant(@Parameter(description = "userId集合", required = true) @RequestParam String userIds,
				   @Parameter(description = "roleId集合", required = true) @RequestParam String roleIds) {
		boolean temp = userService.grant(userIds, roleIds);
		return R.status(temp);
	}

	/**
	 * 重置密码
	 */
	@IsAdmin
	@PostMapping("/reset-password")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "初始化密码", description = "传入userId集合")
	public R resetPassword(@Parameter(description = "userId集合", required = true) @RequestParam String userIds) {
		boolean temp = userService.resetPassword(userIds);
		return R.status(temp);
	}

	/**
	 * 修改密码
	 */
	@PostMapping("/update-password")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "修改密码", description = "传入密码")
	public R updatePassword(BladeUser user, @Parameter(description = "旧密码", required = true) @RequestParam String oldPassword,
							@Parameter(description = "新密码", required = true) @RequestParam String newPassword,
							@Parameter(description = "新密码", required = true) @RequestParam String newPassword1) {
		boolean temp = userService.updatePassword(user.getUserId(), oldPassword, newPassword, newPassword1);
		return R.status(temp);
	}

	/**
	 * 修改基本信息
	 */
	@PostMapping("/update-info")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "修改基本信息", description = "传入User")
	public R updateInfo(@Valid @RequestBody User user) {
		return R.status(userService.updateUserInfo(user));
	}

	/**
	 * 用户列表
	 */
	@GetMapping("/user-list")
	@ApiOperationSupport(order = 11)
	@Operation(summary = "用户列表", description = "传入user")
	public R<List<UserVO>> userList(User user, BladeUser bladeUser) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user);
		List<User> list = userService.list((!AuthUtil.isAdministrator()) ? queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(UserWrapper.build().listVO(list));
	}

	/**
	 * 导入用户
	 */
	@IsAdmin
	@PostMapping("import-user")
	@ApiOperationSupport(order = 12)
	@Operation(summary = "导入用户", description = "传入excel")
	public R importUser(MultipartFile file, Integer isCovered) {
		UserImporter userImporter = new UserImporter(userService, isCovered == 1);
		ExcelUtil.save(file, userImporter, UserExcel.class);
		return R.success("操作成功");
	}

	/**
	 * 导出用户
	 */
	@IsAdmin
	@GetMapping("export-user")
	@ApiOperationSupport(order = 13)
	@Operation(summary = "导出用户", description = "传入user")
	public void exportUser(@Parameter(hidden = true) @RequestParam Map<String, Object> user, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		if (!AuthUtil.isAdministrator()) {
			queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId());
		}
		queryWrapper.lambda().eq(User::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<UserExcel> list = userService.exportUser(queryWrapper);
		ExcelUtil.export(response, "用户数据" + DateUtil.time(), "用户数据表", list, UserExcel.class);
	}

	/**
	 * 导出模板
	 */
	@GetMapping("export-template")
	@ApiOperationSupport(order = 14)
	@Operation(summary = "导出模板")
	public void exportUser(HttpServletResponse response) {
		List<UserExcel> list = new ArrayList<>();
		ExcelUtil.export(response, "用户数据模板", "用户数据表", list, UserExcel.class);
	}


	/**
	 * 第三方注册用户
	 */
	@PostMapping("/register-guest")
	@ApiOperationSupport(order = 15)
	@Operation(summary = "第三方注册用户", description = "传入user")
	public R registerGuest(User user, Long oauthId) {
		return R.status(userService.registerGuest(user, oauthId));
	}

	/**
	 * 配置用户平台信息
	 */
	@PostMapping("/update-platform")
	@ApiOperationSupport(order = 16)
	@Operation(summary = "配置用户平台信息", description = "传入user")
	public R updatePlatform(Long userId, Integer userType, String userExt) {
		return R.status(userService.updatePlatform(userId, userType, userExt));
	}

	/**
	 * 查看平台详情
	 */
	@IsAdmin
	@GetMapping("/platform-detail")
	@ApiOperationSupport(order = 17)
	@Operation(summary = "查看平台详情", description = "传入id")
	public R<UserVO> platformDetail(User user) {
		return R.data(userService.platformDetail(user));
	}

	/**
	 * 用户解锁
	 */
	@IsAdmin
	@PostMapping("/unlock")
	@ApiOperationSupport(order = 18)
	@Operation(summary = "账号解锁", description = "传入id集合")
	public R unlock(String userIds) {
		return R.status(userService.unlock(userIds));
	}

	/**
	 * 审核通过
	 */
	@IsAdmin
	@PostMapping("/audit-pass")
	@ApiOperationSupport(order = 19)
	@Operation(summary = "审核通过", description = "传入id集合")
	public R auditPass(String userIds) {
		return R.status(userService.auditPass(userIds));
	}

	/**
	 * 审核拒绝
	 */
	@IsAdmin
	@PostMapping("/audit-refuse")
	@ApiOperationSupport(order = 20)
	@Operation(summary = "审核拒绝", description = "传入id集合")
	public R auditRefuse(String userIds) {
		return R.status(userService.auditRefuse(userIds));
	}

	/**
	 * 设置用户为主管
	 */
	@IsAdmin
	@PostMapping("/set-leader")
	@ApiOperationSupport(order = 21)
	@Operation(summary = "设置用户为主管", description = "传入userId")
	public R setLeader(@Parameter(description = "userId", required = true) @RequestParam Long userId) {
		return R.status(userService.setLeader(userId));
	}

	/**
	 * 获取用户的主管信息
	 */
	@IsAdmin
	@GetMapping("/leader-info")
	@ApiOperationSupport(order = 22)
	@Operation(summary = "获取用户的主管信息", description = "传入userId")
	public R<List<UserVO>> leaderInfo(@Parameter(description = "userId", required = true) @RequestParam Long userId) {
		List<UserVO> list = userService.leaderInfo(userId);
		return R.data(list);
	}

	/**
	 * 获取主管列表
	 */
	@IsAdmin
	@GetMapping("/leader-list")
	@ApiOperationSupport(order = 23)
	@Operation(summary = "获取主管列表", description = "获取所有主管用户列表")
	public R<List<UserVO>> leaderList(@Parameter(description = "租户编号") String tenantId, @Parameter(description = "用户姓名") String realName) {
		List<UserVO> list = userService.leaderList(tenantId, realName);
		return R.data(list);
	}
}
