package org.springblade.common.constant;

import org.springblade.core.launch.constant.AppConstant;

/**
 * 启动常量
 *
 * @author Chill
 */
public interface LauncherConstant {

	/**
	 * sentinel dev 地址
	 */
	String SENTINEL_DEV_ADDR = "127.0.0.1:8858";

	/**
	 * sentinel prod 地址
	 */
	String SENTINEL_PROD_ADDR = "10.211.55.5:8858";

	/**
	 * sentinel test 地址
	 */
	String SENTINEL_TEST_ADDR = "172.30.0.58:8858";

	/**
	 * elk dev 地址
	 */
	String ELK_DEV_ADDR = "127.0.0.1:9000";

	/**
	 * elk prod 地址
	 */
	String ELK_PROD_ADDR = "172.30.0.58:9000";

	/**
	 * elk test 地址
	 */
	String ELK_TEST_ADDR = "172.30.0.58:9000";

	/**
	 * 动态获取sentinel地址
	 *
	 * @param profile 环境变量
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
	 * 动态获取elk地址
	 *
	 * @param profile 环境变量
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
