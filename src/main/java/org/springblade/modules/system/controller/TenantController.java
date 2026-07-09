package org.springblade.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.secure.annotation.IsAdministrator;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.pojo.entity.TenantPackage;
import org.springblade.modules.system.pojo.vo.TenantVO;
import org.springblade.modules.system.service.ITenantPackageService;
import org.springblade.modules.system.service.ITenantService;
import org.springblade.modules.system.wrapper.TenantWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springblade.common.cache.SysCache.TENANT_PACKAGE_ID;
import static org.springblade.common.cache.SysCache.TENANT_TENANT_ID;
import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;
import static org.springblade.core.tenant.constant.TenantBaseConstant.TENANT_DATASOURCE_CACHE;
import static org.springblade.core.tenant.constant.TenantBaseConstant.TENANT_DATASOURCE_EXIST_KEY;

/**
 * controller
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/tenant")
@Tag(name = "Tenant management", description = "Tenant management")
public class TenantController extends BladeController {

	private final ITenantService tenantService;
	private final ITenantPackageService tenantPackageService;

	/**
	 * Details
	 */
	@IsAdmin
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingtenant")
	public R<Tenant> detail(Tenant tenant) {
		Tenant detail = tenantService.getOne(Condition.getQueryWrapper(tenant));
		return R.data(detail);
	}

	/**
	 * Pagination
	 */
	@IsAdmin
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "tenantId", description = "Parameter name", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "tenantName", description = "role alias", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "contactNumber", description = "Contact number", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingtenant")
	public R<IPage<Tenant>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> tenant, Query query, BladeUser bladeUser) {
		TenantWrapper.build().entityQuery(tenant);
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant, Tenant.class);
		IPage<Tenant> pages = tenantService.page(Condition.getPage(query), (!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Tenant::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(pages);
	}

	/**
	 * Drop down data source
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Drop down data source", description = "incomingtenant")
	public R<List<TenantVO>> select(Tenant tenant, BladeUser bladeUser) {
		LambdaQueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant).lambda();
		queryWrapper.eq(Tenant::getStatus, BladeConstant.DB_STATUS_NORMAL);
		if (!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) {
			queryWrapper.eq(Tenant::getTenantId, bladeUser.getTenantId());
		}
		List<Tenant> list = tenantService.list(queryWrapper);
		return R.data(TenantWrapper.build().listVO(list));
	}

	/**
	 * Custom paging
	 */
	@IsAdmin
	@GetMapping("/page")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Pagination", description = "incomingtenant")
	public R<IPage<Tenant>> page(Tenant tenant, Query query) {
		IPage<Tenant> pages = tenantService.selectTenantPage(Condition.getPage(query), tenant);
		return R.data(pages);
	}

	/**
	 * Add or modify
	 */
	@IsAdministrator
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Add or modify", description = "incomingtenant")
	public R submit(@Valid @RequestBody Tenant tenant) {
		return R.status(tenantService.submitTenant(tenant));
	}

	/**
	 * Delete to recycle bin
	 */
	@IsAdministrator
	@PostMapping("/recycle")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Delete to recycle bin", description = "incomingids")
	public R recycle(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(tenantService.recycleTenant(Func.toLongList(ids)));
	}

	/**
	 * Restore from Recycle Bin
	 */
	@IsAdministrator
	@PostMapping("/pass")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "Restore from Recycle Bin", description = "incomingids")
	public R pass(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(tenantService.passTenant(Func.toLongList(ids)));
	}

	/**
	 * Delete from recycle bin
	 */
	@IsAdministrator
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Delete from recycle bin", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(tenantService.removeTenant(Func.toLongList(ids)));
	}

	/**
	 * Authorization configuration
	 */
	@IsAdministrator
	@PostMapping("/setting")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "Authorization configuration", description = "incomingids,accountNumber,expireTime")
	public R setting(@Parameter(description = "primary key set", required = true) @RequestParam String ids, @Parameter(description = "Account limit") Integer accountNumber, @Parameter(description = "Expiration time") Date expireTime) {
		return R.status(tenantService.setting(accountNumber, expireTime, ids));
	}

	/**
	 * Data source configuration
	 */
	@IsAdministrator
	@PostMapping("datasource")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "Data source configuration", description = "incomingdatasource_id")
	public R datasource(@Parameter(description = "tenantID", required = true) @RequestParam String tenantId, @Parameter(description = "data sourceID", required = true) @RequestParam Long datasourceId) {
		CacheUtil.evict(TENANT_DATASOURCE_CACHE, TENANT_DATASOURCE_EXIST_KEY, tenantId, Boolean.FALSE);
		return R.status(tenantService.update(Wrappers.<Tenant>update().lambda().set(Tenant::getDatasourceId, datasourceId).eq(Tenant::getTenantId, tenantId)));
	}

	/**
	 * Query list by name
	 *
	 * @param name Tenant name
	 */
	@IsAdmin
	@GetMapping("/find-by-name")
	@ApiOperationSupport(order = 11)
	@Operation(summary = "Details", description = "incomingtenant")
	public R<List<Tenant>> findByName(String name) {
		List<Tenant> list = tenantService.list(Wrappers.<Tenant>query().lambda().like(Tenant::getTenantName, name));
		return R.data(list);
	}

	/**
	 * Query information based on domain name
	 *
	 * @param domain domain name
	 */
	@GetMapping("/info")
	@ApiOperationSupport(order = 12)
	@Operation(summary = "Configuration information", description = "incomingdomain")
	public R<Kv> info(String domain) {
		Tenant tenant = tenantService.getOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getDomainUrl, domain));
		Kv kv = Kv.create();
		if (tenant != null) {
			kv.set("tenantId", tenant.getTenantId())
				.set("domain", tenant.getDomainUrl())
				.set("backgroundUrl", tenant.getBackgroundUrl());
		}
		return R.data(kv);
	}

	/**
	 * According to tenantIDCheck product package details
	 *
	 * @param tenantId tenantID
	 */
	@IsAdministrator
	@GetMapping("/package-detail")
	@ApiOperationSupport(order = 13)
	@Operation(summary = "Product package details", description = "incomingtenantId")
	public R<TenantPackage> packageDetail(Long tenantId) {
		Tenant tenant = tenantService.getById(tenantId);
		return R.data(tenantPackageService.getById(tenant.getPackageId()));
	}

	/**
	 * Product package configuration
	 */
	@IsAdministrator
	@PostMapping("/package-setting")
	@ApiOperationSupport(order = 14)
	@Operation(summary = "Product package configuration", description = "incomingpackageId")
	public R packageSetting(@Parameter(description = "tenantID", required = true) @RequestParam String tenantId, @Parameter(description = "product packageID") Long packageId) {
		CacheUtil.evict(SYS_CACHE, TENANT_TENANT_ID, tenantId, Boolean.FALSE);
		CacheUtil.evict(SYS_CACHE, TENANT_PACKAGE_ID, tenantId, Boolean.FALSE);
		return R.status(tenantService.update(Wrappers.<Tenant>update().lambda().set(Tenant::getPackageId, packageId).eq(Tenant::getTenantId, tenantId)));
	}


}
