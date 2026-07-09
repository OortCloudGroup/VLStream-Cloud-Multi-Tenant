package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * Map data type enumeration
 *
 * @author Oort
 * @since 2025-07-28
 */
public enum MapDataTypeEnum {

	/**
	 * WMSmap service
	 */
	WMS("WMS", "WMSmap service"),

	/**
	 * Tile map service
	 */
	TILE("TILE", "Tile map service"),

	/**
	 * WFSmap service
	 */
	WFS("WFS", "WFSmap service"),

	/**
	 * WCSmap service
	 */
	WCS("WCS", "WCSmap service"),

	/**
	 * WMTSmap service
	 */
	WMTS("WMTS", "WMTSmap service"),

	/**
	 * Custom map service
	 */
	CUSTOM("CUSTOM", "Custom map service");

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
	 * Get enumeration object based on value
	 *
	 * @param value enumeration value
	 * @return enumeration object
	 */
	public static MapDataTypeEnum fromValue(String value) {
		for (MapDataTypeEnum dataType : MapDataTypeEnum.values()) {
			if (dataType.getValue().equals(value)) {
				return dataType;
			}
		}
		throw new IllegalArgumentException("Unknown map data type: " + value);
	}
}
