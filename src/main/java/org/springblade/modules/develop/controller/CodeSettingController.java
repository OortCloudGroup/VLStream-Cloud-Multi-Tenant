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
 * Code generator configuration table controller
 *
 * @author Oort
 */
@RestController
@AllArgsConstructor
@IsAdministrator
@RequestMapping(AppConstant.APPLICATION_DEVELOP_NAME + "/code-setting")
@Tag(name = "Code generator configuration table", description = "Code generator configuration table interface")
public class CodeSettingController extends BladeController {

	private final ICodeSettingService codeSettingService;
	private final IModelPrototypeService modelPrototypeService;

	/**
	 * Code generator configuration table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingcodeSetting")
	public R<CodeSetting> detail(CodeSetting codeSetting) {
		CodeSetting detail = codeSettingService.getOne(Condition.getQueryWrapper(codeSetting));
		return R.data(detail);
	}

	/**
	 * Code generator configuration table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingcodeSetting")
	public R<IPage<CodeSetting>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> codeSetting, Query query) {
		IPage<CodeSetting> pages = codeSettingService.page(Condition.getPage(query), Condition.getQueryWrapper(codeSetting, CodeSetting.class).orderByDesc("id"));
		return R.data(pages);
	}

	/**
	 * Code generator configuration table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "New", description = "incomingcodeSetting")
	public R save(@Valid @RequestBody CodeSetting codeSetting) {
		return R.status(codeSettingService.save(codeSetting));
	}

	/**
	 * Code generator configuration table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Revise", description = "incomingcodeSetting")
	public R update(@Valid @RequestBody CodeSetting codeSetting) {
		return R.status(codeSettingService.updateById(codeSetting));
	}

	/**
	 * Code generator configuration table Add or modify
	 */
	@XssIgnore
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Add or modify", description = "incomingcodeSetting")
	public R submit(@Valid @RequestBody CodeSetting codeSetting) {
		boolean temp = codeSettingService.saveOrUpdate(codeSetting);
		if (temp) {
			return R.data(codeSetting);
		} else {
			return R.status(Boolean.FALSE);
		}
	}

	/**
	 * Code generator configuration table delete
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "delete", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(codeSettingService.removeByIds(Func.toLongList(ids)));
	}


	/**
	 * Code generator configuration table enable
	 */
	@PostMapping("/enable")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "Configuration enabled", description = "incomingid")
	public R enable(@Parameter(description = "primary key", required = true) @RequestParam Long id) {
		return R.status(codeSettingService.enable(id));
	}

	/**
	 * Code generator configuration table Enable details
	 */
	@GetMapping("/enable-detail")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Details", description = "incomingcodeSetting")
	public R<CodeSetting> enableDetail() {
		CodeSetting detail = codeSettingService.getOne(Wrappers.<CodeSetting>lambdaQuery().eq(CodeSetting::getStatus, BladeConstant.DB_STATUS_2).eq(CodeSetting::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		return R.data(detail);
	}

	/**
	 * Form designer selection
	 */
	@GetMapping("/table-form")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "Form designer selection", description = "tableName")
	public R<List<CodeSetting>> formSelect(String tableName) {
		return R.data(codeSettingService.list(Wrappers.<CodeSetting>lambdaQuery().eq(CodeSetting::getCode, tableName).eq(CodeSetting::getCategory, 2)));
	}


	/**
	 * Get field information
	 */
	@GetMapping("/table-prototype")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "Physical table field information", description = "incomingtableNameanddatasourceId")
	public R tablePrototype(String tableName, Long datasourceId) {
		TableInfo tableInfo = modelPrototypeService.getTableInfo(tableName, datasourceId);
		if (tableInfo != null) {
			return R.data(tableInfo.getFields());
		} else {
			return R.fail("No relevant table information was obtained");
		}
	}
}
