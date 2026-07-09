package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Interface type enumeration
 *
 * @author
 * @since 2025-07-28
 */
@Getter
@AllArgsConstructor
public enum InterfaceTypeEnum {
	/**
	 * interface
	 */
	INTERFACE("INTERFACE", "interface"),

	/**
	 * callback
	 */
	CALLBACK("CALLBACK", "callback");

	@EnumValue
	private final String code;
	private final String name;

	public static InterfaceTypeEnum fromCode(String code) {
		for (InterfaceTypeEnum interfaceTypeEnum : InterfaceTypeEnum.values()) {
			if (interfaceTypeEnum.code.equals(code)) {
				return interfaceTypeEnum;
			}
		}
		return null;
	}
}
