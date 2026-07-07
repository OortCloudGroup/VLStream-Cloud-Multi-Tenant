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
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.xss.annotation.XssIgnore;
import org.springblade.modules.develop.pojo.entity.CodeSetting;
import org.springblade.modules.develop.service.ICodeSettingService;
import org.springblade.modules.develop.service.IModelPrototypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器配置表 控制器
 *
 * @author Oort
 */
@RestController
@AllArgsConstructor
@IsAdministrator
@RequestMapping(AppConstant.APPLICATION_DEVELOP_NAME + "/code-setting")
@Tag(name = "代码生成器配置表", description = "代码生成器配置表接口")
public class CodeSettingController extends BladeController {

	private final ICodeSettingService codeSettingService;
	private final IModelPrototypeService modelPrototypeService;

	/**
	 * 代码生成器配置表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入codeSetting")
	public R<CodeSetting> detail(CodeSetting codeSetting) {
		CodeSetting detail = codeSettingService.getOne(Condition.getQueryWrapper(codeSetting));
		return R.data(detail);
	}

	/**
	 * 代码生成器配置表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入codeSetting")
	public R<IPage<CodeSetting>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> codeSetting, Query query) {
		IPage<CodeSetting> pages = codeSettingService.page(Condition.getPage(query), Condition.getQueryWrapper(codeSetting, CodeSetting.class).orderByDesc("id"));
		return R.data(pages);
	}

	/**
	 * 代码生成器配置表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增", description = "传入codeSetting")
	public R save(@Valid @RequestBody CodeSetting codeSetting) {
		return R.status(codeSettingService.save(codeSetting));
	}

	/**
	 * 代码生成器配置表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "修改", description = "传入codeSetting")
	public R update(@Valid @RequestBody CodeSetting codeSetting) {
		return R.status(codeSettingService.updateById(codeSetting));
	}

	/**
	 * 代码生成器配置表 新增或修改
	 */
	@XssIgnore
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增或修改", description = "传入codeSetting")
	public R submit(@Valid @RequestBody CodeSetting codeSetting) {
		boolean temp = codeSettingService.saveOrUpdate(codeSetting);
		if (temp) {
			return R.data(codeSetting);
		} else {
			return R.status(Boolean.FALSE);
		}
	}

	/**
	 * 代码生成器配置表 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(codeSettingService.removeByIds(Func.toLongList(ids)));
	}


	/**
	 * 代码生成器配置表 启用
	 */
	@PostMapping("/enable")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "配置启用", description = "传入id")
	public R enable(@Parameter(description = "主键", required = true) @RequestParam Long id) {
		return R.status(codeSettingService.enable(id));
	}

	/**
	 * 代码生成器配置表 启用详情
	 */
	@GetMapping("/enable-detail")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "详情", description = "传入codeSetting")
	public R<CodeSetting> enableDetail() {
		CodeSetting detail = codeSettingService.getOne(Wrappers.<CodeSetting>lambdaQuery().eq(CodeSetting::getStatus, BladeConstant.DB_STATUS_2).eq(CodeSetting::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		return R.data(detail);
	}

	/**
	 * 表单设计器选择
	 */
	@GetMapping("/table-form")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "表单设计器选择", description = "tableName")
	public R<List<CodeSetting>> formSelect(String tableName) {
		return R.data(codeSettingService.list(Wrappers.<CodeSetting>lambdaQuery().eq(CodeSetting::getCode, tableName).eq(CodeSetting::getCategory, 2)));
	}


	/**
	 * 获取字段信息
	 */
	@GetMapping("/table-prototype")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "物理表字段信息", description = "传入tableName与datasourceId")
	public R tablePrototype(String tableName, Long datasourceId) {
		TableInfo tableInfo = modelPrototypeService.getTableInfo(tableName, datasourceId);
		if (tableInfo != null) {
			return R.data(tableInfo.getFields());
		} else {
			return R.fail("未获得相关表信息");
		}
	}
}
