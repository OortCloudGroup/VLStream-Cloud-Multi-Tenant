package org.springblade.modules.auth.granter;

import org.jetbrains.annotations.NotNull;
import org.springblade.common.cache.ParamCache;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.oauth2.exception.ExceptionCode;
import org.springblade.core.oauth2.exception.UserInvalidException;
import org.springblade.core.oauth2.granter.AbstractTokenGranter;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.props.OAuth2Properties;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Token;
import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.oauth2.service.impl.OAuth2UserDetail;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SM2Util;
import org.springblade.modules.auth.provider.UserType;
import org.springblade.modules.system.pojo.entity.User;
import org.springblade.modules.system.service.IUserService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Predicate;

import static org.springblade.modules.auth.constant.BladeAuthConstant.REGISTER_USER_VALUE;

/**
 * RegisterTokenGranter
 *
 * @author Oort
 */
@Component
public class RegisterTokenGranter extends AbstractTokenGranter {

	private final IUserService service;
	private final OAuth2Properties properties;

	public RegisterTokenGranter(OAuth2ClientService clientService, OAuth2UserService userService, PasswordHandler passwordHandler, IUserService service, OAuth2Properties properties) {
		super(clientService, userService, passwordHandler);
		this.service = service;
		this.properties = properties;
	}

	@Override
	public String type() {
		return REGISTER;
	}

	@Override
	public OAuth2User user(OAuth2Request request) {
		// Verify whether the registration function is enabled
		Boolean registerOpen = Func.toBoolean(ParamCache.getValue(REGISTER_USER_VALUE), false);
		if (!registerOpen) {
			throw new UserInvalidException("The registration function is not enabled yet, Please contact the administrator");
		}

		// User registration information
		User user = new User();
		user.setUserType(UserType.of(request.getUserType()).getCategory());
		user.setTenantId(request.getTenantId());
		user.setAccount(request.getUsername());
		user.setPassword(SM2Util.decrypt(request.getPassword(), properties.getPublicKey(), properties.getPrivateKey()));
		user.setName(request.getName());
		user.setRealName(request.getName());
		user.setPhone(request.getPhone());
		user.setEmail(request.getEmail());

		// Verify user format
		validateUser(user);

		// Perform user registration
		if (service.registerUser(user)) {
			// buildoauth2Required user information
			return convertOAuth2UserDetail(user, client(request));
		}
		throw new UserInvalidException(ExceptionCode.INVALID_USER.getMessage());
	}

	@Override
	public OAuth2Token token(OAuth2User user, OAuth2Request request) {
		// Remove tokens returned after registration and refresh tokens, Prevent external attacks from using the registration interface to obtain tokens and call low-privilege interfaces
		// Notice: 
		// 1. The framework has strict mode turned on by default, blade.secure.strict-token=true, If you do not remove the token, it will not be affected., The registration token will be verified and rejected by the framework
		// 2. If you turn off strict mode yourself, blade.secure.strict-token=false, Token must be removed, Otherwise, you can call the low-rights interface after registering to obtain the token.
		OAuth2Token token = super.token(user, request);
		token.getArgs().remove(TokenConstant.ACCESS_TOKEN);
		token.getArgs().remove(TokenConstant.REFRESH_TOKEN);
		return token;
	}

	private void validateUser(User user) {
		Predicate<String> isNameValid = name -> name.matches("^([\\u4e00-\\u9fa5]{2,20}|[a-zA-Z]{2,10})$");
		Predicate<String> isUsernameValid = username -> username.matches("^(?=.*[a-zA-Z])[a-zA-Z0-9_\\-@]{3,20}$");
		Predicate<String> isPasswordValid = password -> password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])[\\w@-]{6,20}$");
		Predicate<String> isPhoneValid = phone -> phone.matches("^1[3-9]\\d{9}$");
		Predicate<String> isEmailValid = email -> email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
		if (!isNameValid.test(user.getName())) {
			throw new UserInvalidException("Username length must be within2-10between, And only pure Chinese or pure English can be set");
		}
		if (!isUsernameValid.test(user.getAccount())) {
			throw new UserInvalidException("The user account length must be within3-20between, And need to include English, Can carry additional numbers、Underline、horizontal bar、@");
		}
		if (!isPasswordValid.test(user.getPassword())) {
			throw new UserInvalidException("User password length must be within6-20between, And need to include English and numbers, Can carry additional underscores、horizontal bar、@");
		}
		if (!isPhoneValid.test(user.getPhone())) {
			throw new UserInvalidException("Mobile phone number format is incorrect");
		}
		if (!isEmailValid.test(user.getEmail())) {
			throw new UserInvalidException("Email format is incorrect");
		}
	}

	@NotNull
	private OAuth2UserDetail convertOAuth2UserDetail(User user, OAuth2Client client) {
		OAuth2UserDetail userDetail = new OAuth2UserDetail();
		userDetail.setUserId(String.valueOf(user.getId()));
		userDetail.setTenantId(user.getTenantId());
		userDetail.setName(user.getName());
		userDetail.setRealName(user.getName());
		userDetail.setAccount(user.getAccount());
		userDetail.setPassword(user.getPassword());
		userDetail.setPhone(user.getPhone());
		userDetail.setEmail(user.getEmail());
		userDetail.setAuthorities(Collections.singletonList(REGISTER));
		userDetail.setClient(client);
		return userDetail;
	}
}
