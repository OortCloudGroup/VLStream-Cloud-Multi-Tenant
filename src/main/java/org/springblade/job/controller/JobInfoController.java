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
import org.springblade.job.pojo.entity.JobInfo;
import org.springblade.job.pojo.vo.JobInfoVO;
import org.springblade.job.service.IJobInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Task information sheet controller
 *
 * @author Oort
 */
@RestController
@AllArgsConstructor
@IsAdmin
@RequestMapping(AppConstant.APPLICATION_JOB_NAME + "/job-info")
@Tag(name = "Task information sheet", description = "Task information table interface")
public class JobInfoController extends BladeController {

	private final IJobInfoService jobInfoService;

	/**
	 * Task information sheet Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingjobInfo")
	public R<JobInfo> detail(JobInfo jobInfo) {
		JobInfo detail = jobInfoService.getOne(Condition.getQueryWrapper(jobInfo));
		return R.data(detail);
	}

	/**
	 * Task information sheet Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingjobInfo")
	public R<IPage<JobInfo>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> jobInfo, Query query) {
		IPage<JobInfo> pages = jobInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(jobInfo, JobInfo.class));
		return R.data(pages);
	}

	/**
	 * Task information sheet Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description = "incomingjobInfo")
	public R<IPage<JobInfoVO>> page(JobInfoVO jobInfo, Query query) {
		IPage<JobInfoVO> pages = jobInfoService.selectJobInfoPage(Condition.getPage(query), jobInfo);
		return R.data(pages);
	}

	/**
	 * Task information sheet New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingjobInfo")
	public R save(@Valid @RequestBody JobInfo jobInfo) {
		return R.status(jobInfoService.save(jobInfo));
	}

	/**
	 * Task information sheet Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingjobInfo")
	public R update(@Valid @RequestBody JobInfo jobInfo) {
		return R.status(jobInfoService.updateById(jobInfo));
	}

	/**
	 * Task information sheet Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingjobInfo")
	public R submit(@Valid @RequestBody JobInfo jobInfo) {
		return R.status(jobInfoService.submitAndSync(jobInfo));
	}

	/**
	 * Task information sheet delete
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "delete", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(jobInfoService.removeAndSync(Func.toLongList(ids)));
	}

	/**
	 * Task information sheet changestate
	 */
	@PostMapping("/change")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "changestate", description = "incomingidandstatus")
	public R change(@Parameter(description = "primary key", required = true) @RequestParam Long id, @Parameter(description = "Whether to enable", required = true) @RequestParam Integer enable) {
		return R.status(jobInfoService.changeServerJob(id, enable));
	}

	/**
	 * Run service
	 */
	@PostMapping("run")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "Run service", description = "incomingjobInfoId")
	public R run(@Parameter(description = "primary key", required = true) @RequestParam Long id) {
		return R.status(jobInfoService.runServerJob(id));
	}


	/**
	 * Task information data synchronization
	 */
	@PostMapping("sync")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "Task information data synchronization", description = "Task information data synchronization")
	public R sync() {
		return R.status(jobInfoService.sync());
	}

}
