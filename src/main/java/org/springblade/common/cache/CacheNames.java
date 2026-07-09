package org.springblade.common.cache;

import org.springblade.core.tool.utils.StringPool;

/**
 * Cache name
 *
 * @author Chill
 */
public interface CacheNames {

	/**
	 * Return the splicedkey
	 *
	 * @param cacheKey      cachekey
	 * @param cacheKeyValue cachekeyvalue
	 * @return tenantKey
	 */
	static String cacheKey(String cacheKey, String cacheKeyValue) {
		return cacheKey.concat(cacheKeyValue);
	}

	/**
	 * Returns the tenant formatkey
	 *
	 * @param tenantId      Tenant number
	 * @param cacheKey      cachekey
	 * @param cacheKeyValue cachekeyvalue
	 * @return tenantKey
	 */
	static String tenantKey(String tenantId, String cacheKey, String cacheKeyValue) {
		return tenantId.concat(StringPool.COLON).concat(cacheKey).concat(cacheKeyValue);
	}

	/**
	 * Verification codekey
	 */
	String CAPTCHA_KEY = "blade:auth::blade:captcha:";

	/**
	 * Account failedkey
	 */
	String ACCOUNT_FAIL_KEY = "blade:lock::account:fail:";

	/**
	 * IPfailkey
	 */
	String IP_FAIL_KEY = "blade:lock::ip:fail:";

}
