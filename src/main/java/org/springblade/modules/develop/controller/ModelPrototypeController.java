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
import org.springblade.modules.develop.pojo.entity.ModelPrototype;
import org.springblade.modules.develop.service.IModelPrototypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据原型表 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@IsAdministrator
@RequestMapping(AppConstant.APPLICATION_DEVELOP_NAME + "/model-prototype")
@Tag(name = "数据原型表", description = "数据原型表接口")
public class ModelPrototypeController extends BladeController {

	private final IModelPrototypeService modelPrototypeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入modelPrototype")
	public R<ModelPrototype> detail(ModelPrototype modelPrototype) {
		ModelPrototype detail = modelPrototypeService.getOne(Condition.getQueryWrapper(modelPrototype));
		return R.data(detail);
	}

	/**
	 * 分页 数据原型表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入modelPrototype")
	public R<IPage<ModelPrototype>> list(ModelPrototype modelPrototype, Query query) {
		IPage<ModelPrototype> pages = modelPrototypeService.page(Condition.getPage(query), Condition.getQueryWrapper(modelPrototype));
		return R.data(pages);
	}

	/**
	 * 新增 数据原型表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入modelPrototype")
	public R save(@Valid @RequestBody ModelPrototype modelPrototype) {
		return R.status(modelPrototypeService.save(modelPrototype));
	}

	/**
	 * 修改 数据原型表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入modelPrototype")
	public R update(@Valid @RequestBody ModelPrototype modelPrototype) {
		return R.status(modelPrototypeService.updateById(modelPrototype));
	}

	/**
	 * 新增或修改 数据原型表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入modelPrototype")
	public R submit(@Valid @RequestBody ModelPrototype modelPrototype) {
		return R.status(modelPrototypeService.saveOrUpdate(modelPrototype));
	}

	/**
	 * 批量新增或修改 数据原型表
	 */
	@PostMapping("/submit-list")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "批量新增或修改", description = "传入modelPrototype集合")
	public R submitList(@Valid @RequestBody List<ModelPrototype> modelPrototypes) {
		return R.status(modelPrototypeService.submitList(modelPrototypes));
	}

	/**
	 * 删除 数据原型表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(modelPrototypeService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 数据原型列表
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "数据原型列表", description = "数据原型列表")
	public R<List<ModelPrototype>> select(@Parameter(description = "数据模型Id", required = true) @RequestParam Long modelId) {
		List<ModelPrototype> list = modelPrototypeService.list(Wrappers.<ModelPrototype>query().lambda().eq(ModelPrototype::getModelId, modelId));
		list.forEach(prototype -> prototype.setJdbcComment(prototype.getJdbcName() + StringPool.COLON + StringPool.SPACE + prototype.getJdbcComment()));
		return R.data(list);
	}

}
