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
import org.springblade.core.secure.annotation.IsAdministrator;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.system.pojo.entity.TenantDatasource;
import org.springblade.modules.system.service.ITenantDatasourceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Multi-tenant data source table controller
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@IsAdministrator
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/tenant-datasource")
@Tag(name = "Multi-tenant data source table", description = "Multi-tenant data source table interface")
public class TenantDatasourceController extends BladeController {

	private final ITenantDatasourceService datasourceService;

	/**
	 * Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingdatasource")
	public R<TenantDatasource> detail(TenantDatasource datasource) {
		TenantDatasource detail = datasourceService.getOne(Condition.getQueryWrapper(datasource));
		return R.data(detail);
	}

	/**
	 * Pagination Data source configuration table
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingdatasource")
	public R<IPage<TenantDatasource>> list(TenantDatasource datasource, Query query) {
		IPage<TenantDatasource> pages = datasourceService.page(Condition.getPage(query), Condition.getQueryWrapper(datasource));
		return R.data(pages);
	}

	/**
	 * New Data source configuration table
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingdatasource")
	public R save(@Valid @RequestBody TenantDatasource datasource) {
		return R.status(datasourceService.save(datasource));
	}

	/**
	 * Revise Data source configuration table
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingdatasource")
	public R update(@Valid @RequestBody TenantDatasource datasource) {
		return R.status(datasourceService.updateById(datasource));
	}

	/**
	 * Add or modify Data source configuration table
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingdatasource")
	public R submit(@Valid @RequestBody TenantDatasource datasource) {
		if (StringUtil.isNotBlank(datasource.getUrl())) {
			datasource.setUrl(datasource.getUrl().replace("&amp;", "&"));
		}
		return R.status(datasourceService.saveOrUpdate(datasource));
	}


	/**
	 * delete Data source configuration table
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(datasourceService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Data source list
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Drop down data source", description = "query list")
	public R<List<TenantDatasource>> select() {
		List<TenantDatasource> list = datasourceService.list();
		return R.data(list);
	}

}
