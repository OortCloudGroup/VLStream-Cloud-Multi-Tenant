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
 * 实体类
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
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 租户ID
	 */
	private String tenantId;

	/**
	 * 第三方系统用户ID
	 */
	private String uuid;

	/**
	 * 用户ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "用户主键")
	private Long userId;

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String avatar;
	/**
	 * 用户网址
	 */
	private String blog;
	/**
	 * 所在公司
	 */
	private String company;
	/**
	 * 位置
	 */
	private String location;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 用户备注（各平台中的用户个人介绍）
	 */
	private String remark;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 用户来源
	 */
	private String source;
	/**
	 * 业务状态
	 */
	private Integer status;
	/**
	 * 是否已删除
	 */
	@TableLogic
	private Integer isDeleted;


}
