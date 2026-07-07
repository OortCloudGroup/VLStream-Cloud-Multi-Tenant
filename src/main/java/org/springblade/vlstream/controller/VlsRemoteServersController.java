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
 * 远程服务器配置表 控制器
 *
 * @author Oort
 * @since 2025-12-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsRemoteServers")
@Tag(name = "远程服务器配置表", description = "远程服务器配置表接口")
public class VlsRemoteServersController extends BladeController {

	private final IVlsRemoteServersService vlsRemoteServersService;

	/**
	 * 远程服务器配置表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description  = "传入vlsRemoteServers")
	public R<RemoteServersVO> detail(RemoteServers vlsRemoteServers) {
		RemoteServers detail = vlsRemoteServersService.getOne(Condition.getQueryWrapper(vlsRemoteServers));
		return R.data(VlsRemoteServersWrapper.build().entityVO(detail));
	}

	/**
	 * 远程服务器配置表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description  = "传入vlsRemoteServers")
	public R<IPage<RemoteServersVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsRemoteServers, Query query) {
		IPage<RemoteServers> pages = vlsRemoteServersService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsRemoteServers, RemoteServers.class));
		return R.data(VlsRemoteServersWrapper.build().pageVO(pages));
	}


	/**
	 * 远程服务器配置表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description  = "传入vlsRemoteServers")
	public R<IPage<RemoteServersVO>> page(RemoteServersVO vlsRemoteServers, Query query) {
		IPage<RemoteServersVO> pages = vlsRemoteServersService.selectVlsRemoteServersPage(Condition.getPage(query), vlsRemoteServers);
		return R.data(pages);
	}

	/**
	 * 远程服务器配置表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description  = "传入vlsRemoteServers")
	public R save(@Valid @RequestBody RemoteServers vlsRemoteServers) {
		return R.status(vlsRemoteServersService.save(vlsRemoteServers));
	}

	/**
	 * 远程服务器配置表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description  = "传入vlsRemoteServers")
	public R update(@Valid @RequestBody RemoteServers vlsRemoteServers) {
		return R.status(vlsRemoteServersService.updateById(vlsRemoteServers));
	}

	/**
	 * 远程服务器配置表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description  = "传入vlsRemoteServers")
	public R submit(@Valid @RequestBody RemoteServers vlsRemoteServers) {
		return R.status(vlsRemoteServersService.saveOrUpdate(vlsRemoteServers));
	}

	/**
	 * 远程服务器配置表 删除
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description  = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(vlsRemoteServersService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 导出数据
	 */
	@IsAdmin
	@GetMapping("/export-vlsRemoteServers")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "导出数据", description  = "传入vlsRemoteServers")
	public void exportVlsRemoteServers(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsRemoteServers, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<RemoteServers> queryWrapper = Condition.getQueryWrapper(vlsRemoteServers, RemoteServers.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsRemoteServersEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsRemoteServersEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsRemoteServersExcel> list = vlsRemoteServersService.exportVlsRemoteServers(queryWrapper);
		ExcelUtil.export(response, "远程服务器配置表数据" + DateUtil.time(), "远程服务器配置表数据表", list, VlsRemoteServersExcel.class);
	}

}
