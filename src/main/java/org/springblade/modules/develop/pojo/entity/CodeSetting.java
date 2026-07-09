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
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Code generator configuration table Entity class
 *
 * @author Oort
 */
@Data
@TableName("blade_code_setting")
@Schema(description = "CodeSettingobject")
public class CodeSetting implements Serializable {

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
	 * name
	 */
	@Schema(description = "name")
	private String name;
	/**
	 * serial number
	 */
	@Schema(description = "serial number")
	private String code;
	/**
	 * Classification[1:Default configuration 2:form design]
	 */
	@Schema(description = "business status", hidden = true)
	private Integer category;
	/**
	 * Configuration items
	 */
	@Schema(description = "Configuration items")
	private String settings;
	/**
	 * state[1:normal]
	 */
	@Schema(description = "business status", hidden = true)
	private Integer status;
	/**
	 * Has it been deleted?
	 */
	@Schema(description = "Has it been deleted?")
	private Integer isDeleted;

}
