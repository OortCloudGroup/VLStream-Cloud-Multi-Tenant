package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * File classification enumeration
 */
public enum CategoryEnum {

	/**
	 * Not under management
	 */
	WSWG("WSWG", "Not under management"),

	/**
	 * Archive management
	 */
	WSGD("WSGD", "Archive management"),

	/**
	 * Case file management
	 */
	WSAJ("WSAJ", "Case file management"),

	/**
	 * Data management
	 */
	WSZL("WSZL", "Data management");

	@EnumValue
	private final String code;
	private final String description;

	CategoryEnum(String code, String description) {
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
	public static CategoryEnum fromCode(String code) {
		for (CategoryEnum category : values()) {
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
			.collect(Collectors.toMap(CategoryEnum::getCode, CategoryEnum::getDescription));
	}

	/**
	 * Get enumeration array, Each element containscodeanddescription
	 *
	 * @return Includecodeanddescriptionarray of
	 */
	public static Map<String, String>[] toArray() {
		return Arrays.stream(values())
			.map(enumValue -> {
				Map<String, String> map = new HashMap<>();
				map.put("code", enumValue.getCode());
				map.put("description", enumValue.getDescription());
				map.put("other", "All files");
				return map;
			})
			.toArray(Map[]::new);
	}

}
