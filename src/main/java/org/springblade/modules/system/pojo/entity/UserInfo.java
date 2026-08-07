package org.springblade.modules.system.pojo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springblade.core.tool.support.Kv;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 * @author Chill
 */
@Data
@Schema(description = "用户信息")
public class UserInfo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 用户基础信息
	 */
	@Schema(description = "用户")
	private User user;

	/**
	 * 拓展信息
	 */
	@Schema(description = "拓展信息")
	private Kv detail;

	/**
	 * 权限标识集合
	 */
	@Schema(description = "权限集合")
	private List<String> permissions;

	/**
	 * 角色集合
	 */
	@Schema(description = "角色集合")
	private List<String> roles;

	/**
	 * 第三方授权id
	 */
	@Schema(description = "第三方授权id")
	private String oauthId;

}
