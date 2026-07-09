package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Database type enum
 *
 * @author
 * @since 2025-07-28
 */
@Getter
@AllArgsConstructor
public enum DatabaseTypeEnum {
	/**
	 * Oracledatabase
	 */
	Oracle("Oracle", "Oracle"),

	/**
	 * PostgreSQLdatabase
	 */
	PostgreSQL("PostgreSQL", "PostgreSQL"),

	/**
	 * SqlServerdatabase
	 */
	SqlServer("SqlServer", "SqlServer"),

	/**
	 * MySQLdatabase
	 */
	MySQL("MySQL", "MySQL");

	@EnumValue
	private final String code;
	private final String name;

	/**
	 * Get enumeration based on code
	 *
	 * @param code code
	 * @return enumerate
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
