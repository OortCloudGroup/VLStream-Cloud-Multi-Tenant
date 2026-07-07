package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库类型枚举
 *
 * @author
 * @since 2025-07-28
 */
@Getter
@AllArgsConstructor
public enum DatabaseTypeEnum {
	/**
	 * Oracle数据库
	 */
	Oracle("Oracle", "Oracle"),

	/**
	 * PostgreSQL数据库
	 */
	PostgreSQL("PostgreSQL", "PostgreSQL"),

	/**
	 * SqlServer数据库
	 */
	SqlServer("SqlServer", "SqlServer"),

	/**
	 * MySQL数据库
	 */
	MySQL("MySQL", "MySQL");

	@EnumValue
	private final String code;
	private final String name;

	/**
	 * 根据代码获取枚举
	 *
	 * @param code 代码
	 * @return 枚举
	 */
	public static DatabaseTypeEnum fromCode(String code) {
		for (DatabaseTypeEnum databaseType : DatabaseTypeEnum.values()) {
			if (databaseType.code.equals(code)) {
				return databaseType;
			}
		}
		return null;
	}
}
