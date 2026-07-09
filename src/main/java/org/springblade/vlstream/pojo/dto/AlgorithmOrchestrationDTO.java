package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.AlgorithmOrchestration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Algorithm layout table Data transfer object entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlgorithmOrchestrationDTO extends AlgorithmOrchestration {
	@Serial
	private static final long serialVersionUID = 1L;

}
