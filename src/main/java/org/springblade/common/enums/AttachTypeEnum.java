package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 附件类型枚举类
 * 1-原文 2-草稿 3-清稿 4-正文 5-附件
 *
 * @author Oort
 * @since 2025-01-01
 */
@Getter
public enum AttachTypeEnum {

	/**
	 * 利用文件
	 */
	UTILIZE("UTILIZE", "利用文件"),

	/**
	 * 原文
	 */
	ORIGINAL("ORIGINAL", "原文"),

	/**
	 * 草稿
	 */
	DRAFT("DRAFT", "草稿"),

	/**
	 * 清稿
	 */
	CLEAN_COPY("CLEAN_COPY", "清稿"),

	/**
	 * 正文
	 */
	MAIN_TEXT("MAIN_TEXT", "正文"),

	/**
	 * 附件
	 */
	ATTACHMENT("ATTACHMENT", "附件");

	@EnumValue
	private final String code;
	private final String description;

	AttachTypeEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * 根据code获取枚举
	 *
	 * @param code 类型码
	 * @return 枚举对象，如果未找到则返回null
	 */
	public static AttachTypeEnum of(String code) {
		if (code == null) {
			return null;
		}
		for (AttachTypeEnum type : values()) {
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		return null;
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
				return map;
			})
			.toArray(Map[]::new);
	}

}

