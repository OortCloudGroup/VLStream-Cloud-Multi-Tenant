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
 * 数据审计表 控制器
 *
 * @author Oort
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/record-data")
@Tag(name = "数据审计表", description = "数据审计表接口")
public class RecordDataController extends BladeController {

	private final IRecordDataService recordDataService;

	/**
	 * 数据审计表 详情
	 */
	@IsAdmin
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description  = "传入recordData")
	public R<RecordDataVO> detail(RecordData recordData) {
		RecordData detail = recordDataService.getOne(Condition.getQueryWrapper(recordData));
		return R.data(RecordDataWrapper.build().entityVO(detail));
	}

	/**
	 * 数据审计表 分页
	 */
	@IsAdmin
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description  = "传入recordData")
	public R<IPage<RecordDataVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> recordData, Query query) {
		IPage<RecordData> pages = recordDataService.page(Condition.getPage(query.setDescs(BladeConstant.DB_PRIMARY_KEY)), Condition.getQueryWrapper(recordData, RecordData.class));
		return R.data(RecordDataWrapper.build().pageVO(pages));
	}

	/**
	 * 数据审计表 新增
	 */
	@IsAdministrator
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增", description  = "传入recordData")
	public R save(@Valid @RequestBody RecordData recordData) {
		return R.status(recordDataService.save(recordData));
	}

	/**
	 * 数据审计表 修改
	 */
	@IsAdministrator
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "修改", description  = "传入recordData")
	public R update(@Valid @RequestBody RecordData recordData) {
		return R.status(recordDataService.updateById(recordData));
	}

	/**
	 * 数据审计表 新增或修改
	 */
	@IsAdministrator
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增或修改", description  = "传入recordData")
	public R submit(@Valid @RequestBody RecordData recordData) {
		return R.status(recordDataService.saveOrUpdate(recordData));
	}

	/**
	 * 数据审计表 删除
	 */
	@IsAdministrator
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "删除", description  = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(recordDataService.removeByIds(Func.toLongList(ids)));
	}

}
