package org.springblade.modules.auth.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.cache.SysCache;
import org.springblade.common.constant.TenantConstant;
import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.oauth2.exception.ExceptionCode;
import org.springblade.core.oauth2.handler.AbstractAuthorizationHandler;
import org.springblade.core.oauth2.props.OAuth2Properties;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.provider.OAuth2Validation;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.tenant.BladeTenantProperties;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.*;
import org.springblade.modules.system.pojo.entity.Tenant;

import java.util.Date;
import java.util.List;

/**
 * 认证处理器
 * 统一处理认证前校验、认证成功/失败回调等逻辑
 *
 * @author Oort
 */
@Slf4j
@RequiredArgsConstructor
public class BladeAuthorizationHandler extends AbstractAuthorizationHandler {

	private final BladeProperties bladeProperties;
	private final BladeTenantProperties tenantProperties;
	private final OAuth2Properties oAuth2Properties;
	private final BladeLockHandler lockHandler;

	/**
	 * 自定义弱密码列表
	 */
	private static final List<String> WEAK_PASSWORDS = List.of("admin", "administrator", "hr", "manager", "boss");

	/**
	 * 认证前校验
	 *
	 * @param request 请求信息
	 * @return boolean
	 */
	@Override
	public OAuth2Validation preValidation(OAuth2Request request) {
		if (request.isPassword() || request.isCaptchaCode()) {
			// 生产环境弱密码校验
			if (bladeProperties.isProd() && isWeakPassword(request.getPassword())) {
				return buildValidationFailure(ExceptionCode.INVALID_USER_PASSWORD);
			}
			// 判断账号是否锁定
			OAuth2Validation accountValidation = lockHandler.validateAccountLock(request.getTenantId(), request.getUsername());
			if (!accountValidation.isSuccess()) {
				return accountValidation;
			}
			// 判断IP是否锁定
			OAuth2Validation ipValidation = lockHandler.validateIpLock(request.getTenantId());
			if (!ipValidation.isSuccess()) {
				return ipValidation;
			}
		}
		return super.preValidation(request);
	}

	/**
	 * 认证前失败回调
	 *
	 * @param validation 失败信息
	 */
	@Override
	public void preFailure(OAuth2Request request, OAuth2Validation validation) {
		// 处理认证失败，增加错误次数
		lockHandler.handleAuthFailure(request.getTenantId(), request.getUsername());

		log.error("用户：{}，认证失败，失败原因：{}", request.getUsername(), validation.getMessage());
	}

	/**
	 * 认证校验
	 *
	 * @param user    用户信息
	 * @param request 请求信息
	 * @return boolean
	 */
	@Override
	public OAuth2Validation authValidation(OAuth2User user, OAuth2Request request) {
		// 密码模式、刷新token模式、验证码模式需要校验租户状态
		if (request.isPassword() || request.isRefreshToken() || request.isCaptchaCode()) {
			// 租户校验
			OAuth2Validation tenantValidation = validateTenant(user.getTenantId());
			if (!tenantValidation.isSuccess()) {
				return tenantValidation;
			}
		}
		return super.authValidation(user, request);
	}

	/**
	 * 认证成功回调
	 *
	 * @param user 用户信息
	 */
	@Override
	public void authSuccessful(OAuth2User user, OAuth2Request request) {
		// 处理认证成功，清空错误次数
		lockHandler.handleAuthSuccess(user.getTenantId(), user.getAccount());

		log.info("用户：{}，认证成功", user.getAccount());
	}

	/**
	 * 认证失败回调
	 *
	 * @param user       用户信息
	 * @param validation 失败信息
	 */
	@Override
	public void authFailure(OAuth2User user, OAuth2Request request, OAuth2Validation validation) {
		// 自定义认证失败回调
	}

	/**
	 * 判断是否为弱密码
	 *
	 * @param rawPassword 加密密码
	 * @return boolean
	 */
	private boolean isWeakPassword(String rawPassword) {
		// 获取公钥
		String publicKey = oAuth2Properties.getPublicKey();
		// 获取私钥
		String privateKey = oAuth2Properties.getPrivateKey();
		// 解密密码
		String decryptPassword = SM2Util.decrypt(rawPassword, publicKey, privateKey);
		return WEAK_PASSWORDS.stream()
			.anyMatch(weakPass -> weakPass.equalsIgnoreCase(decryptPassword));
	}

	/**
	 * 租户授权校验
	 *
	 * @param tenantId 租户id
	 * @return OAuth2Validation
	 */
	private OAuth2Validation validateTenant(String tenantId) {
		// 租户校验
		Tenant tenant = SysCache.getTenant(tenantId);
		if (tenant == null) {
			return buildValidationFailure(ExceptionCode.USER_TENANT_NOT_FOUND);
		}
		// 租户授权时间校验
		Date expireTime = tenant.getExpireTime();
		if (tenantProperties.getLicense()) {
			String licenseKey = tenant.getLicenseKey();
			String decrypt = DesUtil.decryptFormHex(licenseKey, TenantConstant.DES_KEY);
			Tenant license = JsonUtil.parse(decrypt, Tenant.class);
			if (license == null || !license.getId().equals(tenant.getId())) {
				return buildValidationFailure(ExceptionCode.UNAUTHORIZED_USER_TENANT);
			}
			expireTime = license.getExpireTime();
		}
		if (expireTime != null && expireTime.before(DateUtil.now())) {
			return buildValidationFailure(ExceptionCode.UNAUTHORIZED_USER_TENANT);
		}
		return new OAuth2Validation();
	}
}
