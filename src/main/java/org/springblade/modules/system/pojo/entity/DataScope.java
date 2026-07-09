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
 * Entity class
 *
 * @author Oort
 */
@Data
@TableName("blade_scope_data")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "DataScopeobject")
public class DataScope extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Menu main key
	 */
	@Schema(description = "Menu main key")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long menuId;
	/**
	 * Resource number
	 */
	@Schema(description = "Resource number")
	private String resourceCode;
	/**
	 * Data permission name
	 */
	@Schema(description = "Data permission name")
	private String scopeName;
	/**
	 * Visible fields for data permissions
	 */
	@Schema(description = "Visible fields for data permissions")
	private String scopeField;
	/**
	 * Data permission class name
	 */
	@Schema(description = "Data permission class name")
	private String scopeClass;
	/**
	 * Data permission fields
	 */
	@Schema(description = "Data permission fields")
	private String scopeColumn;
	/**
	 * Data permission type
	 */
	@Schema(description = "Data permission type")
	private Integer scopeType;
	/**
	 * Data permission value range
	 */
	@Schema(description = "Data permission value range")
	private String scopeValue;
	/**
	 * Data permission remarks
	 */
	@Schema(description = "Data permission remarks")
	private String remark;


}
