package org.springblade.vlstream.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Algorithm type enum
 *
 * @author Administrator
 */
@Getter
public enum AlgorithmCategoryEnum {
	personDetect("personDetect", "Pedestrian detection algorithm"),
	detect("detect", "Target detection algorithm"),
	segment("segment", "Instance segmentation algorithm"),
	semanticSeg("semanticSeg", "Semantic segmentation algorithm"),
	classify("classify", "Image classification algorithm"),
	pose("pose", "Key point detection algorithm"),
	obb("obb", "Rotating target detection algorithm"),
	faceDetect("faceDetect", "Face recognition algorithm");

	@EnumValue
    private final String code;
    private final String description;

    AlgorithmCategoryEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * according tocodeGet enumeration
     *
     * @param code status code
     * @return enumeration object
     */
    public static AlgorithmCategoryEnum of(String code) {
        if (code == null) {
            return null;
        }
        for (AlgorithmCategoryEnum algorithmCategoryEnum : values()) {
            if (algorithmCategoryEnum.getCode().equals(code)) {
                return algorithmCategoryEnum;
            }
        }
        return null;
    }
}
