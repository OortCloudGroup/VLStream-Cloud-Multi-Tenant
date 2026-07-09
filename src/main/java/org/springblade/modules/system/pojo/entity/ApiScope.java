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
@TableName("blade_scope_api")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "ApiScopeobject")
public class ApiScope extends BaseEntity {

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
	 * Interface permission name
	 */
	@Schema(description = "Interface permission name")
	private String scopeName;
	/**
	 * Interface permission field
	 */
	@Schema(description = "Interface permission field")
	private String scopePath;
	/**
	 * Interface permission type
	 */
	@Schema(description = "Interface permission type")
	private Integer scopeType;
	/**
	 * Interface permission remarks
	 */
	@Schema(description = "Interface permission remarks")
	private String remark;


}
