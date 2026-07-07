package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 档案分类枚举
 */
public enum GovDaCategoryEnum {

	/**
	 * 未归档文件分类
	 */
	WGWJFL("WGWJFL", "未归档文件分类"),

	/**
	 * 归档文件分类
	 */
	GDWJFL("GDWJFL", "归档文件分类"),

	/**
	 * 案卷分类
	 */
	AJFL("AJFL", "案卷分类"),

	/**
	 * 资料分类
	 */
	ZLFL("ZLFL", "资料分类");

	@EnumValue
	private final String code;
	private final String description;

	GovDaCategoryEnum(String code, String description) {
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
	public static GovDaCategoryEnum fromCode(String code) {
		for (GovDaCategoryEnum category : values()) {
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
			.collect(Collectors.toMap(GovDaCategoryEnum::getCode, GovDaCategoryEnum::getDescription));
	}

}
