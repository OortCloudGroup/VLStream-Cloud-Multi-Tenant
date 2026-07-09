package org.springblade.modules.system.pojo.entity;

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
@TableName("blade_role")
@Schema(description = "Roleobject")
public class Role implements Serializable {

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
	 * tenantID
	 */
	@Schema(description = "tenantID")
	private String tenantId;

	/**
	 * Parent primary key
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "Parent primary key")
	private Long parentId;

	/**
	 * Character name
	 */
	@Schema(description = "Character name")
	private String roleName;

	/**
	 * sort
	 */
	@Schema(description = "sort")
	private Integer sort;

	/**
	 * role alias
	 */
	@Schema(description = "role alias")
	private String roleAlias;

	/**
	 * business status
	 */
	@Schema(description = "business status")
	private Integer status;

	/**
	 * Has it been deleted?
	 */
	@TableLogic
	@Schema(description = "Has it been deleted?")
	private Integer isDeleted;


}
