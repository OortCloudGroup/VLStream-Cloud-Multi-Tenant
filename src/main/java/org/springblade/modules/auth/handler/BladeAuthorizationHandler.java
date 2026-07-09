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
 * Authentication Processor
 * Unified processing of pre-certification verification、Authentication successful/Failure callback and other logic
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
	 * Custom weak password list
	 */
	private static final List<String> WEAK_PASSWORDS = List.of("admin", "administrator", "hr", "manager", "boss");

	/**
	 * Verification before certification
	 *
	 * @param request request information
	 * @return boolean
	 */
	@Override
	public OAuth2Validation preValidation(OAuth2Request request) {
		if (request.isPassword() || request.isCaptchaCode()) {
			// Weak password verification in production environment
			if (bladeProperties.isProd() && isWeakPassword(request.getPassword())) {
				return buildValidationFailure(ExceptionCode.INVALID_USER_PASSWORD);
			}
			// Determine whether the account is locked
			OAuth2Validation accountValidation = lockHandler.validateAccountLock(request.getTenantId(), request.getUsername());
			if (!accountValidation.isSuccess()) {
				return accountValidation;
			}
			// judgeIPWhether to lock
			OAuth2Validation ipValidation = lockHandler.validateIpLock(request.getTenantId());
			if (!ipValidation.isSuccess()) {
				return ipValidation;
			}
		}
		return super.preValidation(request);
	}

	/**
	 * Failure callback before authentication
	 *
	 * @param validation Failure message
	 */
	@Override
	public void preFailure(OAuth2Request request, OAuth2Validation validation) {
		// Handle authentication failure, Increase the number of errors
		lockHandler.handleAuthFailure(request.getTenantId(), request.getUsername());

		log.error("user: {}, Authentication failed, Reason for failure: {}", request.getUsername(), validation.getMessage());
	}

	/**
	 * Certification verification
	 *
	 * @param user    User information
	 * @param request request information
	 * @return boolean
	 */
	@Override
	public OAuth2Validation authValidation(OAuth2User user, OAuth2Request request) {
		// password mode、refreshtokenmodel、Verification code mode needs to verify tenant status
		if (request.isPassword() || request.isRefreshToken() || request.isCaptchaCode()) {
			// Tenant verification
			OAuth2Validation tenantValidation = validateTenant(user.getTenantId());
			if (!tenantValidation.isSuccess()) {
				return tenantValidation;
			}
		}
		return super.authValidation(user, request);
	}

	/**
	 * Authentication success callback
	 *
	 * @param user User information
	 */
	@Override
	public void authSuccessful(OAuth2User user, OAuth2Request request) {
		// Processing authentication successful, Clear error count
		lockHandler.handleAuthSuccess(user.getTenantId(), user.getAccount());

		log.info("user: {}, Authentication successful", user.getAccount());
	}

	/**
	 * Authentication failure callback
	 *
	 * @param user       User information
	 * @param validation Failure message
	 */
	@Override
	public void authFailure(OAuth2User user, OAuth2Request request, OAuth2Validation validation) {
		// Custom authentication failure callback
	}

	/**
	 * Determine whether the password is weak
	 *
	 * @param rawPassword Encrypted password
	 * @return boolean
	 */
	private boolean isWeakPassword(String rawPassword) {
		// Get public key
		String publicKey = oAuth2Properties.getPublicKey();
		// Get private key
		String privateKey = oAuth2Properties.getPrivateKey();
		// Decrypt password
		String decryptPassword = SM2Util.decrypt(rawPassword, publicKey, privateKey);
		return WEAK_PASSWORDS.stream()
			.anyMatch(weakPass -> weakPass.equalsIgnoreCase(decryptPassword));
	}

	/**
	 * Tenant authorization verification
	 *
	 * @param tenantId tenantid
	 * @return OAuth2Validation
	 */
	private OAuth2Validation validateTenant(String tenantId) {
		// Tenant verification
		Tenant tenant = SysCache.getTenant(tenantId);
		if (tenant == null) {
			return buildValidationFailure(ExceptionCode.USER_TENANT_NOT_FOUND);
		}
		// Tenant authorization time verification
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
