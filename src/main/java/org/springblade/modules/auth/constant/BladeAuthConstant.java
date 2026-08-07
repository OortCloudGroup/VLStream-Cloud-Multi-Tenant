package org.springblade.modules.auth.constant;

/**
 * AuthorizationConstant
 *
 * @author Chill
 */
public interface BladeAuthConstant {

	/**
	 * 是否开启注册参数key
	 */
	String REGISTER_USER_VALUE = "account.registerUser";
	/**
	 * 账号锁定错误次数参数key
	 */
	String FAIL_COUNT_VALUE = "account.failCount";
	/**
	 * 账号锁定默认错误次数
	 */
	Integer FAIL_COUNT = 5;
}
