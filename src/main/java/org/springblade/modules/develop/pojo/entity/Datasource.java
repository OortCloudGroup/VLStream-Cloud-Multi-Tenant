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
 * 数据源配置表实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_datasource")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "数据源配置表")
public class Datasource extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 数据源类型
	 */
	@Schema(description = "数据源类型")
	private Integer category;
	/**
	 * 名称
	 */
	@Schema(description = "名称")
	private String name;
	/**
	 * 驱动类
	 */
	@Schema(description = "驱动类")
	private String driverClass;
	/**
	 * 连接地址
	 */
	@Schema(description = "连接地址")
	private String url;
	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;
	/**
	 * 密码
	 */
	@Schema(description = "密码")
	private String password;
	/**
	 * 分库分表配置
	 */
	@Schema(description = "分库分表配置")
	private String shardingConfig;
	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;


}
