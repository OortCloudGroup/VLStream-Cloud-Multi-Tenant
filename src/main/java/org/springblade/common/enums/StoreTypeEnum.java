package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 存储类型枚举
 *
 * @author zhonghuixiong
 */
@Schema(description = "存储类型")
public enum StoreTypeEnum {

	/**
	 * MySQL Geometry类型
	 */
	MySQLGeometry("MySQLGeometry", "MySQL Geometry类型"),

	/**
	 * 点类型(Point)
	 */
	Point("Point", "点类型"),

	/**
	 * 几何类型(Geometry)
	 */
	Geometry("Geometry", "几何类型"),

	/**
	 * 坐标类型(Coordinates)
	 */
	Coordinates("Coordinates", "坐标类型");

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
