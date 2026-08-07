package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 档案分类枚举
 */
public enum CategoryEnum {

	/**
	 * 未归管理
	 */
	WSWG("WSWG", "未归管理"),

	/**
	 * 归档管理
	 */
	WSGD("WSGD", "归档管理"),

	/**
	 * 案卷管理
	 */
	WSAJ("WSAJ", "案卷管理"),

	/**
	 * 资料管理
	 */
	WSZL("WSZL", "资料管理");

	@EnumValue
	private final String code;
	private final String description;

	CategoryEnum(String code, String description) {
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
	public static CategoryEnum fromCode(String code) {
		for (CategoryEnum category : values()) {
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
			.collect(Collectors.toMap(CategoryEnum::getCode, CategoryEnum::getDescription));
	}

	/**
	 * 获取枚举数组，每个元素包含code和description
	 *
	 * @return 包含code和description的数组
	 */
	public static Map<String, String>[] toArray() {
		return Arrays.stream(values())
			.map(enumValue -> {
				Map<String, String> map = new HashMap<>();
				map.put("code", enumValue.getCode());
				map.put("description", enumValue.getDescription());
				map.put("other", "所有文件");
				return map;
			})
			.toArray(Map[]::new);
	}

}
