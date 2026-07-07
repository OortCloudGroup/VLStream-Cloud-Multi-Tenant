package org.springblade.modules.auth.handler;

import org.springblade.core.oauth2.handler.OAuth2PasswordHandler;
import org.springblade.core.oauth2.props.OAuth2Properties;

/**
 * BladePasswordHandler
 *
 * @author Oort
 */
public class BladePasswordHandler extends OAuth2PasswordHandler {

	public BladePasswordHandler(OAuth2Properties properties) {
		super(properties);
	}

	/**
	 * 判断密码是否匹配
	 *
	 * @param rawPassword     请求时提交的原密码
	 * @param encodedPassword 数据库加密后的密码
	 * @return boolean
	 */
	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
		return super.matches(rawPassword, encodedPassword);
	}

	/**
	 * 加密密码规则
	 *
	 * @param rawPassword 密码
	 * @return 加密后的密码
	 */
	@Override
	public String encode(String rawPassword) {
		return super.encode(rawPassword);
	}
}
