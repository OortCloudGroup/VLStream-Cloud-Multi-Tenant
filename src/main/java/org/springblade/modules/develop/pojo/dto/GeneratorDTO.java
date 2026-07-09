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
package org.springblade.modules.develop.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * code generationDTO
 *
 * @author Chill
 */
@Data
public class GeneratorDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Upper level menu main key
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "Upper level menu main key")
	private Long menuId;

	/**
	 * Data source primary key
	 */
	@Schema(description = "Data source primary key")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long datasourceId;
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
	 * form design
	 */
	@Schema(description = "form design")
	private String modelForm;
	/**
	 * Model class name
	 */
	@Schema(description = "Model class name")
	private String modelClass;

	/**
	 * module name
	 */
	@Schema(description = "Service name")
	private String serviceName;

	/**
	 * module name
	 */
	@Schema(description = "module name")
	private String codeName;

	/**
	 * table name
	 */
	@Schema(description = "table name")
	private String tableName;

	/**
	 * Entity name
	 */
	@Schema(description = "table prefix")
	private String tablePrefix;

	/**
	 * primary key name
	 */
	@Schema(description = "primary key name")
	private String pkName;

	/**
	 * Backend package name
	 */
	@Schema(description = "Backend package name")
	private String packageName;

	/**
	 * Basic business model
	 */
	@Schema(description = "Basic business model")
	private Integer baseMode;

	/**
	 * wrapper pattern
	 */
	@Schema(description = "wrapper pattern")
	private Integer wrapMode;

	/**
	 * remote call mode
	 */
	@Schema(description = "remote call mode")
	private Integer feignMode;

	/**
	 * coding style
	 */
	@Schema(description = "coding style")
	private String codeStyle;

	/**
	 * backend path
	 */
	@Schema(description = "backend path")
	private String apiPath;

	/**
	 * frontend path
	 */
	@Schema(description = "frontend path")
	private String webPath;

}
