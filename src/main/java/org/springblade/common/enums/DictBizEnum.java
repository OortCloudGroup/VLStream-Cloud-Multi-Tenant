package org.springblade.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Business dictionary enumeration class
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum DictBizEnum {

	/**
	 * test
	 */
	TEST("test"),
	;

	final String name;

}
