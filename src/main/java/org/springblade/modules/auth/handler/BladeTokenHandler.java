package org.springblade.modules.auth.handler;

import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.oauth2.handler.OAuth2TokenHandler;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Token;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.tool.support.Kv;

/**
 * BladeTokenHandler
 *
 * @author Oort
 */
public class BladeTokenHandler extends OAuth2TokenHandler {

	public BladeTokenHandler(JwtProperties properties) {
		super(properties);
	}

	@Override
	public OAuth2Token enhance(OAuth2User user, OAuth2Token token, OAuth2Request request) {
		// 父类令牌状态配置
		OAuth2Token enhanceToken = super.enhance(user, token, request);

		// 令牌统一处理，增加或删减字段
		Kv args = enhanceToken.getArgs();
		args.set(TokenConstant.USER_NAME, user.getAccount());

		// 返回令牌
		return enhanceToken;
	}
}
