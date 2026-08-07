package org.springblade.common.cache;

import org.springblade.core.tool.utils.StringPool;

/**
 * 缓存名
 *
 * @author Chill
 */
public interface CacheNames {

	/**
	 * 返回拼接后的key
	 *
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String cacheKey(String cacheKey, String cacheKeyValue) {
		return cacheKey.concat(cacheKeyValue);
	}

	/**
	 * 返回租户格式的key
	 *
	 * @param tenantId      租户编号
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String tenantKey(String tenantId, String cacheKey, String cacheKeyValue) {
		return tenantId.concat(StringPool.COLON).concat(cacheKey).concat(cacheKeyValue);
	}

	/**
	 * 验证码key
	 */
	String CAPTCHA_KEY = "blade:auth::blade:captcha:";

	/**
	 * 账号失败key
	 */
	String ACCOUNT_FAIL_KEY = "blade:lock::account:fail:";

	/**
	 * IP失败key
	 */
	String IP_FAIL_KEY = "blade:lock::ip:fail:";

}
