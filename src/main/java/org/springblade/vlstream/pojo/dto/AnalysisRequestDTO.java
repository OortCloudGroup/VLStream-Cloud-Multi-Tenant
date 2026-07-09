package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.AnalysisRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Intelligent analysis request form Data transfer object entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnalysisRequestDTO extends AnalysisRequest {
	@Serial
	private static final long serialVersionUID = 1L;

}
