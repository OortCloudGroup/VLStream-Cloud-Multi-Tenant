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
 * Mobile scene management controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsMobileSceneGovernance")
@Tag(name = "Mobile scene management", description = "Mobile scene management interface")
public class VlsMobileSceneGovernanceController extends BladeController {

	private final IVlsMobileSceneGovernanceService vlsMobileSceneGovernanceService;

	@GetMapping("/immediate/list")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Instant governance list", description = "Query the real-time governance list on the mobile terminal")
	public R<IPage<MobileSceneGovernance>> listImmediate(@Parameter(hidden = true) @RequestParam Map<String, Object> mobileSceneGovernance, Query query) {
		return R.data(vlsMobileSceneGovernanceService.listImmediate(Condition.getPage(query)));
	}

	@GetMapping("/loop/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Cycle governance list", description = "Query the mobile cycle management list(Contains sub-loop tasks)")
	public R<IPage<MobileSceneGovernanceLoopVO>> listLoop(@Parameter(hidden = true) @RequestParam Map<String, Object> mobileSceneGovernance, Query query) {
		return R.data(vlsMobileSceneGovernanceService.listLoop(Condition.getPage(query)));
	}

	@PostMapping("/immediate/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Add real-time management", description = "Added real-time management on mobile terminal")
	public R saveImmediate(@Valid @RequestBody MobileSceneGovernance mobileSceneGovernance) {
		return R.status(vlsMobileSceneGovernanceService.saveImmediate(mobileSceneGovernance));
	}

	@PostMapping("/loop/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Added cycle management", description = "Added mobile terminal cycle management and generated sub-cycle tasks")
	public R saveLoop(@Valid @RequestBody MobileSceneGovernance mobileSceneGovernance) {
		return R.status(vlsMobileSceneGovernanceService.saveLoop(mobileSceneGovernance));
	}
}
