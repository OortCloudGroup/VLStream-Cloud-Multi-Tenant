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
package org.springblade.modules.develop.service;

import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.develop.pojo.entity.Datasource;
import org.springblade.modules.develop.pojo.entity.ModelPrototype;

import java.util.List;

/**
 * Data prototype table Service category
 *
 * @author Chill
 */
public interface IModelPrototypeService extends BaseService<ModelPrototype> {

	/**
	 * Bulk submission
	 *
	 * @param modelPrototypes prototype collection
	 * @return boolean
	 */
	boolean submitList(List<ModelPrototype> modelPrototypes);

	/**
	 * Prototype list
	 *
	 * @param modelId ModelID
	 * @return List<ModelPrototype>
	 */
	List<ModelPrototype> prototypeList(Long modelId);

	/**
	 * Get table information
	 *
	 * @param tableName    table name
	 * @param datasourceId Data source primary key
	 */
	TableInfo getTableInfo(String tableName, Long datasourceId);

	/**
	 * Get table configuration information
	 *
	 * @param datasource Data source information
	 */
	default ConfigBuilder getConfigBuilder(Datasource datasource) {
		return getConfigBuilder(datasource, null);
	}

	/**
	 * Get table configuration information
	 *
	 * @param datasource Data source information
	 * @param tableName  table name
	 */
	ConfigBuilder getConfigBuilder(Datasource datasource, String tableName);

}
