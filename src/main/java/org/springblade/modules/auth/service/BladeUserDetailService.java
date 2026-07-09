package org.springblade.modules.auth.service;

import lombok.RequiredArgsConstructor;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.auth.provider.UserType;
import org.springblade.modules.auth.utils.TokenUtil;
import org.springblade.modules.system.pojo.entity.UserInfo;
import org.springblade.modules.system.service.IUserService;

import java.util.Optional;

/**
 * BladeUserDetailService
 *
 * @author Chill
 */
@RequiredArgsConstructor
public class BladeUserDetailService implements OAuth2UserService {
	private final IUserService userService;

	@Override
	public OAuth2User loadByUserId(String userId, OAuth2Request request) {
		// Get user parameters
		String userType = Optional.ofNullable(request.getUserType())
			.filter(s -> !StringUtil.isBlank(s))
			.orElse(UserType.WEB.getName());

		// Get user information
		UserInfo userInfo = userService.userInfo(Func.toLong(userId), UserType.of(userType));

		// buildoauth2User information
		return TokenUtil.convertUser(userInfo, request);
	}

	@Override
	public OAuth2User loadByUsername(String username, OAuth2Request request) {
		// Get user parameters
		String userType = Optional.ofNullable(request.getUserType())
			.filter(s -> !StringUtil.isBlank(s))
			.orElse(UserType.WEB.getName());
		String tenantId = request.getTenantId();

		// Get user information
		UserInfo userInfo = userService.userInfo(tenantId, username, UserType.of(userType));

		// buildoauth2User information
		return TokenUtil.convertUser(userInfo, request);
	}

	@Override
	public OAuth2User loadByPhone(String phone, OAuth2Request request) {
		// Get user parameters
		String userType = Optional.ofNullable(request.getUserType())
			.filter(s -> !StringUtil.isBlank(s))
			.orElse(UserType.WEB.getName());
		String tenantId = request.getTenantId();

		// Get user information
		UserInfo userInfo = userService.userInfoByPhone(tenantId, phone, UserType.of(userType));

		// buildoauth2User information
		return TokenUtil.convertUser(userInfo, request);
	}

	@Override
	public boolean validateUser(OAuth2User user) {
		return Optional.ofNullable(user)
			.filter(u -> u.getUserId() != null && !u.getUserId().isEmpty())
			.filter(u -> u.getAuthorities() != null && !u.getAuthorities().isEmpty())
			.isPresent();
	}

}
