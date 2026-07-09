package org.springblade.modules.auth.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.cache.CacheNames;
import org.springblade.common.cache.ParamCache;
import org.springblade.core.oauth2.exception.ExceptionCode;
import org.springblade.core.oauth2.provider.OAuth2Validation;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.WebUtil;

import java.time.Duration;

import static org.springblade.modules.auth.constant.BladeAuthConstant.FAIL_COUNT;
import static org.springblade.modules.auth.constant.BladeAuthConstant.FAIL_COUNT_VALUE;

/**
 * Failure to lock handler
 * Unified management of account locking andIPLocking logic
 *
 * @author Oort
 */
@Slf4j
@RequiredArgsConstructor
public class BladeLockHandler {

	private final BladeRedis bladeRedis;

	/**
	 * Lock duration(minute)
	 */
	private static final int LOCK_DURATION_MINUTES = 30;

	/**
	 * Verify whether the account is locked
	 *
	 * @param tenantId tenantid
	 * @param account  account
	 * @return OAuth2Validation
	 */
	public OAuth2Validation validateAccountLock(String tenantId, String account) {
		int cnt = getAccountFailCount(tenantId, account);
		int failCount = getFailCountThreshold();
		if (cnt >= failCount) {
			log.error("user: {}, locked, askip: {}", account, WebUtil.getIP());
			return buildValidationFailure();
		}
		return new OAuth2Validation();
	}

	/**
	 * checkIPWhether to lock
	 *
	 * @param tenantId tenantid
	 * @return OAuth2Validation
	 */
	public OAuth2Validation validateIpLock(String tenantId) {
		String clientIp = WebUtil.getIP();
		int cnt = getIpFailCount(tenantId, clientIp);
		int failCount = getFailCountThreshold();
		if (cnt >= failCount) {
			log.error("IP: {}, locked", clientIp);
			return buildValidationFailure();
		}
		return new OAuth2Validation();
	}

	/**
	 * Increase the number of account errors
	 *
	 * @param tenantId tenantid
	 * @param account  account
	 */
	public void addAccountFailCount(String tenantId, String account) {
		if (Func.hasEmpty(tenantId, account)) {
			return;
		}
		int count = getAccountFailCount(tenantId, account);
		String cacheKey = CacheNames.tenantKey(tenantId, CacheNames.ACCOUNT_FAIL_KEY, account);
		bladeRedis.setEx(cacheKey, count + 1, Duration.ofMinutes(LOCK_DURATION_MINUTES));
	}

	/**
	 * IncreaseIPnumber of errors
	 *
	 * @param tenantId tenantid
	 */
	public void addIpFailCount(String tenantId) {
		String clientIp = WebUtil.getIP();
		if (Func.hasEmpty(tenantId, clientIp)) {
			return;
		}
		int count = getIpFailCount(tenantId, clientIp);
		String cacheKey = CacheNames.tenantKey(tenantId, CacheNames.IP_FAIL_KEY, clientIp);
		bladeRedis.setEx(cacheKey, count + 1, Duration.ofMinutes(LOCK_DURATION_MINUTES));
	}

	/**
	 * Clear account error count
	 *
	 * @param tenantId tenantid
	 * @param account  account
	 */
	public void clearAccountFailCount(String tenantId, String account) {
		if (Func.hasEmpty(tenantId, account)) {
			return;
		}
		String cacheKey = CacheNames.tenantKey(tenantId, CacheNames.ACCOUNT_FAIL_KEY, account);
		bladeRedis.del(cacheKey);
	}

	/**
	 * ClearIPnumber of errors
	 *
	 * @param tenantId tenantid
	 */
	public void clearIpFailCount(String tenantId) {
		String clientIp = WebUtil.getIP();
		if (Func.hasEmpty(tenantId, clientIp)) {
			return;
		}
		String cacheKey = CacheNames.tenantKey(tenantId, CacheNames.IP_FAIL_KEY, clientIp);
		bladeRedis.del(cacheKey);
	}

	/**
	 * Handle authentication failure
	 * Also add accounts andIPnumber of errors
	 *
	 * @param tenantId tenantid
	 * @param account  account
	 */
	public void handleAuthFailure(String tenantId, String account) {
		addAccountFailCount(tenantId, account);
		addIpFailCount(tenantId);
	}

	/**
	 * Processing authentication successful
	 * Clear the account andIPnumber of errors
	 *
	 * @param tenantId tenantid
	 * @param account  account
	 */
	public void handleAuthSuccess(String tenantId, String account) {
		clearAccountFailCount(tenantId, account);
		clearIpFailCount(tenantId);
	}

	/**
	 * Get the number of account errors
	 *
	 * @param tenantId tenantid
	 * @param account  account
	 * @return int
	 */
	private int getAccountFailCount(String tenantId, String account) {
		if (Func.hasEmpty(tenantId, account)) {
			return 0;
		}
		String cacheKey = CacheNames.tenantKey(tenantId, CacheNames.ACCOUNT_FAIL_KEY, account);
		return Func.toInt(bladeRedis.get(cacheKey), 0);
	}

	/**
	 * getIPnumber of errors
	 *
	 * @param tenantId tenantid
	 * @param clientIp clientIP
	 * @return int
	 */
	private int getIpFailCount(String tenantId, String clientIp) {
		if (Func.hasEmpty(tenantId, clientIp)) {
			return 0;
		}
		String cacheKey = CacheNames.tenantKey(tenantId, CacheNames.IP_FAIL_KEY, clientIp);
		return Func.toInt(bladeRedis.get(cacheKey), 0);
	}

	/**
	 * Get the failure count threshold
	 *
	 * @return int
	 */
	private int getFailCountThreshold() {
		return Func.toInt(ParamCache.getValue(FAIL_COUNT_VALUE), FAIL_COUNT);
	}

	/**
	 * Build verification failure results
	 *
	 * @return OAuth2Validation
	 */
	private OAuth2Validation buildValidationFailure() {
		OAuth2Validation validation = new OAuth2Validation();
		validation.setSuccess(false);
		validation.setCode(ExceptionCode.USER_TOO_MANY_FAILS.getCode());
		validation.setMessage(ExceptionCode.USER_TOO_MANY_FAILS.getMessage());
		return validation;
	}
}
