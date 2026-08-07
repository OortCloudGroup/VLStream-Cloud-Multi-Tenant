package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 控件类型枚举
 */
public enum ControlTypeEnum {
	/**
	 * 单行输入框
	 */
	INPUT("INPUT", "单行输入框", "input"),

	/**
	 * 多行输入框
	 */
	TEXTAREA("TEXTAREA", "多行输入框", "text"),

	/**
	 * 密码框
	 */
	PASSWORD("PASSWORD", "密码框", "password"),

	/**
	 * 单选框
	 */
	RADIO("RADIO", "单选框", "radio"),

	/**
	 * 复选框
	 */
	CHECKBOX("CHECKBOX", "复选框", "checkbox"),

	/**
	 * 下拉框
	 */
	SELECT("SELECT", "下拉框", "select"),

	/**
	 * 日期选择器
	 */
	DATE_PICKER("DATE_PICKER", "日期选择器", "date"),

	/**
	 * 时间选择器
	 */
	TIME_PICKER("TIME_PICKER", "时间选择器", "datetime"),

	/**
	 * 日期时间选择器
	 */
	DATETIME_PICKER("DATETIME_PICKER", "日期时间选择器", "datetime"),

	/**
	 * 文件上传
	 */
	FILE_UPLOAD("FILE_UPLOAD", "文件上传", ""),

	/**
	 * 图片上传
	 */
	IMAGE_UPLOAD("IMAGE_UPLOAD", "图片上传", ""),

	/**
	 * 视频上传
	 */
	VIDEO_UPLOAD("VIDEO_UPLOAD", "视频上传", "");

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
	 * 根据code获取枚举值
	 *
	 * @param code 控件类型代码
	 * @return 对应的枚举值
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
