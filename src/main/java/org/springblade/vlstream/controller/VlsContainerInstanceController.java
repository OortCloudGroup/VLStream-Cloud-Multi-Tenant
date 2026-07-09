package org.springblade.vlstream.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.vlstream.excel.VlsContainerInstanceExcel;
import org.springblade.vlstream.pojo.entity.ContainerInstance;
import org.springblade.vlstream.pojo.vo.ContainerInstanceVO;
import org.springblade.vlstream.service.IVlsContainerInstanceService;
import org.springblade.vlstream.wrapper.VlsContainerInstanceWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Container instance table controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsContainerInstance")
@Tag(name = "Container instance table", description = "Container instance table interface")
public class VlsContainerInstanceController extends BladeController {

	private final IVlsContainerInstanceService vlsContainerInstanceService;

	/**
	 * Container instance table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description  = "incomingvlsContainerInstance")
	public R<ContainerInstanceVO> detail(ContainerInstance vlsContainerInstance) {
		ContainerInstance detail = vlsContainerInstanceService.getOne(Condition.getQueryWrapper(vlsContainerInstance));
		return R.data(VlsContainerInstanceWrapper.build().entityVO(detail));
	}

	/**
	 * Container instance table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description  = "incomingvlsContainerInstance")
	public R<IPage<ContainerInstanceVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsContainerInstance, Query query) {
		IPage<ContainerInstance> pages = vlsContainerInstanceService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsContainerInstance, ContainerInstance.class));
		return R.data(VlsContainerInstanceWrapper.build().pageVO(pages));
	}


	/**
	 * Container instance table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description  = "incomingvlsContainerInstance")
	public R<IPage<ContainerInstanceVO>> page(ContainerInstanceVO vlsContainerInstance, Query query) {
		IPage<ContainerInstanceVO> pages = vlsContainerInstanceService.selectVlsContainerInstancePage(Condition.getPage(query), vlsContainerInstance);
		return R.data(pages);
	}

	/**
	 * Container instance table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description  = "incomingvlsContainerInstance")
	public R save(@Valid @RequestBody ContainerInstance vlsContainerInstance) {
		return R.status(vlsContainerInstanceService.save(vlsContainerInstance));
	}

	/**
	 * Container instance table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description  = "incomingvlsContainerInstance")
	public R update(@Valid @RequestBody ContainerInstance vlsContainerInstance) {
		return R.status(vlsContainerInstanceService.updateById(vlsContainerInstance));
	}

	/**
	 * Container instance table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description  = "incomingvlsContainerInstance")
	public R submit(@Valid @RequestBody ContainerInstance vlsContainerInstance) {
		return R.status(vlsContainerInstanceService.saveOrUpdate(vlsContainerInstance));
	}

	/**
	 * Container instance table delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description  = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsContainerInstanceService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsContainerInstance")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description  = "incomingvlsContainerInstance")
	public void exportVlsContainerInstance(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsContainerInstance, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<ContainerInstance> queryWrapper = Condition.getQueryWrapper(vlsContainerInstance, ContainerInstance.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsContainerInstanceEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsContainerInstanceEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsContainerInstanceExcel> list = vlsContainerInstanceService.exportVlsContainerInstance(queryWrapper);
		ExcelUtil.export(response, "Container instance table data" + DateUtil.time(), "Container instance table data table", list, VlsContainerInstanceExcel.class);
	}

}
