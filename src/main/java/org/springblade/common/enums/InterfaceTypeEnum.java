package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 接口类型枚举
 *
 * @author
 * @since 2025-07-28
 */
@Getter
@AllArgsConstructor
public enum InterfaceTypeEnum {
	/**
	 * 接口
	 */
	INTERFACE("INTERFACE", "接口"),

	/**
	 * 回调
	 */
	CALLBACK("CALLBACK", "回调");

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
