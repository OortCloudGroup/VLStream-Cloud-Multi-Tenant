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
 * task service table controller
 *
 * @author Oort
 */
@RestController
@AllArgsConstructor
@IsAdmin
@RequestMapping(AppConstant.APPLICATION_JOB_NAME + "/job-server")
@Tag(name = "task service table", description = "Task service table interface")
public class JobServerController extends BladeController {

	private final IJobServerService jobServerService;

	/**
	 * task service table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingjobServer")
	public R<JobServer> detail(JobServer jobServer) {
		JobServer detail = jobServerService.getOne(Condition.getQueryWrapper(jobServer));
		return R.data(detail);
	}

	/**
	 * task service table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingjobServer")
	public R<IPage<JobServer>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> jobServer, Query query) {
		IPage<JobServer> pages = jobServerService.page(Condition.getPage(query), Condition.getQueryWrapper(jobServer, JobServer.class));
		return R.data(pages);
	}

	/**
	 * task service table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description = "incomingjobServer")
	public R<IPage<JobServerVO>> page(JobServerVO jobServer, Query query) {
		IPage<JobServerVO> pages = jobServerService.selectJobServerPage(Condition.getPage(query), jobServer);
		return R.data(pages);
	}

	/**
	 * task service table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingjobServer")
	public R save(@Valid @RequestBody JobServer jobServer) {
		return R.status(jobServerService.save(jobServer));
	}

	/**
	 * task service table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingjobServer")
	public R update(@Valid @RequestBody JobServer jobServer) {
		return R.status(jobServerService.updateById(jobServer));
	}

	/**
	 * task service table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingjobServer")
	public R submit(@Valid @RequestBody JobServer jobServer) {
		return R.status(jobServerService.submitAndSync(jobServer));
	}

	/**
	 * task service table delete
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(jobServerService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Application service information list
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Application service information", description = "Application service information")
	public R select() {
		List<JobServer> list = jobServerService.list();
		list.forEach(jobServer -> jobServer.setJobAppName(
			jobServer.getJobAppName() + StringPool.COLON + StringPool.SPACE + StringPool.LEFT_BRACKET +
				jobServer.getJobServerName() + StringPool.SPACE + StringPool.DASH + StringPool.SPACE + jobServer.getJobServerUrl() + StringPool.RIGHT_BRACKET)
		);
		return R.data(list);
	}

	/**
	 * Task service data synchronization
	 */
	@PostMapping("sync")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "Task service data synchronization", description = "Task service data synchronization")
	public R sync() {
		jobServerService.list().forEach(jobServerService::sync);
		return R.success("Synchronization completed");
	}


}
