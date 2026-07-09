package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Whether the enumeration class is universal
 * 1-yes, 0-no
 *
 * @author Administrator
 */
@Getter
public enum YesNoEnum {

	/**
	 * no
	 */
	NO(0, "no"),

	/**
	 * yes
	 */
	YES(1, "yes");

	@EnumValue
	private final Integer code;
	private final String description;

	YesNoEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * according tocodeGet enumeration
	 *
	 * @param code status code
	 * @return enumeration object
	 */
	public static YesNoEnum of(Integer code) {
		if (code == null) {
			return null;
		}
		for (YesNoEnum status : values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		return null;
	}

	/**
	 * Determine whether it is"yes"
	 *
	 * @param code status code
	 * @return Is it"yes"
	 */
	public static boolean isYes(Integer code) {
		return YES.getCode().equals(code);
	}

	/**
	 * Determine whether it is"no"
	 *
	 * @param code status code
	 * @return Is it"no"
	 */
	public static boolean isNo(Integer code) {
		return NO.getCode().equals(code);
	}
}
