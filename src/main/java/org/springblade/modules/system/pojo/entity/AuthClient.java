package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@TableName("blade_client")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "AuthClientobject")
public class AuthClient extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * clientid
	 */
	@Schema(description = "clientid")
	private String clientId;
	/**
	 * client key
	 */
	@Schema(description = "client key")
	private String clientSecret;
	/**
	 * Resource collection
	 */
	@Schema(description = "Resource collection")
	private String resourceIds;
	/**
	 * Authorization scope
	 */
	@Schema(description = "Authorization scope")
	private String scope;
	/**
	 * Authorization type
	 */
	@Schema(description = "Authorization type")
	private String authorizedGrantTypes;
	/**
	 * callback address
	 */
	@Schema(description = "callback address")
	private String webServerRedirectUri;
	/**
	 * Permissions
	 */
	@Schema(description = "Permissions")
	private String authorities;
	/**
	 * Token expiration seconds
	 */
	@Schema(description = "Token expiration seconds")
	private Integer accessTokenValidity;
	/**
	 * Refresh token expiration seconds
	 */
	@Schema(description = "Refresh token expiration seconds")
	private Integer refreshTokenValidity;
	/**
	 * Additional notes
	 */
	@JsonIgnore
	@Schema(description = "Additional notes")
	private String additionalInformation;
	/**
	 * Automatic authorization
	 */
	@Schema(description = "Automatic authorization")
	private String autoapprove;


}
