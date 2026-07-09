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
import org.springblade.vlstream.pojo.entity.RemoteServers;
import org.springblade.vlstream.pojo.vo.RemoteServersVO;
import org.springblade.vlstream.excel.VlsRemoteServersExcel;
import org.springblade.vlstream.wrapper.VlsRemoteServersWrapper;
import org.springblade.vlstream.service.IVlsRemoteServersService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.excel.util.ExcelUtil;

import java.util.Map;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Remote server configuration table controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsRemoteServers")
@Tag(name = "Remote server configuration table", description = "Remote server configuration table interface")
public class VlsRemoteServersController extends BladeController {

	private final IVlsRemoteServersService vlsRemoteServersService;

	/**
	 * Remote server configuration table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description  = "incomingvlsRemoteServers")
	public R<RemoteServersVO> detail(RemoteServers vlsRemoteServers) {
		RemoteServers detail = vlsRemoteServersService.getOne(Condition.getQueryWrapper(vlsRemoteServers));
		return R.data(VlsRemoteServersWrapper.build().entityVO(detail));
	}

	/**
	 * Remote server configuration table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description  = "incomingvlsRemoteServers")
	public R<IPage<RemoteServersVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsRemoteServers, Query query) {
		IPage<RemoteServers> pages = vlsRemoteServersService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsRemoteServers, RemoteServers.class));
		return R.data(VlsRemoteServersWrapper.build().pageVO(pages));
	}


	/**
	 * Remote server configuration table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description  = "incomingvlsRemoteServers")
	public R<IPage<RemoteServersVO>> page(RemoteServersVO vlsRemoteServers, Query query) {
		IPage<RemoteServersVO> pages = vlsRemoteServersService.selectVlsRemoteServersPage(Condition.getPage(query), vlsRemoteServers);
		return R.data(pages);
	}

	/**
	 * Remote server configuration table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description  = "incomingvlsRemoteServers")
	public R save(@Valid @RequestBody RemoteServers vlsRemoteServers) {
		return R.status(vlsRemoteServersService.save(vlsRemoteServers));
	}

	/**
	 * Remote server configuration table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description  = "incomingvlsRemoteServers")
	public R update(@Valid @RequestBody RemoteServers vlsRemoteServers) {
		return R.status(vlsRemoteServersService.updateById(vlsRemoteServers));
	}

	/**
	 * Remote server configuration table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description  = "incomingvlsRemoteServers")
	public R submit(@Valid @RequestBody RemoteServers vlsRemoteServers) {
		return R.status(vlsRemoteServersService.saveOrUpdate(vlsRemoteServers));
	}

	/**
	 * Remote server configuration table delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description  = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsRemoteServersService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsRemoteServers")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description  = "incomingvlsRemoteServers")
	public void exportVlsRemoteServers(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsRemoteServers, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<RemoteServers> queryWrapper = Condition.getQueryWrapper(vlsRemoteServers, RemoteServers.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsRemoteServersEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsRemoteServersEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsRemoteServersExcel> list = vlsRemoteServersService.exportVlsRemoteServers(queryWrapper);
		ExcelUtil.export(response, "Remote server configuration table data" + DateUtil.time(), "Remote server configuration table data table", list, VlsRemoteServersExcel.class);
	}

}
