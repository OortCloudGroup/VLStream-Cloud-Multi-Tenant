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
 * Data prototype table entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_model_prototype")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Data prototype table")
public class ModelPrototype extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Model primary key
	 */
	@Schema(description = "Model primary key")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long modelId;
	/**
	 * Physical column name
	 */
	@Schema(description = "Physical column name")
	private String jdbcName;
	/**
	 * physical type
	 */
	@Schema(description = "physical type")
	private String jdbcType;
	/**
	 * Notes
	 */
	@Schema(description = "Notes")
	private String jdbcComment;
	/**
	 * Entity column name
	 */
	@Schema(description = "Entity column name")
	private String propertyName;
	/**
	 * Entity type
	 */
	@Schema(description = "Entity type")
	private String propertyType;
	/**
	 * Entity type reference
	 */
	@Schema(description = "Entity type reference")
	private String propertyEntity;
	/**
	 * List display
	 */
	@Schema(description = "List display")
	private Integer isList;
	/**
	 * form display
	 */
	@Schema(description = "form display")
	private Integer isForm;
	/**
	 * exclusive line
	 */
	@Schema(description = "exclusive line")
	private Integer isRow;
	/**
	 * Component type
	 */
	@Schema(description = "Component type")
	private String componentType;
	/**
	 * dictionary encoding
	 */
	@Schema(description = "dictionary encoding")
	private String dictCode;
	/**
	 * Is it required?
	 */
	@Schema(description = "Is it required?")
	private Integer isRequired;
	/**
	 * Query configuration
	 */
	@Schema(description = "Query configuration")
	private Integer isQuery;
	/**
	 * Query type
	 */
	@Schema(description = "Query type")
	private String queryType;


}
