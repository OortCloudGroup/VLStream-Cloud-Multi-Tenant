package org.springblade.vlstream.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Training status enum
 *
 * @author Administrator
 */
@Getter
public enum AlgorithmTrainingStatusEnum {

	pending("pending", "wait"),
	training("training", "in training"),
	completed("completed", "Finish"),
	failed("failed", "fail"),
	stop("stop", "stop");

	@EnumValue
	private final String code;
	private final String description;

	AlgorithmTrainingStatusEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * according tocodeGet enumeration
	 *
	 * @param code status code
	 * @return enumeration object
	 */
	public static AlgorithmTrainingStatusEnum of(String code) {
		if (code == null) {
			return null;
		}
		for (AlgorithmTrainingStatusEnum algorithmTrainingStatusEnum : values()) {
			if (algorithmTrainingStatusEnum.getCode().equals(code)) {
				return algorithmTrainingStatusEnum;
			}
		}
		return null;
	}
}
