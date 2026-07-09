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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.RequiredArgsConstructor;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.develop.mapper.ModelPrototypeMapper;
import org.springblade.modules.develop.pojo.entity.Datasource;
import org.springblade.modules.develop.pojo.entity.ModelPrototype;
import org.springblade.modules.develop.service.IDatasourceService;
import org.springblade.modules.develop.service.IModelPrototypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * Data prototype table Service implementation class
 *
 * @author Chill
 */
//@Master
@Service
@RequiredArgsConstructor
public class ModelPrototypeServiceImpl extends BaseServiceImpl<ModelPrototypeMapper, ModelPrototype> implements IModelPrototypeService {

	private final IDatasourceService datasourceService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submitList(List<ModelPrototype> modelPrototypes) {
		modelPrototypes.forEach(modelPrototype -> {
			if (modelPrototype.getId() == null) {
				this.save(modelPrototype);
			} else {
				this.updateById(modelPrototype);
			}
		});
		return true;
	}

	@Override
	public List<ModelPrototype> prototypeList(Long modelId) {
		return this.list(Wrappers.<ModelPrototype>lambdaQuery().eq(ModelPrototype::getModelId, modelId));
	}

	@Override
	public TableInfo getTableInfo(String tableName, Long datasourceId) {
		Datasource datasource = datasourceService.getById(datasourceId);
		ConfigBuilder config = getConfigBuilder(datasource, tableName);
		List<TableInfo> tableInfoList = config.getTableInfoList();
		TableInfo tableInfo = null;
		Iterator<TableInfo> iterator = tableInfoList.stream().filter(table -> table.getName().equals(tableName)).toList().iterator();
		if (iterator.hasNext()) {
			tableInfo = iterator.next();
			if (tableName.contains(StringPool.UNDERSCORE)) {
				String entityPrefix = StringUtil.firstCharToUpper(tableName.split(StringPool.UNDERSCORE)[0]);
				String entityName = StringUtil.removePrefix(tableInfo.getEntityName(), entityPrefix);
				tableInfo.setEntityName(entityName);
			} else {
				tableInfo.setEntityName(StringUtil.firstCharToUpper(tableName));
			}
		}
		return tableInfo;
	}

	@Override
	public ConfigBuilder getConfigBuilder(Datasource datasource, String tableName) {
		StrategyConfig.Builder builder = new StrategyConfig.Builder();
		//Table prefix filtering, Currently, only one prefix is ​​officially supported, You can modify it yourself tosys_ortb_or other business table prefix
		//builder.likeTable(new LikeTable("blade_", SqlLike.RIGHT));
		if (StringUtil.isNotBlank(tableName)) {
			builder.addInclude(tableName);
		}
		StrategyConfig strategyConfig = builder.entityBuilder()
			.naming(NamingStrategy.underline_to_camel)
			.columnNaming(NamingStrategy.underline_to_camel).build();
		DataSourceConfig datasourceConfig = new DataSourceConfig.Builder(
			datasource.getUrl(), datasource.getUsername(), datasource.getPassword()
		).build();
		return new ConfigBuilder(null, datasourceConfig, strategyConfig, null, null, null);
	}

}
