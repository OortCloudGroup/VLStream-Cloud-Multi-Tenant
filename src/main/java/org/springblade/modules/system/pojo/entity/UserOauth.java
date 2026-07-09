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
@EqualsAndHashCode(callSuper = true)
@TableName("blade_user_oauth")
public class UserOauth extends Model<UserOauth> {

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
	private String tenantId;

	/**
	 * Third party system usersID
	 */
	private String uuid;

	/**
	 * userID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "userprimary key")
	private Long userId;

	/**
	 * username
	 */
	private String username;
	/**
	 * User nickname
	 */
	private String nickname;
	/**
	 * User avatar
	 */
	private String avatar;
	/**
	 * user网址
	 */
	private String blog;
	/**
	 * company
	 */
	private String company;
	/**
	 * Location
	 */
	private String location;
	/**
	 * User email
	 */
	private String email;
	/**
	 * User remarks(User profiles on each platform)
	 */
	private String remark;
	/**
	 * gender
	 */
	private String gender;
	/**
	 * User source
	 */
	private String source;
	/**
	 * business status
	 */
	private Integer status;
	/**
	 * Has it been deleted?
	 */
	@TableLogic
	private Integer isDeleted;


}
