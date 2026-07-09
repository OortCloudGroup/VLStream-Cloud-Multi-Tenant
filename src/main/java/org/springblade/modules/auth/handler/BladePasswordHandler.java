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
	 * Determine whether the password matches
	 *
	 * @param rawPassword     The original password submitted when requesting
	 * @param encodedPassword Database encrypted password
	 * @return boolean
	 */
	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
		return super.matches(rawPassword, encodedPassword);
	}

	/**
	 * Encryption password rules
	 *
	 * @param rawPassword password
	 * @return Encrypted password
	 */
	@Override
	public String encode(String rawPassword) {
		return super.encode(rawPassword);
	}
}
