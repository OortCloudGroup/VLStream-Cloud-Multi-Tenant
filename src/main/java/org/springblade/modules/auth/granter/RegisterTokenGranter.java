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
		// 校验注册功能是否开启
		Boolean registerOpen = Func.toBoolean(ParamCache.getValue(REGISTER_USER_VALUE), false);
		if (!registerOpen) {
			throw new UserInvalidException("注册功能暂未开启，请联系管理员");
		}

		// 用户注册信息
		User user = new User();
		user.setUserType(UserType.of(request.getUserType()).getCategory());
		user.setTenantId(request.getTenantId());
		user.setAccount(request.getUsername());
		user.setPassword(SM2Util.decrypt(request.getPassword(), properties.getPublicKey(), properties.getPrivateKey()));
		user.setName(request.getName());
		user.setRealName(request.getName());
		user.setPhone(request.getPhone());
		user.setEmail(request.getEmail());

		// 校验用户格式
		validateUser(user);

		// 执行用户注册
		if (service.registerUser(user)) {
			// 构建oauth2所需用户信息
			return convertOAuth2UserDetail(user, client(request));
		}
		throw new UserInvalidException(ExceptionCode.INVALID_USER.getMessage());
	}

	@Override
	public OAuth2Token token(OAuth2User user, OAuth2Request request) {
		// 移除注册后返回的令牌与刷新令牌，防止外部攻击采用注册接口获取令牌并调用低权接口
		// 注意：
		// 1. 框架已默认开启严格模式，blade.secure.strict-token=true，不移除令牌则不受影响，注册令牌会被框架校验并拒绝
		// 2. 若自行关闭严格模式，blade.secure.strict-token=false，必须将令牌移除，否则注册获取令牌后可调用低权接口
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
			throw new UserInvalidException("用户姓名长度必须在2-10之间，且仅能设置纯中文或纯英文");
		}
		if (!isUsernameValid.test(user.getAccount())) {
			throw new UserInvalidException("用户账号长度必须在3-20之间，且需要包含英文，可额外携带数字、下划线、横杠、@");
		}
		if (!isPasswordValid.test(user.getPassword())) {
			throw new UserInvalidException("用户密码长度必须在6-20之间，且需要包含英文与数字，可额外携带下划线、横杠、@");
		}
		if (!isPhoneValid.test(user.getPhone())) {
			throw new UserInvalidException("手机号格式不正确");
		}
		if (!isEmailValid.test(user.getEmail())) {
			throw new UserInvalidException("邮箱格式不正确");
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
