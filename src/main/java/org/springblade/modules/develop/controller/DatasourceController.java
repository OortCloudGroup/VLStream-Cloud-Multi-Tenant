/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2018-2099, https://bladex.cn. All rights reserved.
 * <p>
 * Use of this software is governed by the Commercial License Agreement
 * obtained after purchasing a license from BladeX.
 * <p>
 * 1. This software is for development use only under a valid license
 * from BladeX.
 * <p>
 * 2. Redistribution of this software's source code to any third party
 * without a commercial license is strictly prohibited.
 * <p>
 * 3. Licensees may copyright their own code but cannot use segments
 * from this software for such purposes. Copyright of this software
 * remains with BladeX.
 * <p>
 * Using this software signifies agreement to this License, and the software
 * must not be used for illegal purposes.
 * <p>
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY. The author is
 * not liable for any claims arising from secondary or illegal development.
 * <p>
 * Author: Chill Zhuang (bladejava@qq.com)
 */
package org.springblade.modules.develop.controller;

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
import org.springblade.core.xss.annotation.XssIgnore;
import org.springblade.modules.develop.pojo.entity.Datasource;
import org.springblade.modules.develop.service.IDatasourceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Data source configuration table controller
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@IsAdministrator
@RequestMapping(AppConstant.APPLICATION_DEVELOP_NAME + "/datasource")
@Tag(name = "Data source configuration table", description = "Data source configuration table interface")
public class DatasourceController extends BladeController {

	private final IDatasourceService datasourceService;

	/**
	 * Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingdatasource")
	public R<Datasource> detail(Datasource datasource) {
		Datasource detail = datasourceService.getOne(Condition.getQueryWrapper(datasource));
		return R.data(detail);
	}

	/**
	 * Pagination Data source configuration table
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingdatasource")
	public R<IPage<Datasource>> list(Datasource datasource, Query query) {
		IPage<Datasource> pages = datasourceService.page(Condition.getPage(query), Condition.getQueryWrapper(datasource));
		return R.data(pages);
	}

	/**
	 * New Data source configuration table
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingdatasource")
	public R save(@Valid @RequestBody Datasource datasource) {
		return R.status(datasourceService.save(datasource));
	}

	/**
	 * Revise Data source configuration table
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingdatasource")
	public R update(@Valid @RequestBody Datasource datasource) {
		return R.status(datasourceService.updateById(datasource));
	}

	/**
	 * Add or modify Data source configuration table
	 */
	@XssIgnore
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingdatasource")
	public R submit(@Valid @RequestBody Datasource datasource) {
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
	public R<List<Datasource>> select() {
		List<Datasource> list = datasourceService.list();
		return R.data(list);
	}

}
