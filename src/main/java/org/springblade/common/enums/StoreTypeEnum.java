package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Storage type enum
 *
 * @author zhonghuixiong
 */
@Schema(description = "storage type")
public enum StoreTypeEnum {

	/**
	 * MySQL Geometrytype
	 */
	MySQLGeometry("MySQLGeometry", "MySQL Geometrytype"),

	/**
	 * point type(Point)
	 */
	Point("Point", "point type"),

	/**
	 * Geometry type(Geometry)
	 */
	Geometry("Geometry", "Geometry type"),

	/**
	 * Coordinate type(Coordinates)
	 */
	Coordinates("Coordinates", "Coordinate type");

	@EnumValue
	private final String value;
	private final String description;

	StoreTypeEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

}
