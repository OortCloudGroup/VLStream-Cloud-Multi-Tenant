package org.springblade.vlstream.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Annotation type enum
 *
 * @author Administrator
 */
@Getter
public enum AlgorithmAnnotationTypeEnum {

	rect("rect", "rectangle"),
	circle("circle", "round"),
	polygon("polygon", "polygon");

	@EnumValue
	private final String code;
	private final String description;

	AlgorithmAnnotationTypeEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * according tocodeGet enumeration
	 *
	 * @param code status code
	 * @return enumeration object
	 */
	public static AlgorithmAnnotationTypeEnum of(String code) {
		if (code == null) {
			return null;
		}
		for (AlgorithmAnnotationTypeEnum algorithmAnnotationTypeEnum : values()) {
			if (algorithmAnnotationTypeEnum.getCode().equals(code)) {
				return algorithmAnnotationTypeEnum;
			}
		}
		return null;
	}
}
