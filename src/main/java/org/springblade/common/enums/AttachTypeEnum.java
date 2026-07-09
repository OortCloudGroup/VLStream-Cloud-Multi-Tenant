package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Attachment type enumeration class
 * 1-original 2-draft 3-Clear draft 4-text 5-appendix
 *
 * @author Oort
 * @since 2025-01-01
 */
@Getter
public enum AttachTypeEnum {

	/**
	 * exploit file
	 */
	UTILIZE("UTILIZE", "exploit file"),

	/**
	 * original
	 */
	ORIGINAL("ORIGINAL", "original"),

	/**
	 * draft
	 */
	DRAFT("DRAFT", "draft"),

	/**
	 * Clear draft
	 */
	CLEAN_COPY("CLEAN_COPY", "Clear draft"),

	/**
	 * text
	 */
	MAIN_TEXT("MAIN_TEXT", "text"),

	/**
	 * appendix
	 */
	ATTACHMENT("ATTACHMENT", "appendix");

	@EnumValue
	private final String code;
	private final String description;

	AttachTypeEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * according tocodeGet enumeration
	 *
	 * @param code type code
	 * @return enumeration object, Return if not foundnull
	 */
	public static AttachTypeEnum of(String code) {
		if (code == null) {
			return null;
		}
		for (AttachTypeEnum type : values()) {
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		return null;
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
				return map;
			})
			.toArray(Map[]::new);
	}

}

