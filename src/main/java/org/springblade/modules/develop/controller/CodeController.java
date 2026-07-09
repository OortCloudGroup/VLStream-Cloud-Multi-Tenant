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
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springblade.modules.develop.pojo.dto.GeneratorDTO;
import org.springblade.modules.develop.pojo.entity.Code;
import org.springblade.modules.develop.service.ICodeService;
import org.springblade.modules.develop.service.IGenerateService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * controller
 *
 * @author Chill
 */
@NonDS
@Hidden
@RestController
@AllArgsConstructor
@IsAdministrator
@RequestMapping(AppConstant.APPLICATION_DEVELOP_NAME + "/code")
@Tag(name = "code generation", description = "code generation")
public class CodeController extends BladeController {

	private final ICodeService codeService;
	private final IGenerateService generateService;

	/**
	 * Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingcode")
	public R<Code> detail(Code code) {
		Code detail = codeService.getOne(Condition.getQueryWrapper(code));
		return R.data(detail);
	}

	/**
	 * Pagination
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "codeName", description = "module name", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "tableName", description = "table name", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "modelName", description = "Entity name", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingcode")
	public R<IPage<Code>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> code, Query query) {
		IPage<Code> pages = codeService.page(Condition.getPage(query), Condition.getQueryWrapper(code, Code.class));
		return R.data(pages);
	}

	/**
	 * Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Add or modify", description = "incomingcode")
	public R submit(@Valid @RequestBody Code code) {
		return R.status(codeService.submit(code));
	}


	/**
	 * delete
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "delete", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(codeService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * copy
	 */
	@PostMapping("/copy")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "copy", description = "incomingid")
	public R copy(@Parameter(description = "primary key", required = true) @RequestParam Long id) {
		Code code = codeService.getById(id);
		code.setId(null);
		code.setCodeName(code.getCodeName() + "-copy");
		return R.status(codeService.save(code));
	}

	/**
	 * code generation
	 */
	@PostMapping("/gen-code")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "code generation", description = "incomingids")
	public R genCode(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(generateService.code(Func.toLongList(ids)));
	}

	/**
	 * code generation
	 */
	@PostMapping("/gen-code-fast")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "Quick code generation", description = "Pass in configuration collection")
	public R genCodeFast(@Parameter(description = "primary key set", required = true) @RequestBody GeneratorDTO dto) {
		return R.status(generateService.codeFast(dto));
	}

}
