package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Algorithm training task creationDTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlgorithmTrainingCreateDTO", description = "Algorithm training task creation parameters")
public class AlgorithmTrainingCreateDTO {

    @ApiModelProperty(value = "Task name", required = true)
    @NotBlank(message = "Task name cannot be empty")
    private String taskName;

    @ApiModelProperty(value = "algorithmID", required = true)
    @NotNull(message = "algorithmIDcannot be empty")
    private Long algorithmId;

    @ApiModelProperty(value = "DatasetID")
    private Long datasetId;

    @ApiModelProperty(value = "training type", required = true)
    @NotBlank(message = "Training type cannot be empty")
    private String trainType;

    @ApiModelProperty(value = "total rounds", example = "100")
    @Min(value = 1, message = "The total number of rounds must be greater than0")
    @Max(value = 10000, message = "The total number of rounds cannot exceed10000")
    private Integer epochTotal = 100;

    @ApiModelProperty(value = "Estimated time")
    private String estimatedTime;

    @ApiModelProperty(value = "Model output path")
    private String modelOutputPath;

    @ApiModelProperty(value = "Log path")
    private String logPath;

    @ApiModelProperty(value = "training parameters(JSONFormat)")
    private String configParams;
}
