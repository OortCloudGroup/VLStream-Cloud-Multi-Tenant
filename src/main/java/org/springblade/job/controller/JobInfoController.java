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
 * 任务信息表 控制器
 *
 * @author Oort
 */
@RestController
@AllArgsConstructor
@IsAdmin
@RequestMapping(AppConstant.APPLICATION_JOB_NAME + "/job-info")
@Tag(name = "任务信息表", description = "任务信息表接口")
public class JobInfoController extends BladeController {

	private final IJobInfoService jobInfoService;

	/**
	 * 任务信息表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入jobInfo")
	public R<JobInfo> detail(JobInfo jobInfo) {
		JobInfo detail = jobInfoService.getOne(Condition.getQueryWrapper(jobInfo));
		return R.data(detail);
	}

	/**
	 * 任务信息表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入jobInfo")
	public R<IPage<JobInfo>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> jobInfo, Query query) {
		IPage<JobInfo> pages = jobInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(jobInfo, JobInfo.class));
		return R.data(pages);
	}

	/**
	 * 任务信息表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description = "传入jobInfo")
	public R<IPage<JobInfoVO>> page(JobInfoVO jobInfo, Query query) {
		IPage<JobInfoVO> pages = jobInfoService.selectJobInfoPage(Condition.getPage(query), jobInfo);
		return R.data(pages);
	}

	/**
	 * 任务信息表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入jobInfo")
	public R save(@Valid @RequestBody JobInfo jobInfo) {
		return R.status(jobInfoService.save(jobInfo));
	}

	/**
	 * 任务信息表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入jobInfo")
	public R update(@Valid @RequestBody JobInfo jobInfo) {
		return R.status(jobInfoService.updateById(jobInfo));
	}

	/**
	 * 任务信息表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入jobInfo")
	public R submit(@Valid @RequestBody JobInfo jobInfo) {
		return R.status(jobInfoService.submitAndSync(jobInfo));
	}

	/**
	 * 任务信息表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(jobInfoService.removeAndSync(Func.toLongList(ids)));
	}

	/**
	 * 任务信息表 变更状态
	 */
	@PostMapping("/change")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "变更状态", description = "传入id与status")
	public R change(@Parameter(description = "主键", required = true) @RequestParam Long id, @Parameter(description = "是否启用", required = true) @RequestParam Integer enable) {
		return R.status(jobInfoService.changeServerJob(id, enable));
	}

	/**
	 * 运行服务
	 */
	@PostMapping("run")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "运行服务", description = "传入jobInfoId")
	public R run(@Parameter(description = "主键", required = true) @RequestParam Long id) {
		return R.status(jobInfoService.runServerJob(id));
	}


	/**
	 * 任务信息数据同步
	 */
	@PostMapping("sync")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "任务信息数据同步", description = "任务信息数据同步")
	public R sync() {
		return R.status(jobInfoService.sync());
	}

}
