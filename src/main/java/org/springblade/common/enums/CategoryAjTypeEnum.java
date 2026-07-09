package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * File classification file type enumeration
 */
public enum CategoryAjTypeEnum {

	/**
	 * within the volumeJN filesAN
	 */
	JN("JN", "within the volume"),

	/**
	 * files
	 */
	AJ("AJ", "files");

	@EnumValue
	private final String code;
	private final String description;

	CategoryAjTypeEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * according tocodeGet enumeration
	 *
	 * @param code Classificationcode
	 * @return The corresponding enumeration value, Return if not foundnull
	 */
	public static CategoryAjTypeEnum fromCode(String code) {
		for (CategoryAjTypeEnum category : values()) {
			if (category.getCode().equals(code)) {
				return category;
			}
		}
		return null;
	}

	/**
	 * Get enumerationmap, keyforcode, valuefor an enumeration instance
	 *
	 * @return enumeratemap
	 */
	public static Map<String, String> toMap() {
		return Arrays.stream(values())
			.collect(Collectors.toMap(CategoryAjTypeEnum::getCode, CategoryAjTypeEnum::getDescription));
	}

}
