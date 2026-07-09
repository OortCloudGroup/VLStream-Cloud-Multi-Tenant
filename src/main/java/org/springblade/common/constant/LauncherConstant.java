package org.springblade.common.constant;

import org.springblade.core.launch.constant.AppConstant;

/**
 * startup constants
 *
 * @author Chill
 */
public interface LauncherConstant {

	/**
	 * sentinel dev address
	 */
	String SENTINEL_DEV_ADDR = "127.0.0.1:8858";

	/**
	 * sentinel prod address
	 */
	String SENTINEL_PROD_ADDR = "10.211.55.5:8858";

	/**
	 * sentinel test address
	 */
	String SENTINEL_TEST_ADDR = "172.30.0.58:8858";

	/**
	 * elk dev address
	 */
	String ELK_DEV_ADDR = "127.0.0.1:9000";

	/**
	 * elk prod address
	 */
	String ELK_PROD_ADDR = "172.30.0.58:9000";

	/**
	 * elk test address
	 */
	String ELK_TEST_ADDR = "172.30.0.58:9000";

	/**
	 * Dynamic acquisitionsentineladdress
	 *
	 * @param profile environment variables
	 * @return addr
	 */
	static String sentinelAddr(String profile) {
		return switch (profile) {
			case (AppConstant.PROD_CODE) -> SENTINEL_PROD_ADDR;
			case (AppConstant.TEST_CODE) -> SENTINEL_TEST_ADDR;
			default -> SENTINEL_DEV_ADDR;
		};
	}

	/**
	 * Dynamic acquisitionelkaddress
	 *
	 * @param profile environment variables
	 * @return addr
	 */
	static String elkAddr(String profile) {
		return switch (profile) {
			case (AppConstant.PROD_CODE) -> ELK_PROD_ADDR;
			case (AppConstant.TEST_CODE) -> ELK_TEST_ADDR;
			default -> ELK_DEV_ADDR;
		};
	}


}
