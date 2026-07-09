package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;
import org.springblade.core.tool.jackson.Sensitive;
import org.springblade.core.tool.sensitive.SensitiveType;

import java.io.Serial;
import java.util.Date;

/**
 * Entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_user")
@EqualsAndHashCode(callSuper = true)
public class User extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * User number
	 */
	private String code;
	/**
	 * User platform
	 */
	private Integer userType;
	/**
	 * account
	 */
	private String account;
	/**
	 * password
	 */
	private String password;
	/**
	 * Nick name
	 */
	private String name;
	/**
	 * real name
	 */
	private String realName;
	/**
	 * avatar
	 */
	private String avatar;
	/**
	 * Mail
	 */
	@Sensitive(type = SensitiveType.EMAIL)
	private String email;
	/**
	 * cell phone
	 */
	@Sensitive(type = SensitiveType.MOBILE)
	private String phone;
	/**
	 * Birthday
	 */
	private Date birthday;
	/**
	 * gender
	 */
	private Integer sex;
	/**
	 * Roleid
	 */
	private String roleId;
	/**
	 * departmentid
	 */
	private String deptId;
	/**
	 * postid
	 */
	private String postId;
	/**
	 * directorid
	 */
	private String leaderId;
	/**
	 * Whether in charge
	 */
	private Integer isLeader;


}
