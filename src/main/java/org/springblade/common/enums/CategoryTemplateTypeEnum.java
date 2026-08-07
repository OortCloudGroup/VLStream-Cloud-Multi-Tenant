package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * 分类界面设计模板类型枚举
 *
 * @author Oort
 * @since 2025-10-21
 */
@Getter
@AllArgsConstructor
public enum CategoryTemplateTypeEnum {

	/**
	 * 浏览界面
	 */
	BROWSE(1, "浏览界面"),

	/**
	 * 检索界面
	 */
	SEARCH(2, "检索界面"),

	/**
	 * 录入界面
	 */
	INPUT(3, "录入界面"),

	/**
	 * 详情界面
	 */
	DETAIL(4, "详情界面"),

	/**
	 * 统计界面
	 */
	STATISTICS(5, "统计界面"),

	/**
	 * 补充著录界面
	 */
	SUPPLEMENT_INSERT(6, "补充著录界面");

	/**
	 * 类型值
	 */
	@EnumValue
	@JsonValue
	private final Integer value;

	/**
	 * 类型描述
	 */
	private final String description;

	/**
	 * 根据值获取枚举
	 *
	 * @param value 类型值
	 * @return 枚举对象，未找到返回null
	 */
	public static CategoryTemplateTypeEnum getByValue(Integer value) {
		if (value == null) {
			return null;
		}
		for (CategoryTemplateTypeEnum type : CategoryTemplateTypeEnum.values()) {
			if (type.getValue().equals(value)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * 判断值是否有效
	 *
	 * @param value 类型值
	 * @return 是否有效
	 */
	public static boolean isValid(Integer value) {
		return getByValue(value) != null;
	}

	/**
	 * 获取描述
	 *
	 * @param value 类型值
	 * @return 描述信息
	 */
	public static String getDescriptionByValue(Integer value) {
		CategoryTemplateTypeEnum type = getByValue(value);
		return type != null ? type.getDescription() : null;
	}
}
