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
 * 数据原型表实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_model_prototype")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "数据原型表")
public class ModelPrototype extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 模型主键
	 */
	@Schema(description = "模型主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long modelId;
	/**
	 * 物理列名
	 */
	@Schema(description = "物理列名")
	private String jdbcName;
	/**
	 * 物理类型
	 */
	@Schema(description = "物理类型")
	private String jdbcType;
	/**
	 * 注释说明
	 */
	@Schema(description = "注释说明")
	private String jdbcComment;
	/**
	 * 实体列名
	 */
	@Schema(description = "实体列名")
	private String propertyName;
	/**
	 * 实体类型
	 */
	@Schema(description = "实体类型")
	private String propertyType;
	/**
	 * 实体类型引用
	 */
	@Schema(description = "实体类型引用")
	private String propertyEntity;
	/**
	 * 列表显示
	 */
	@Schema(description = "列表显示")
	private Integer isList;
	/**
	 * 表单显示
	 */
	@Schema(description = "表单显示")
	private Integer isForm;
	/**
	 * 独占一行
	 */
	@Schema(description = "独占一行")
	private Integer isRow;
	/**
	 * 组件类型
	 */
	@Schema(description = "组件类型")
	private String componentType;
	/**
	 * 字典编码
	 */
	@Schema(description = "字典编码")
	private String dictCode;
	/**
	 * 是否必填
	 */
	@Schema(description = "是否必填")
	private Integer isRequired;
	/**
	 * 查询配置
	 */
	@Schema(description = "查询配置")
	private Integer isQuery;
	/**
	 * 查询类型
	 */
	@Schema(description = "查询类型")
	private String queryType;


}
