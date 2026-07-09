package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Geometry type enum
 *
 * @author
 * @since 2025-07-28
 */
@Getter
@AllArgsConstructor
public enum GeoTypeEnum {

	/**
	 * point
	 */
	Point("Point", "point"),
	/**
	 * Wire
	 */
	LineString("LineString", "Wire"),
	/**
	 * noodle
	 */
	Polygon("Polygon", "noodle"),
	/**
	 * More
	 */
	MultiPoint("MultiPoint", "More"),
	/**
	 * multiple lines
	 */
	MultiLineString("MultiLineString", "multiple lines"),
	/**
	 * polygon
	 */
	MultiPolygon("MultiPolygon", "polygon"),
	/**
	 * geometry
	 */
	Geometry("Geometry", "geometry");

	@EnumValue
	private final String value;
	private final String description;

	/**
	 * Get enum based on value
	 *
	 * @param value value
	 * @return enumerate
	 */
	public static GeoTypeEnum fromValue(String value) {
		for (GeoTypeEnum geoTypeEnum : GeoTypeEnum.values()) {
			if (geoTypeEnum.value.equals(value)) {
				return geoTypeEnum;
			}
		}
		return null;
	}
}
