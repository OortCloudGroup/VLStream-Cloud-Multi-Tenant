package org.springblade.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Primary key strategy enumeration
 *
 * @author zhonghuixiong
 */
@Schema(description = "Primary key strategy enumeration")
public enum IdStrategyEnum {

	@Schema(description = "SEQsequence")
	SEQ("SEQ", "sequence"),

	@Schema(description = "AUTOself-increasing")
	AUTO("AUTO", "self-increasing"),

	@Schema(description = "UUIDself-increasingUID")
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
