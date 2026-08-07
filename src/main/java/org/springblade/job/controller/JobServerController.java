package org.springblade.job.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.job.pojo.entity.JobServer;
import org.springblade.job.pojo.vo.JobServerVO;
import org.springblade.job.service.IJobServerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 任务服务表 控制器
 *
 * @author Oort
 */
@RestController
@AllArgsConstructor
@IsAdmin
@RequestMapping(AppConstant.APPLICATION_JOB_NAME + "/job-server")
@Tag(name = "任务服务表", description = "任务服务表接口")
public class JobServerController extends BladeController {

	private final IJobServerService jobServerService;

	/**
	 * 任务服务表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入jobServer")
	public R<JobServer> detail(JobServer jobServer) {
		JobServer detail = jobServerService.getOne(Condition.getQueryWrapper(jobServer));
		return R.data(detail);
	}

	/**
	 * 任务服务表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入jobServer")
	public R<IPage<JobServer>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> jobServer, Query query) {
		IPage<JobServer> pages = jobServerService.page(Condition.getPage(query), Condition.getQueryWrapper(jobServer, JobServer.class));
		return R.data(pages);
	}

	/**
	 * 任务服务表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description = "传入jobServer")
	public R<IPage<JobServerVO>> page(JobServerVO jobServer, Query query) {
		IPage<JobServerVO> pages = jobServerService.selectJobServerPage(Condition.getPage(query), jobServer);
		return R.data(pages);
	}

	/**
	 * 任务服务表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入jobServer")
	public R save(@Valid @RequestBody JobServer jobServer) {
		return R.status(jobServerService.save(jobServer));
	}

	/**
	 * 任务服务表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入jobServer")
	public R update(@Valid @RequestBody JobServer jobServer) {
		return R.status(jobServerService.updateById(jobServer));
	}

	/**
	 * 任务服务表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入jobServer")
	public R submit(@Valid @RequestBody JobServer jobServer) {
		return R.status(jobServerService.submitAndSync(jobServer));
	}

	/**
	 * 任务服务表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(jobServerService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 应用服务信息 列表
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "应用服务信息", description = "应用服务信息")
	public R select() {
		List<JobServer> list = jobServerService.list();
		list.forEach(jobServer -> jobServer.setJobAppName(
			jobServer.getJobAppName() + StringPool.COLON + StringPool.SPACE + StringPool.LEFT_BRACKET +
				jobServer.getJobServerName() + StringPool.SPACE + StringPool.DASH + StringPool.SPACE + jobServer.getJobServerUrl() + StringPool.RIGHT_BRACKET)
		);
		return R.data(list);
	}

	/**
	 * 任务服务数据同步
	 */
	@PostMapping("sync")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "任务服务数据同步", description = "任务服务数据同步")
	public R sync() {
		jobServerService.list().forEach(jobServerService::sync);
		return R.success("同步完毕");
	}


}
