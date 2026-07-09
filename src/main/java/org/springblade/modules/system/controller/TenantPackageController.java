package org.springblade.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.IsAdministrator;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.TenantPackage;
import org.springblade.modules.system.service.ITenantPackageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * Tenant product table controller
 *
 * @author Oort
 */
@RestController
@AllArgsConstructor
@IsAdministrator
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/tenant-package")
@Tag(name = "Tenant product package", description = "Tenant product package")
public class TenantPackageController extends BladeController {

	private final ITenantPackageService tenantPackageService;

	/**
	 * Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingtenantPackage")
	public R<TenantPackage> detail(TenantPackage tenantPackage) {
		TenantPackage detail = tenantPackageService.getOne(Condition.getQueryWrapper(tenantPackage));
		return R.data(detail);
	}

	/**
	 * Pagination Tenant product table
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "packageName", description = "Product package name", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingtenantPackage")
	public R<IPage<TenantPackage>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> tenantPackage, Query query) {
		IPage<TenantPackage> pages = tenantPackageService.page(Condition.getPage(query), Condition.getQueryWrapper(tenantPackage, TenantPackage.class));
		return R.data(pages);
	}

	/**
	 * New Tenant product table
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "New", description = "incomingtenantPackage")
	public R save(@Valid @RequestBody TenantPackage tenantPackage) {
		return R.status(tenantPackageService.save(tenantPackage));
	}

	/**
	 * Revise Tenant product table
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Revise", description = "incomingtenantPackage")
	public R update(@Valid @RequestBody TenantPackage tenantPackage) {
		return R.status(tenantPackageService.updateById(tenantPackage));
	}

	/**
	 * Add or modify Tenant product table
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Add or modify", description = "incomingtenantPackage")
	public R submit(@Valid @RequestBody TenantPackage tenantPackage) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(tenantPackageService.saveOrUpdate(tenantPackage));
	}


	/**
	 * delete Tenant product table
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(tenantPackageService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * Drop down data source
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "Drop down data source", description = "incomingtenant")
	public R<List<TenantPackage>> select(TenantPackage tenantPackage) {
		return R.data(tenantPackageService.list(Condition.getQueryWrapper(tenantPackage)));
	}


}
