package org.springblade.modules.system.pojo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springblade.core.tool.support.Kv;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * User information
 *
 * @author Chill
 */
@Data
@Schema(description = "User information")
public class UserInfo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * User basic information
	 */
	@Schema(description = "user")
	private User user;

	/**
	 * Expand information
	 */
	@Schema(description = "Expand information")
	private Kv detail;

	/**
	 * Permission ID collection
	 */
	@Schema(description = "Permission set")
	private List<String> permissions;

	/**
	 * role collection
	 */
	@Schema(description = "role collection")
	private List<String> roles;

	/**
	 * Third party authorizationid
	 */
	@Schema(description = "Third party authorizationid")
	private String oauthId;

}
