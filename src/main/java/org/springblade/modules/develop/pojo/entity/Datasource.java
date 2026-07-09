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
package org.springblade.modules.develop.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * Data source configuration table entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_datasource")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Data source configuration table")
public class Datasource extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Data source type
	 */
	@Schema(description = "Data source type")
	private Integer category;
	/**
	 * name
	 */
	@Schema(description = "name")
	private String name;
	/**
	 * Driver class
	 */
	@Schema(description = "Driver class")
	private String driverClass;
	/**
	 * connection address
	 */
	@Schema(description = "connection address")
	private String url;
	/**
	 * username
	 */
	@Schema(description = "username")
	private String username;
	/**
	 * password
	 */
	@Schema(description = "password")
	private String password;
	/**
	 * Sub-database and sub-table configuration
	 */
	@Schema(description = "Sub-database and sub-table configuration")
	private String shardingConfig;
	/**
	 * Remark
	 */
	@Schema(description = "Remark")
	private String remark;


}
