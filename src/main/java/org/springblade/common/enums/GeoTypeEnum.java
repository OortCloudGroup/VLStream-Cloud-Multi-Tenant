package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 几何类型枚举
 *
 * @author
 * @since 2025-07-28
 */
@Getter
@AllArgsConstructor
public enum GeoTypeEnum {

	/**
	 * 点
	 */
	Point("Point", "点"),
	/**
	 * 线
	 */
	LineString("LineString", "线"),
	/**
	 * 面
	 */
	Polygon("Polygon", "面"),
	/**
	 * 多点
	 */
	MultiPoint("MultiPoint", "多点"),
	/**
	 * 多条线
	 */
	MultiLineString("MultiLineString", "多条线"),
	/**
	 * 多边形
	 */
	MultiPolygon("MultiPolygon", "多边形"),
	/**
	 * 几何
	 */
	Geometry("Geometry", "几何");

	@EnumValue
	private final String value;
	private final String description;

	/**
	 * 根据值获取枚举
	 *
	 * @param value 值
	 * @return 枚举
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
