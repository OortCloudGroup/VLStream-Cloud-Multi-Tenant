package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * Classification interface design template type enumeration
 *
 * @author Oort
 * @since 2025-10-21
 */
@Getter
@AllArgsConstructor
public enum CategoryTemplateTypeEnum {

	/**
	 * Browse interface
	 */
	BROWSE(1, "Browse interface"),

	/**
	 * Search interface
	 */
	SEARCH(2, "Search interface"),

	/**
	 * Input interface
	 */
	INPUT(3, "Input interface"),

	/**
	 * Details interface
	 */
	DETAIL(4, "Details interface"),

	/**
	 * Statistics interface
	 */
	STATISTICS(5, "Statistics interface"),

	/**
	 * Supplementary description interface
	 */
	SUPPLEMENT_INSERT(6, "Supplementary description interface");

	/**
	 * type value
	 */
	@EnumValue
	@JsonValue
	private final Integer value;

	/**
	 * Type description
	 */
	private final String description;

	/**
	 * Get enum based on value
	 *
	 * @param value type value
	 * @return enumeration object, Not found returnnull
	 */
	public static CategoryTemplateTypeEnum getByValue(Integer value) {
		if (value == null) {
			return null;
		}
		for (CategoryTemplateTypeEnum type : CategoryTemplateTypeEnum.values()) {
			if (type.getValue().equals(value)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Determine whether the value is valid
	 *
	 * @param value type value
	 * @return Is it valid?
	 */
	public static boolean isValid(Integer value) {
		return getByValue(value) != null;
	}

	/**
	 * Get description
	 *
	 * @param value type value
	 * @return Description information
	 */
	public static String getDescriptionByValue(Integer value) {
		CategoryTemplateTypeEnum type = getByValue(value);
		return type != null ? type.getDescription() : null;
	}
}
