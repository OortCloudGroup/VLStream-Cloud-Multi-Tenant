package org.springblade.modules.auth.constant;

/**
 * AuthorizationConstant
 *
 * @author Chill
 */
public interface BladeAuthConstant {

	/**
	 * Whether to enable registration parameterskey
	 */
	String REGISTER_USER_VALUE = "account.registerUser";
	/**
	 * Account lock error count parameterkey
	 */
	String FAIL_COUNT_VALUE = "account.failCount";
	/**
	 * Default number of account lockout errors
	 */
	Integer FAIL_COUNT = 5;
}
