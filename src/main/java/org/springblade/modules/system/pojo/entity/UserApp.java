package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * Entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_user_app")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "UserAppobject")
public class UserApp extends Model<UserApp> {

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
	 * userID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "userID")
	private Long userId;

	/**
	 * User development information
	 */
	@Schema(description = "User development information")
	private String userExt;

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
