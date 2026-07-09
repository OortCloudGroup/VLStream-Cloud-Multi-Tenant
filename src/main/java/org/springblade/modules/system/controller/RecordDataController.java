package org.springblade.modules.system.controller;

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
import org.springblade.core.secure.annotation.IsAdministrator;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.RecordData;
import org.springblade.modules.system.pojo.vo.RecordDataVO;
import org.springblade.modules.system.service.IRecordDataService;
import org.springblade.modules.system.wrapper.RecordDataWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Data audit table controller
 *
 * @author Oort
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/record-data")
@Tag(name = "Data audit table", description = "Data audit table interface")
public class RecordDataController extends BladeController {

	private final IRecordDataService recordDataService;

	/**
	 * Data audit table Details
	 */
	@IsAdmin
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description  = "incomingrecordData")
	public R<RecordDataVO> detail(RecordData recordData) {
		RecordData detail = recordDataService.getOne(Condition.getQueryWrapper(recordData));
		return R.data(RecordDataWrapper.build().entityVO(detail));
	}

	/**
	 * Data audit table Pagination
	 */
	@IsAdmin
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description  = "incomingrecordData")
	public R<IPage<RecordDataVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> recordData, Query query) {
		IPage<RecordData> pages = recordDataService.page(Condition.getPage(query.setDescs(BladeConstant.DB_PRIMARY_KEY)), Condition.getQueryWrapper(recordData, RecordData.class));
		return R.data(RecordDataWrapper.build().pageVO(pages));
	}

	/**
	 * Data audit table New
	 */
	@IsAdministrator
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "New", description  = "incomingrecordData")
	public R save(@Valid @RequestBody RecordData recordData) {
		return R.status(recordDataService.save(recordData));
	}

	/**
	 * Data audit table Revise
	 */
	@IsAdministrator
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Revise", description  = "incomingrecordData")
	public R update(@Valid @RequestBody RecordData recordData) {
		return R.status(recordDataService.updateById(recordData));
	}

	/**
	 * Data audit table Add or modify
	 */
	@IsAdministrator
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Add or modify", description  = "incomingrecordData")
	public R submit(@Valid @RequestBody RecordData recordData) {
		return R.status(recordDataService.saveOrUpdate(recordData));
	}

	/**
	 * Data audit table delete
	 */
	@IsAdministrator
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "delete", description  = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(recordDataService.removeByIds(Func.toLongList(ids)));
	}

}
