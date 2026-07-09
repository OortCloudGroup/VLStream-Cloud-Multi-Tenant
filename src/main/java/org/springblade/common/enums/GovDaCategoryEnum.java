package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * File classification enumeration
 */
public enum GovDaCategoryEnum {

	/**
	 * Unfiled file classification
	 */
	WGWJFL("WGWJFL", "Unfiled file classification"),

	/**
	 * Archive file classification
	 */
	GDWJFL("GDWJFL", "Archive file classification"),

	/**
	 * Case file classification
	 */
	AJFL("AJFL", "Case file classification"),

	/**
	 * Data classification
	 */
	ZLFL("ZLFL", "Data classification");

	@EnumValue
	private final String code;
	private final String description;

	GovDaCategoryEnum(String code, String description) {
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
	public static GovDaCategoryEnum fromCode(String code) {
		for (GovDaCategoryEnum category : values()) {
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
			.collect(Collectors.toMap(GovDaCategoryEnum::getCode, GovDaCategoryEnum::getDescription));
	}

}
