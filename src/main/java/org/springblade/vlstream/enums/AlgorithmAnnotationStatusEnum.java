package org.springblade.vlstream.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Annotation status enumeration
 *
 * @author Administrator
 */
@Getter
public enum AlgorithmAnnotationStatusEnum {

	none("none", "Not labeled"),
	partial("partial", "Partial annotation"),
	completed("completed", "Complete annotation");

	@EnumValue
	private final String code;
	private final String description;

	AlgorithmAnnotationStatusEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * according tocodeGet enumeration
	 *
	 * @param code status code
	 * @return enumeration object
	 */
	public static AlgorithmAnnotationStatusEnum of(String code) {
		if (code == null) {
			return null;
		}
		for (AlgorithmAnnotationStatusEnum algorithmAnnotationStatusEnum : values()) {
			if (algorithmAnnotationStatusEnum.getCode().equals(code)) {
				return algorithmAnnotationStatusEnum;
			}
		}
		return null;
	}
}
