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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_code")
@Schema(description = "Codeobject")
public class Code implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * primary key
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "primary key")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * Data model primary key
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "Data model primary key")
	private Long modelId;

	/**
	 * Upper level menu main key
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "Upper level menu main key")
	private Long menuId;

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
	 * template type
	 */
	@Schema(description = "template type")
	private String templateType;

	/**
	 * Author information
	 */
	@Schema(description = "Author information")
	private String author;

	/**
	 * Child table model primary key
	 */
	@Schema(description = "Child table model primary key")
	private String subModelId;

	/**
	 * Subtable binding foreign key
	 */
	@Schema(description = "Subtable binding foreign key")
	private String subFkId;

	/**
	 * tree primary key field
	 */
	@Schema(description = "tree primary key field")
	private String treeId;

	/**
	 * Tree parent primary key field
	 */
	@Schema(description = "Tree parent primary key field")
	private String treePid;

	/**
	 * tree name field
	 */
	@Schema(description = "tree name field")
	private String treeName;

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

	/**
	 * Has it been deleted?
	 */
	@TableLogic
	@Schema(description = "Has it been deleted?")
	private Integer isDeleted;


}
