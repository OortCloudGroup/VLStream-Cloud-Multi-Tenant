package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 地图数据类型枚举
 *
 * @author Oort
 * @since 2025-07-28
 */
public enum MapDataTypeEnum {

	/**
	 * WMS地图服务
	 */
	WMS("WMS", "WMS地图服务"),

	/**
	 * 瓦片地图服务
	 */
	TILE("TILE", "瓦片地图服务"),

	/**
	 * WFS地图服务
	 */
	WFS("WFS", "WFS地图服务"),

	/**
	 * WCS地图服务
	 */
	WCS("WCS", "WCS地图服务"),

	/**
	 * WMTS地图服务
	 */
	WMTS("WMTS", "WMTS地图服务"),

	/**
	 * 自定义地图服务
	 */
	CUSTOM("CUSTOM", "自定义地图服务");

	@EnumValue
	private final String value;
	private final String description;

	MapDataTypeEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return value;
	}

	/**
	 * 根据值获取枚举对象
	 *
	 * @param value 枚举值
	 * @return 枚举对象
	 */
	public static MapDataTypeEnum fromValue(String value) {
		for (MapDataTypeEnum dataType : MapDataTypeEnum.values()) {
			if (dataType.getValue().equals(value)) {
				return dataType;
			}
		}
		throw new IllegalArgumentException("未知的地图数据类型: " + value);
	}
}
