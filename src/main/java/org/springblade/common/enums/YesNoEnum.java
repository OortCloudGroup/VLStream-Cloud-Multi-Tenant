package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 通用是否枚举类
 * 1-是，0-否
 *
 * @author Administrator
 */
@Getter
public enum YesNoEnum {

	/**
	 * 否
	 */
	NO(0, "否"),

	/**
	 * 是
	 */
	YES(1, "是");

	@EnumValue
	private final Integer code;
	private final String description;

	YesNoEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * 根据code获取枚举
	 *
	 * @param code 状态码
	 * @return 枚举对象
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
	 * 判断是否为"是"
	 *
	 * @param code 状态码
	 * @return 是否为"是"
	 */
	public static boolean isYes(Integer code) {
		return YES.getCode().equals(code);
	}

	/**
	 * 判断是否为"否"
	 *
	 * @param code 状态码
	 * @return 是否为"否"
	 */
	public static boolean isNo(Integer code) {
		return NO.getCode().equals(code);
	}
}
