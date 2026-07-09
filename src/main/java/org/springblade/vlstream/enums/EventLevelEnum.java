package org.springblade.vlstream.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Event level enumeration
 *
 * @author Administrator
 */
@Getter
public enum EventLevelEnum {

	low("low", "Low"),
	medium("medium", "middle"),
	high("high", "high"),
	urgent("urgent", "urgent");

	@EnumValue
	private final String code;
	private final String description;

	EventLevelEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * according tocodeGet enumeration
	 *
	 * @param code status code
	 * @return enumeration object
	 */
	public static EventLevelEnum of(String code) {
		if (code == null) {
			return null;
		}
		for (EventLevelEnum eventLevelEnum : values()) {
			if (eventLevelEnum.getCode().equals(code)) {
				return eventLevelEnum;
			}
		}
		return null;
	}
}
