package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据源类型枚举
 *
 * @author
 * @since 2025-07-28
 */
@Getter
@AllArgsConstructor
public enum DatasourceCategoryEnum {

	/**
	 * 主表
	 */
	MAIN_TABLE("MAIN_TABLE", "主表"),

	/**
	 * 中台服务
	 */
	SERVICE("SERVICE", "服务"),

	/**
	 * 库表
	 */
	DATABASE("DATABASE", "库表"),

	/**
	 * 接口
	 */
	INTERFACE("INTERFACE", "接口");

	@EnumValue
	private final String code;
	private final String name;

	/**
	 * 根据代码获取枚举
	 *
	 * @param code 代码
	 * @return 枚举
	 */
	public static DatasourceCategoryEnum fromCode(String code) {
		for (DatasourceCategoryEnum category : DatasourceCategoryEnum.values()) {
			if (category.code.equals(code)) {
				return category;
			}
		}
		return null;
	}
}
