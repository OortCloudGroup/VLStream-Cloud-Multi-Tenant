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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * Data model table entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_model")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Data model table")
public class Model extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Data source primary key
	 */
	@Schema(description = "Data source primary key")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long datasourceId;
	/**
	 * Model name
	 */
	@Schema(description = "Model name")
	private String modelName;
	/**
	 * Model number
	 */
	@Schema(description = "Model number")
	private String modelCode;
	/**
	 * Physical table name
	 */
	@Schema(description = "Physical table name")
	private String modelTable;
	/**
	 * Model class name
	 */
	@Schema(description = "Model class name")
	private String modelClass;
	/**
	 * Model remarks
	 */
	@Schema(description = "Model remarks")
	private String modelRemark;


}
