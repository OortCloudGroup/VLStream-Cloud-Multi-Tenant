package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 主键策略枚举
 *
 * @author zhonghuixiong
 */
@Schema(description = "主键策略枚举")
public enum IdStrategyEnum {

	@Schema(description = "SEQ序列")
	SEQ("SEQ", "序列"),

	@Schema(description = "AUTO自增")
	AUTO("AUTO", "自增"),

	@Schema(description = "UUID自增UID")
	UUID("UUID", "UUID");

	@EnumValue
	private final String code;
	private final String description;

	IdStrategyEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return code;
	}
}
