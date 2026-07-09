package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Algorithm training task updateDTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlgorithmTrainingUpdateDTO", description = "Algorithm training task update parameters")
public class AlgorithmTrainingUpdateDTO {

    @ApiModelProperty(value = "training tasksID", required = true)
    private Long id;

    @ApiModelProperty(value = "training status")
    private String trainStatus;

    @ApiModelProperty(value = "Training progress percentage")
    @Min(value = 0, message = "The training progress cannot be less than0")
    @Max(value = 100, message = "The training progress cannot be greater than100")
    private Integer progress;

    @ApiModelProperty(value = "current round")
    @Min(value = 0, message = "The current round cannot be less than0")
    private Integer epochCurrent;

    @ApiModelProperty(value = "Accuracy")
    private BigDecimal accuracy;

    @ApiModelProperty(value = "Accuracy")
    private BigDecimal precisionValue;

    @ApiModelProperty(value = "Recall")
    private BigDecimal recallValue;

    @ApiModelProperty(value = "mAPvalue")
    private BigDecimal mapValue;

    @ApiModelProperty(value = "loss value")
    private BigDecimal lossValue;

    @ApiModelProperty(value = "GPUUsage rate")
    private String gpuUsage;

    @ApiModelProperty(value = "Estimated time")
    private String estimatedTime;

    @ApiModelProperty(value = "Model output path")
    private String modelOutputPath;

    @ApiModelProperty(value = "Log path")
    private String logPath;

    @ApiModelProperty(value = "training parameters(JSONFormat)")
    private String configParams;

    @ApiModelProperty(value = "error message")
    private String errorMessage;
}
