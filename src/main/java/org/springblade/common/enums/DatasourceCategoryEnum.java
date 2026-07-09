package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data source type enumeration
 *
 * @author
 * @since 2025-07-28
 */
@Getter
@AllArgsConstructor
public enum DatasourceCategoryEnum {

	/**
	 * main table
	 */
	MAIN_TABLE("MAIN_TABLE", "main table"),

	/**
	 * Middle office service
	 */
	SERVICE("SERVICE", "Serve"),

	/**
	 * Library table
	 */
	DATABASE("DATABASE", "Library table"),

	/**
	 * interface
	 */
	INTERFACE("INTERFACE", "interface");

	@EnumValue
	private final String code;
	private final String name;

	/**
	 * Get enumeration based on code
	 *
	 * @param code code
	 * @return enumerate
	 */
	public static DatasourceCategoryEnum fromCode(String code) {
		for (DatasourceCategoryEnum category : DatasourceCategoryEnum.values()) {
			if (category.code.equals(code)) {
				return category;
			}
		}
		return null;
	}
}
