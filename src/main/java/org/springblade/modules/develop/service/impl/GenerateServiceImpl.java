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
package org.springblade.modules.develop.service.impl;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import lombok.RequiredArgsConstructor;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.develop.constant.DevelopConstant;
import org.springblade.develop.support.BladeCodeGenerator;
import org.springblade.modules.develop.pojo.dto.GeneratorDTO;
import org.springblade.modules.develop.pojo.entity.*;
import org.springblade.modules.develop.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service implementation class
 *
 * @author Chill
 */
//@Master
@Service
@RequiredArgsConstructor
public class GenerateServiceImpl implements IGenerateService {

	private final ICodeService codeService;
	private final ICodeSettingService codeSettingService;
	private final IDatasourceService datasourceService;
	private final IModelService modelService;
	private final IModelPrototypeService modelPrototypeService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean code(List<Long> ids) {
		Collection<Code> codes = codeService.listByIds(ids);
		codes.forEach(code -> {
			// Create a code generator
			BladeCodeGenerator generator = new BladeCodeGenerator();
			// Set menu data
			this.generateMenu(generator, code);
			// Set configuration information
			this.generateTemplate(generator, code);
			// Set up the base model
			Model model = modelService.getById(code.getModelId());
			this.generateModel(generator, code, model);
			// Set data source
			this.generateDatasource(generator, model);
			// Start code generation
			generator.run();
		});
		return true;
	}

	@Override
	public boolean codeFast(GeneratorDTO dto) {
		// Create a code generator
		BladeCodeGenerator generator = new BladeCodeGenerator();
		Code code = Objects.requireNonNull(BeanUtil.copyProperties(dto, Code.class));
		Model model = Objects.requireNonNull(BeanUtil.copyProperties(dto, Model.class));
		String modelForm = dto.getModelForm();
		// Set menu data
		this.generateMenu(generator, code);
		// Set configuration information
		this.generateForm(generator, modelForm);
		this.generateTemplate(generator, code);
		this.generateModel(generator, code, model);
		// Set data source
		this.generateDatasource(generator, model);
		// Start code generation
		generator.run();
		return true;
	}

	private void generateMenu(BladeCodeGenerator generator, Code code) {
		// Set upper-level menuid
		generator.setMenuId(String.valueOf(code.getMenuId()));
		// Set whether to generate a menusql
		generator.setHasMenuSql(Boolean.TRUE);
	}

	private void generateForm(BladeCodeGenerator generator, String modelForm) {
		if (StringUtil.isNotBlank(modelForm)) {
			CodeSetting codeSetting = codeSettingService.getById(Func.toLong(modelForm));
			if (codeSetting != null) {
				generator.setModelFormOption(codeSetting.getSettings());
			}
		}
	}

	private void generateTemplate(BladeCodeGenerator generator, Code code) {// Set up basic configuration
		generator.setCodeStyle(code.getCodeStyle());
		generator.setCodeName(code.getCodeName());
		generator.setServiceName(code.getServiceName());
		generator.setPackageName(code.getPackageName());
		generator.setPackageDir(code.getApiPath());
		generator.setPackageWebDir(code.getWebPath());
		generator.setTablePrefix(Func.toStrArray(code.getTablePrefix()));
		generator.setIncludeTables(Func.toStrArray(code.getTableName()));
		// Set template information
		generator.setTemplateType(Func.toStr(code.getTemplateType(), DevelopConstant.TEMPLATE_CRUD));
		generator.setAuthor(code.getAuthor());
		generator.setSubModelId(code.getSubModelId());
		generator.setSubFkId(code.getSubFkId());
		generator.setTreeId(code.getTreeId());
		generator.setTreePid(code.getTreePid());
		generator.setTreeName(code.getTreeName());
		// Set whether to inherit basic business fields
		generator.setHasSuperEntity(code.getBaseMode() == 2);
		// Set whether to enable wrapper mode
		generator.setHasWrapper(code.getWrapMode() == 2);
		// Set whether to enable remote calling mode
		generator.setHasFeign(code.getFeignMode() == 2);
		// Set controller service name prefix
		generator.setHasServiceName(Boolean.TRUE);
	}

	private void generateModel(BladeCodeGenerator generator, Code code, Model model) {
		generator.setModelCode(model.getModelCode());
		generator.setModelClass(model.getModelClass());
		generator.setModel(JsonUtil.readMap(JsonUtil.toJson(model)));

		// Set up model collection
		if (Func.isNotEmpty(model.getId())) {
			List<ModelPrototype> prototypes = modelPrototypeService.prototypeList(model.getId());
			generator.setPrototypes(JsonUtil.readListMap(JsonUtil.toJson(prototypes)));
			if (StringUtil.isNotBlank(code.getSubModelId()) && StringUtil.equals(code.getTemplateType(), DevelopConstant.TEMPLATE_SUB)) {
				Model subModel = modelService.getById(Func.toLong(code.getSubModelId()));
				List<ModelPrototype> subPrototypes = modelPrototypeService.prototypeList(subModel.getId());
				generator.setSubModel(JsonUtil.readMap(JsonUtil.toJson(subModel)));
				generator.setSubPrototypes(JsonUtil.readListMap(JsonUtil.toJson(subPrototypes)));
			}
		} else {
			TableInfo tableInfo = modelPrototypeService.getTableInfo(model.getModelTable(), model.getDatasourceId());
			List<TableField> fields = tableInfo.getFields();
			List<ModelPrototype> prototypes = convertPrototypes(fields);
			generator.setPrototypes(JsonUtil.readListMap(JsonUtil.toJson(prototypes)));
		}
	}

	private void generateDatasource(BladeCodeGenerator generator, Model model) {
		Datasource datasource = datasourceService.getById(model.getDatasourceId());
		generator.setDriverName(datasource.getDriverClass());
		generator.setUrl(datasource.getUrl());
		generator.setUsername(datasource.getUsername());
		generator.setPassword(datasource.getPassword());
	}

	/**
	 * Will TableField List converted to ModelPrototype list
	 *
	 * @param tableFields input TableField list
	 * @return converted ModelPrototype list
	 */
	public static List<ModelPrototype> convertPrototypes(List<TableField> tableFields) {
		return tableFields.stream().map(tableField -> {
			ModelPrototype prototype = new ModelPrototype();
			prototype.setJdbcName(tableField.getName());
			if (tableField.getColumnType() != null) {
				prototype.setJdbcType(tableField.getColumnType().getType());
				prototype.setPropertyType(tableField.getColumnType().getType());
			}
			prototype.setJdbcComment(tableField.getComment());
			prototype.setPropertyName(tableField.getPropertyName());
			prototype.setComponentType("input");
			return prototype;
		}).collect(Collectors.toList());
	}
}
