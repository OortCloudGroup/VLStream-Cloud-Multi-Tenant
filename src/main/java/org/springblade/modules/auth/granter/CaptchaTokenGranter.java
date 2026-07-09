package org.springblade.modules.auth.granter;

import org.springblade.core.oauth2.constant.OAuth2TokenConstant;
import org.springblade.core.oauth2.exception.UserInvalidException;
import org.springblade.core.oauth2.granter.PasswordTokenGranter;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.stereotype.Component;

/**
 * CaptchaTokenGranter
 *
 * @author Oort
 */
@Component
public class CaptchaTokenGranter extends PasswordTokenGranter {

	private final BladeRedis bladeRedis;

	public CaptchaTokenGranter(OAuth2ClientService clientService, OAuth2UserService userService, PasswordHandler passwordHandler, BladeRedis bladeRedis) {
		super(clientService, userService, passwordHandler);
		this.bladeRedis = bladeRedis;
	}

	@Override
	public String type() {
		return CAPTCHA;
	}

	@Override
	public OAuth2User user(OAuth2Request request) {
		// Get verification code information
		String key = request.getCaptchaKey();
		String code = request.getCaptchaCode();
		// Get verification code
		String redisCode = bladeRedis.getAndDel(OAuth2TokenConstant.CAPTCHA_CACHE_KEY + key);
		// Determine verification code
		if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
			throw new UserInvalidException(OAuth2TokenConstant.CAPTCHA_NOT_CORRECT);
		}
		return super.user(request);
	}
}
