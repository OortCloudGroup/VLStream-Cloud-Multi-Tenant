package org.springblade.modules.system.pojo.entity;

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
 * Entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_role_scope")
@Schema(description = "RoleScopeobject")
public class RoleScope implements Serializable {

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
	 * Permission type
	 */
	@Schema(description = "Permission type")
	private Integer scopeCategory;

	/**
	 * Permissionsid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "Permissionsid")
	private Long scopeId;

	/**
	 * Roleid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "Roleid")
	private Long roleId;


}
