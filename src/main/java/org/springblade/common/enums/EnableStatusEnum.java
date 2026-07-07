package org.springblade.common.enums;

import lombok.Getter;

/**
 * 启用状态枚举
 *
 * @author Administrator
 */
@Getter
public enum EnableStatusEnum {

	/**
	 * 禁用
	 */
	DISABLED(1, "禁用"),

	/**
	 * 启用
	 */
	ENABLED(2, "启用");

	private final Integer code;
	private final String description;

	EnableStatusEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * 根据code获取枚举
	 *
	 * @param code 状态码
	 * @return 枚举对象
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
