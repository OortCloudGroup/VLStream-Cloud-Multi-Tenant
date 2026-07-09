package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * Field type enumeration class
 */
public enum FieldTypeEnum {
	/**
	 * string type
	 */
	STRING("STRING", "string"),

	/**
	 * integer type
	 */
	INT("INT", "integer"),

	/**
	 * large integer type
	 */
	BIGINT("BIGINT", "large integer"),

	/**
	 * long integer type
	 */
	LONG("LONG", "long integer"),

	/**
	 * floating point type
	 */
	FLOAT("FLOAT", "floating point number"),

	/**
	 * Double precision floating point type
	 */
	DOUBLE("DOUBLE", "Double precision floating point number"),

	/**
	 * Boolean type
	 */
	BOOLEAN("BOOLEAN", "Boolean value"),

	/**
	 * string type
	 */
	CHAR("CHAR", "Fixed length string"),

	/**
	 * Short type
	 */
	SHORT("SHORT", "Short"),

	/**
	 * High precision
	 */
	DECIMAL("DECIMAL", "High precision"),

	/**
	 * date type
	 */
	DATE("DATE", "date"),

	/**
	 * timestamp type
	 */
	DATE_TIME("DATE_TIME", "date time"),

	/**
	 * string type
	 */
	TEXT("TEXT", "large string"),

	/**
	 * string type
	 */
	LONGTEXT("LONGTEXT", "Very large string"),

	/**
	 * JSON
	 */
	JSON("JSON", "JSON"),

	/**
	 * POINT
	 */
	POINT("POINT", "Point coordinates"),

	/**
	 * GEOMETRY
	 */
	GEOMETRY("GEOMETRY", "Surface coordinates");

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
	 * according tocodeGet enumeration value
	 *
	 * @param code type code
	 * @return The corresponding enumeration value
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
