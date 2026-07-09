package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.AnalysisRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Intelligent analysis request form View entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnalysisRequestVO extends AnalysisRequest {
	@Serial
	private static final long serialVersionUID = 1L;

	private String cameraName;

}
