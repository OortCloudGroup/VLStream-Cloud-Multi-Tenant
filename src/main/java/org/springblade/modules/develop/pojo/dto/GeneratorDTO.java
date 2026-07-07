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
 * 代码生成DTO
 *
 * @author Chill
 */
@Data
public class GeneratorDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 上级菜单主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "上级菜单主键")
	private Long menuId;

	/**
	 * 数据源主键
	 */
	@Schema(description = "数据源主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long datasourceId;
	/**
	 * 模型编号
	 */
	@Schema(description = "模型编号")
	private String modelCode;
	/**
	 * 物理表名
	 */
	@Schema(description = "物理表名")
	private String modelTable;
	/**
	 * 表单设计
	 */
	@Schema(description = "表单设计")
	private String modelForm;
	/**
	 * 模型类名
	 */
	@Schema(description = "模型类名")
	private String modelClass;

	/**
	 * 模块名称
	 */
	@Schema(description = "服务名称")
	private String serviceName;

	/**
	 * 模块名称
	 */
	@Schema(description = "模块名称")
	private String codeName;

	/**
	 * 表名
	 */
	@Schema(description = "表名")
	private String tableName;

	/**
	 * 实体名
	 */
	@Schema(description = "表前缀")
	private String tablePrefix;

	/**
	 * 主键名
	 */
	@Schema(description = "主键名")
	private String pkName;

	/**
	 * 后端包名
	 */
	@Schema(description = "后端包名")
	private String packageName;

	/**
	 * 基础业务模式
	 */
	@Schema(description = "基础业务模式")
	private Integer baseMode;

	/**
	 * 包装器模式
	 */
	@Schema(description = "包装器模式")
	private Integer wrapMode;

	/**
	 * 远程调用模式
	 */
	@Schema(description = "远程调用模式")
	private Integer feignMode;

	/**
	 * 代码风格
	 */
	@Schema(description = "代码风格")
	private String codeStyle;

	/**
	 * 后端路径
	 */
	@Schema(description = "后端路径")
	private String apiPath;

	/**
	 * 前端路径
	 */
	@Schema(description = "前端路径")
	private String webPath;

}
