package org.springblade.common.enums;

import lombok.Getter;

/**
 * Enable status enumeration
 *
 * @author Administrator
 */
@Getter
public enum EnableStatusEnum {

	/**
	 * Disable
	 */
	DISABLED(1, "Disable"),

	/**
	 * enable
	 */
	ENABLED(2, "enable");

	private final Integer code;
	private final String description;

	EnableStatusEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * according tocodeGet enumeration
	 *
	 * @param code status code
	 * @return enumeration object
	 */
	public static EnableStatusEnum of(Integer code) {
		if (code == null) {
			return null;
		}
		for (EnableStatusEnum status : values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		return null;
	}
}
