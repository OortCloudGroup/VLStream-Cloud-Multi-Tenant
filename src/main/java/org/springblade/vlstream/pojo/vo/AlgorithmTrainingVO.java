package org.springblade.vlstream.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springblade.vlstream.enums.AlgorithmCategoryEnum;
import org.springblade.vlstream.pojo.entity.AlgorithmTraining;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Algorithm training task list View entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlgorithmTrainingVO extends AlgorithmTraining {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Algorithm name")
	private String algorithmName;

	@Schema(description = "Algorithm type")
	private AlgorithmCategoryEnum trainType;

	@Schema(description = "Corresponding model")
	private String targetModel;

	@Schema(description = "Data set name")
	private String datasetName;

	@Schema(description = "Creator name")
	private String createdByName;

	@Schema(description = "Training duration(minute)")
	private Long durationMinutes;

	@Schema(description = "Training status description")
	private String trainStatusDesc;
}
