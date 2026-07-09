package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * Control type enumeration
 */
public enum ControlTypeEnum {
	/**
	 * Single line input box
	 */
	INPUT("INPUT", "Single line input box", "input"),

	/**
	 * Multi-line input box
	 */
	TEXTAREA("TEXTAREA", "Multi-line input box", "text"),

	/**
	 * Password box
	 */
	PASSWORD("PASSWORD", "Password box", "password"),

	/**
	 * radio button
	 */
	RADIO("RADIO", "radio button", "radio"),

	/**
	 * checkbox
	 */
	CHECKBOX("CHECKBOX", "checkbox", "checkbox"),

	/**
	 * drop down box
	 */
	SELECT("SELECT", "drop down box", "select"),

	/**
	 * date picker
	 */
	DATE_PICKER("DATE_PICKER", "date picker", "date"),

	/**
	 * time picker
	 */
	TIME_PICKER("TIME_PICKER", "time picker", "datetime"),

	/**
	 * Date time picker
	 */
	DATETIME_PICKER("DATETIME_PICKER", "Date time picker", "datetime"),

	/**
	 * document上传
	 */
	FILE_UPLOAD("FILE_UPLOAD", "document上传", ""),

	/**
	 * Image upload
	 */
	IMAGE_UPLOAD("IMAGE_UPLOAD", "Image upload", ""),

	/**
	 * Video upload
	 */
	VIDEO_UPLOAD("VIDEO_UPLOAD", "Video upload", "");

	@EnumValue
	private final String code;
	private final String description;
	private final String htmlTag;

	ControlTypeEnum(String code, String description, String htmlTag) {
		this.code = code;
		this.description = description;
		this.htmlTag = htmlTag;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getHtmlTag() {
		return htmlTag;
	}

	/**
	 * according tocodeGet enumeration value
	 *
	 * @param code Control type code
	 * @return The corresponding enumeration value
	 */
	public static ControlTypeEnum fromCode(String code) {
		for (ControlTypeEnum type : ControlTypeEnum.values()) {
			if (type.code.equals(code)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown control type code: " + code);
	}

	@Override
	public String toString() {
		return code;
	}
}
