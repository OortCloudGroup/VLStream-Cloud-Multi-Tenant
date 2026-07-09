package org.springblade.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Tenant constants
 *
 * @author Chill
 */
public interface TenantConstant {

	/**
	 * Tenant default passwordKEY
	 */
	String PASSWORD_KEY = "tenant.default.password";

	/**
	 * Tenant default account quotaKEY
	 */
	String ACCOUNT_NUMBER_KEY = "tenant.default.accountNumber";

	/**
	 * Tenant default menu collectionKEY
	 */
	String ACCOUNT_MENU_CODE_KEY = "tenant.default.menuCode";

	/**
	 * Tenant default password
	 */
	String DEFAULT_PASSWORD = "123456";

	/**
	 * Tenant authorization code default16bit key
	 */
	String DES_KEY = "0000000000000000";

	/**
	 * Tenant default account quota
	 */
	Integer DEFAULT_ACCOUNT_NUMBER = -1;

	/**
	 * Tenant default menu collection
	 */
	List<String> MENU_CODES = Arrays.asList(
		"desk", "flow", "work", "monitor", "resource", "role", "user", "dept", "dictbiz", "topmenu"
	);

}
