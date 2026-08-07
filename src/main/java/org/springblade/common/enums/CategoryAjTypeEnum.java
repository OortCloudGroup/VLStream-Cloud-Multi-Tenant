package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 档案分类案卷类型枚举
 */
public enum CategoryAjTypeEnum {

	/**
	 * 卷内JN 案卷AN
	 */
	JN("JN", "卷内"),

	/**
	 * 案卷
	 */
	AJ("AJ", "案卷");

	@EnumValue
	private final String code;
	private final String description;

	CategoryAjTypeEnum(String code, String description) {
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
	 * 根据code获取枚举
	 *
	 * @param code 分类code
	 * @return 对应的枚举值，如果未找到则返回null
	 */
	public static CategoryAjTypeEnum fromCode(String code) {
		for (CategoryAjTypeEnum category : values()) {
			if (category.getCode().equals(code)) {
				return category;
			}
		}
		return null;
	}

	/**
	 * 获取枚举map，key为code，value为枚举实例
	 *
	 * @return 枚举map
	 */
	public static Map<String, String> toMap() {
		return Arrays.stream(values())
			.collect(Collectors.toMap(CategoryAjTypeEnum::getCode, CategoryAjTypeEnum::getDescription));
	}

}
