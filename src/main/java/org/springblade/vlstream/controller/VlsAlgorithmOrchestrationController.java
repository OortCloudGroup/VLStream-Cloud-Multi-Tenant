package org.springblade.vlstream.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import jakarta.validation.Valid;

import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.vlstream.pojo.entity.AlgorithmOrchestration;
import org.springblade.vlstream.pojo.vo.AlgorithmOrchestrationVO;
import org.springblade.vlstream.excel.VlsAlgorithmOrchestrationExcel;
import org.springblade.vlstream.wrapper.VlsAlgorithmOrchestrationWrapper;
import org.springblade.vlstream.service.IVlsAlgorithmOrchestrationService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.excel.util.ExcelUtil;

import java.util.Map;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Algorithm layout table controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsAlgorithmOrchestration")
@Tag(name = "Algorithm layout table", description = "Algorithm layout table interface")
public class VlsAlgorithmOrchestrationController extends BladeController {

	private final IVlsAlgorithmOrchestrationService vlsAlgorithmOrchestrationService;

	/**
	 * Algorithm layout table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description  = "incomingvlsAlgorithmOrchestration")
	public R<AlgorithmOrchestrationVO> detail(AlgorithmOrchestration vlsAlgorithmOrchestration) {
		AlgorithmOrchestration detail = vlsAlgorithmOrchestrationService.getOne(Condition.getQueryWrapper(vlsAlgorithmOrchestration));
		return R.data(VlsAlgorithmOrchestrationWrapper.build().entityVO(detail));
	}

	/**
	 * Algorithm layout table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description  = "incomingvlsAlgorithmOrchestration")
	public R<IPage<AlgorithmOrchestrationVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAlgorithmOrchestration, Query query) {
		IPage<AlgorithmOrchestration> pages = vlsAlgorithmOrchestrationService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsAlgorithmOrchestration, AlgorithmOrchestration.class));
		return R.data(VlsAlgorithmOrchestrationWrapper.build().pageVO(pages));
	}


	/**
	 * Algorithm layout table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description  = "incomingvlsAlgorithmOrchestration")
	public R<IPage<AlgorithmOrchestrationVO>> page(AlgorithmOrchestrationVO vlsAlgorithmOrchestration, Query query) {
		IPage<AlgorithmOrchestrationVO> pages = vlsAlgorithmOrchestrationService.selectVlsAlgorithmOrchestrationPage(Condition.getPage(query), vlsAlgorithmOrchestration);
		return R.data(pages);
	}

	/**
	 * Algorithm layout table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description  = "incomingvlsAlgorithmOrchestration")
	public R save(@Valid @RequestBody AlgorithmOrchestration vlsAlgorithmOrchestration) {
		return R.status(vlsAlgorithmOrchestrationService.save(vlsAlgorithmOrchestration));
	}

	/**
	 * Algorithm layout table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description  = "incomingvlsAlgorithmOrchestration")
	public R update(@Valid @RequestBody AlgorithmOrchestration vlsAlgorithmOrchestration) {
		return R.status(vlsAlgorithmOrchestrationService.updateById(vlsAlgorithmOrchestration));
	}

	/**
	 * Algorithm layout table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description  = "incomingvlsAlgorithmOrchestration")
	public R submit(@Valid @RequestBody AlgorithmOrchestration vlsAlgorithmOrchestration) {
		return R.status(vlsAlgorithmOrchestrationService.saveOrUpdate(vlsAlgorithmOrchestration));
	}

	/**
	 * Algorithm layout table delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description  = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsAlgorithmOrchestrationService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsAlgorithmOrchestration")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description  = "incomingvlsAlgorithmOrchestration")
	public void exportVlsAlgorithmOrchestration(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAlgorithmOrchestration, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<AlgorithmOrchestration> queryWrapper = Condition.getQueryWrapper(vlsAlgorithmOrchestration, AlgorithmOrchestration.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsAlgorithmOrchestrationEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsAlgorithmOrchestrationEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsAlgorithmOrchestrationExcel> list = vlsAlgorithmOrchestrationService.exportVlsAlgorithmOrchestration(queryWrapper);
		ExcelUtil.export(response, "Algorithm layout table data" + DateUtil.time(), "Algorithm orchestration table data table", list, VlsAlgorithmOrchestrationExcel.class);
	}

}
