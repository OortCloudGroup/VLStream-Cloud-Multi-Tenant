package org.springblade.vlstream.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.vlstream.pojo.entity.MobileSceneGovernance;
import org.springblade.vlstream.pojo.vo.MobileSceneGovernanceLoopVO;
import org.springblade.vlstream.service.IVlsMobileSceneGovernanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 移动端场景治理 控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsMobileSceneGovernance")
@Tag(name = "移动端场景治理", description = "移动端场景治理接口")
public class VlsMobileSceneGovernanceController extends BladeController {

	private final IVlsMobileSceneGovernanceService vlsMobileSceneGovernanceService;

	@GetMapping("/immediate/list")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "即时治理列表", description = "查询移动端即时治理列表")
	public R<IPage<MobileSceneGovernance>> listImmediate(@Parameter(hidden = true) @RequestParam Map<String, Object> mobileSceneGovernance, Query query) {
		return R.data(vlsMobileSceneGovernanceService.listImmediate(Condition.getPage(query)));
	}

	@GetMapping("/loop/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "循环治理列表", description = "查询移动端循环治理列表（包含子循环任务）")
	public R<IPage<MobileSceneGovernanceLoopVO>> listLoop(@Parameter(hidden = true) @RequestParam Map<String, Object> mobileSceneGovernance, Query query) {
		return R.data(vlsMobileSceneGovernanceService.listLoop(Condition.getPage(query)));
	}

	@PostMapping("/immediate/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增即时治理", description = "新增移动端即时治理")
	public R saveImmediate(@Valid @RequestBody MobileSceneGovernance mobileSceneGovernance) {
		return R.status(vlsMobileSceneGovernanceService.saveImmediate(mobileSceneGovernance));
	}

	@PostMapping("/loop/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增循环治理", description = "新增移动端循环治理并生成子循环任务")
	public R saveLoop(@Valid @RequestBody MobileSceneGovernance mobileSceneGovernance) {
		return R.status(vlsMobileSceneGovernanceService.saveLoop(mobileSceneGovernance));
	}
}
