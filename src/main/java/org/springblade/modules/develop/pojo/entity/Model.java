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
 * 数据模型表实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_model")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "数据模型表")
public class Model extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 数据源主键
	 */
	@Schema(description = "数据源主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long datasourceId;
	/**
	 * 模型名称
	 */
	@Schema(description = "模型名称")
	private String modelName;
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
	 * 模型类名
	 */
	@Schema(description = "模型类名")
	private String modelClass;
	/**
	 * 模型备注
	 */
	@Schema(description = "模型备注")
	private String modelRemark;


}
