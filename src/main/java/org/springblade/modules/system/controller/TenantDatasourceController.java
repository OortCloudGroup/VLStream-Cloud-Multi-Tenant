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
 * 多租户数据源表 控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@IsAdministrator
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/tenant-datasource")
@Tag(name = "多租户数据源表", description = "多租户数据源表接口")
public class TenantDatasourceController extends BladeController {

	private final ITenantDatasourceService datasourceService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入datasource")
	public R<TenantDatasource> detail(TenantDatasource datasource) {
		TenantDatasource detail = datasourceService.getOne(Condition.getQueryWrapper(datasource));
		return R.data(detail);
	}

	/**
	 * 分页 数据源配置表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入datasource")
	public R<IPage<TenantDatasource>> list(TenantDatasource datasource, Query query) {
		IPage<TenantDatasource> pages = datasourceService.page(Condition.getPage(query), Condition.getQueryWrapper(datasource));
		return R.data(pages);
	}

	/**
	 * 新增 数据源配置表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入datasource")
	public R save(@Valid @RequestBody TenantDatasource datasource) {
		return R.status(datasourceService.save(datasource));
	}

	/**
	 * 修改 数据源配置表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入datasource")
	public R update(@Valid @RequestBody TenantDatasource datasource) {
		return R.status(datasourceService.updateById(datasource));
	}

	/**
	 * 新增或修改 数据源配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入datasource")
	public R submit(@Valid @RequestBody TenantDatasource datasource) {
		if (StringUtil.isNotBlank(datasource.getUrl())) {
			datasource.setUrl(datasource.getUrl().replace("&amp;", "&"));
		}
		return R.status(datasourceService.saveOrUpdate(datasource));
	}


	/**
	 * 删除 数据源配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(datasourceService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 数据源列表
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "下拉数据源", description = "查询列表")
	public R<List<TenantDatasource>> select() {
		List<TenantDatasource> list = datasourceService.list();
		return R.data(list);
	}

}
