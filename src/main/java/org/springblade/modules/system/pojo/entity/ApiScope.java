package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * 实体类
 *
 * @author Oort
 */
@Data
@TableName("blade_scope_api")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "ApiScope对象")
public class ApiScope extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单主键
	 */
	@Schema(description = "菜单主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long menuId;
	/**
	 * 资源编号
	 */
	@Schema(description = "资源编号")
	private String resourceCode;
	/**
	 * 接口权限名称
	 */
	@Schema(description = "接口权限名称")
	private String scopeName;
	/**
	 * 接口权限字段
	 */
	@Schema(description = "接口权限字段")
	private String scopePath;
	/**
	 * 接口权限类型
	 */
	@Schema(description = "接口权限类型")
	private Integer scopeType;
	/**
	 * 接口权限备注
	 */
	@Schema(description = "接口权限备注")
	private String remark;


}
