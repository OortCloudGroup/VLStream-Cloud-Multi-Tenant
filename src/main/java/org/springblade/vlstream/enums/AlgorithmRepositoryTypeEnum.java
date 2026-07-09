package org.springblade.vlstream.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Warehouse type enum
 *
 * @author Administrator
 */
@Getter
public enum AlgorithmRepositoryTypeEnum {

	basic("basic", "Basic presets"),
	extended("extended", "Expand");

	@EnumValue
	private final String code;
	private final String description;

	AlgorithmRepositoryTypeEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * according tocodeGet enumeration
	 *
	 * @param code status code
	 * @return enumeration object
	 */
	public static AlgorithmRepositoryTypeEnum of(String code) {
		if (code == null) {
			return null;
		}
		for (AlgorithmRepositoryTypeEnum algorithmRepositoryTypeEnum : values()) {
			if (algorithmRepositoryTypeEnum.getCode().equals(code)) {
				return algorithmRepositoryTypeEnum;
			}
		}
		return null;
	}
}
