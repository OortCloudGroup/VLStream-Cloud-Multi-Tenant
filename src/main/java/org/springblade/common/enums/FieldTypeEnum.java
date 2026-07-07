package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 字段类型枚举类
 */
public enum FieldTypeEnum {
	/**
	 * 字符串类型
	 */
	STRING("STRING", "字符串"),

	/**
	 * 整数类型
	 */
	INT("INT", "整数"),

	/**
	 * 大整数类型
	 */
	BIGINT("BIGINT", "大整数"),

	/**
	 * 长整数类型
	 */
	LONG("LONG", "长整数"),

	/**
	 * 浮点数类型
	 */
	FLOAT("FLOAT", "浮点数"),

	/**
	 * 双精度浮点数类型
	 */
	DOUBLE("DOUBLE", "双精度浮点数"),

	/**
	 * 布尔类型
	 */
	BOOLEAN("BOOLEAN", "布尔值"),

	/**
	 * 字符串类型
	 */
	CHAR("CHAR", "定长字符串"),

	/**
	 * 短整型类型
	 */
	SHORT("SHORT", "短整型"),

	/**
	 * 高精度
	 */
	DECIMAL("DECIMAL", "高精度"),

	/**
	 * 日期类型
	 */
	DATE("DATE", "日期"),

	/**
	 * 时间戳类型
	 */
	DATE_TIME("DATE_TIME", "日期时间"),

	/**
	 * 字符串类型
	 */
	TEXT("TEXT", "大字符串"),

	/**
	 * 字符串类型
	 */
	LONGTEXT("LONGTEXT", "超大字符串"),

	/**
	 * JSON
	 */
	JSON("JSON", "JSON"),

	/**
	 * POINT
	 */
	POINT("POINT", "点坐标"),

	/**
	 * GEOMETRY
	 */
	GEOMETRY("GEOMETRY", "面坐标");

	@EnumValue
	private final String code;
	private final String description;

	FieldTypeEnum(String code, String description) {
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
	 * 根据code获取枚举值
	 *
	 * @param code 类型代码
	 * @return 对应的枚举值
	 */
	public static FieldTypeEnum fromCode(String code) {
		for (FieldTypeEnum fieldType : FieldTypeEnum.values()) {
			if (fieldType.code.equals(code)) {
				return fieldType;
			}
		}
		throw new IllegalArgumentException("Unknown field type code: " + code);
	}

	@Override
	public String toString() {
		return code;
	}
}
