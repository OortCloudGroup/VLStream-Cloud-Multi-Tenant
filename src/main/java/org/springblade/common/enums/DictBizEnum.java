package org.springblade.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务字典枚举类
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum DictBizEnum {

	/**
	 * 测试
	 */
	TEST("test"),
	;

	final String name;

}
