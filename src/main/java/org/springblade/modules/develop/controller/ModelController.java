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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
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
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.develop.pojo.entity.Datasource;
import org.springblade.modules.develop.pojo.entity.Model;
import org.springblade.modules.develop.pojo.entity.ModelPrototype;
import org.springblade.modules.develop.service.IDatasourceService;
import org.springblade.modules.develop.service.IModelPrototypeService;
import org.springblade.modules.develop.service.IModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data model table controller
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@IsAdministrator
@RequestMapping(AppConstant.APPLICATION_DEVELOP_NAME + "/model")
@Tag(name = "Data model table", description = "Data model table interface")
public class ModelController extends BladeController {

	private final IModelService modelService;
	private final IModelPrototypeService modelPrototypeService;
	private final IDatasourceService datasourceService;

	/**
	 * Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingmodel")
	public R<Model> detail(Model model) {
		Model detail = modelService.getOne(Condition.getQueryWrapper(model));
		return R.data(detail);
	}

	/**
	 * Pagination Data model table
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingmodel")
	public R<IPage<Model>> list(Model model, Query query) {
		IPage<Model> pages = modelService.page(Condition.getPage(query), Condition.getQueryWrapper(model));
		return R.data(pages);
	}

	/**
	 * New Data model table
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "New", description = "incomingmodel")
	public R save(@Valid @RequestBody Model model) {
		return R.status(modelService.save(model));
	}

	/**
	 * Revise Data model table
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Revise", description = "incomingmodel")
	public R update(@Valid @RequestBody Model model) {
		return R.status(modelService.updateById(model));
	}

	/**
	 * Add or modify Data model table
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Add or modify", description = "incomingmodel")
	public R submit(@Valid @RequestBody Model model) {
		boolean temp = modelService.saveOrUpdate(model);
		if (temp) {
			return R.data(model);
		} else {
			return R.status(Boolean.FALSE);
		}
	}

	/**
	 * delete Data model table
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(modelService.delete(Func.toLongList(ids)));
	}

	/**
	 * Model list
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "Model list", description = "Model list")
	public R<List<Model>> select() {
		List<Model> list = modelService.list();
		list.forEach(model -> model.setModelName(model.getModelTable() + StringPool.COLON + StringPool.SPACE + model.getModelName()));
		return R.data(list);
	}

	/**
	 * Get physical table list
	 */
	@GetMapping("/table-list")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Physical table list", description = "incomingdatasourceId")
	public R<List<TableInfo>> tableList(Long datasourceId) {
		Datasource datasource = datasourceService.getById(datasourceId);
		ConfigBuilder config = modelPrototypeService.getConfigBuilder(datasource);
		List<TableInfo> tableInfoList = config.getTableInfoList().stream()
			.filter(tableInfo -> !StringUtil.startsWithIgnoreCase(tableInfo.getName(), "ACT_") && !StringUtil.startsWithIgnoreCase(tableInfo.getName(), "FLW_"))
			.map(tableInfo -> tableInfo.setComment(tableInfo.getName() + StringPool.COLON + tableInfo.getComment()))
			.collect(Collectors.toList());
		return R.data(tableInfoList);
	}

	/**
	 * Get physical table information
	 */
	@GetMapping("/table-info")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "Physical table information", description = "incomingmodelinformation")
	public R<TableInfo> tableInfo(Long modelId, String tableName, Long datasourceId) {
		if (StringUtil.isBlank(tableName)) {
			Model model = modelService.getById(modelId);
			tableName = model.getModelTable();
		}
		TableInfo tableInfo = modelPrototypeService.getTableInfo(tableName, datasourceId);
		return R.data(tableInfo);
	}

	/**
	 * Get field information
	 */
	@GetMapping("/model-prototype")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "Physical table field information", description = "incomingmodelIdanddatasourceId")
	public R modelPrototype(Long modelId, Long datasourceId) {
		List<ModelPrototype> modelPrototypeList = modelPrototypeService.list(Wrappers.<ModelPrototype>query().lambda().eq(ModelPrototype::getModelId, modelId));
		if (!modelPrototypeList.isEmpty()) {
			return R.data(modelPrototypeList);
		}
		Model model = modelService.getById(modelId);
		String tableName = model.getModelTable();
		TableInfo tableInfo = modelPrototypeService.getTableInfo(tableName, datasourceId);
		if (tableInfo != null) {
			return R.data(tableInfo.getFields());
		} else {
			return R.fail("No relevant table information was obtained");
		}
	}

}
