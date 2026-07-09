package org.springblade.modules.system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.User;

import java.io.Serial;

/**
 * View entity class
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "UserVOobject")
public class UserVO extends User {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * primary keyID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * password
	 */
	@JsonIgnore
	private String password;

	/**
	 * Tenant name
	 */
	private String tenantName;

	/**
	 * User platform name
	 */
	private String userTypeName;

	/**
	 * Character name
	 */
	private String roleName;

	/**
	 * Department name
	 */
	private String deptName;

	/**
	 * Position name
	 */
	private String postName;

	/**
	 * gender
	 */
	private String sexName;

	/**
	 * Expand information
	 */
	private String userExt;
}
