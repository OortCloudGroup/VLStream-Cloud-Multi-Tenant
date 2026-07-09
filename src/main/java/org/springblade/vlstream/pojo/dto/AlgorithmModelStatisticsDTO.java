package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Algorithm model statisticsDTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlgorithmModelStatisticsDTO", description = "Algorithm model statistics")
public class AlgorithmModelStatisticsDTO {

    @ApiModelProperty(value = "Total number of models")
    private Long totalCount;

    @ApiModelProperty(value = "Number of models in draft status")
    private Long draftCount;

    @ApiModelProperty(value = "Number of test state models")
    private Long testingCount;

    @ApiModelProperty(value = "Number of published models")
    private Long publishedCount;

    @ApiModelProperty(value = "Total downloads")
    private Long totalDownloadCount;

    @ApiModelProperty(value = "total number of deployments")
    private Long totalDeployCount;

    @ApiModelProperty(value = "average accuracy")
    private BigDecimal avgAccuracy;

    @ApiModelProperty(value = "highest accuracy")
    private BigDecimal maxAccuracy;

    @ApiModelProperty(value = "lowest accuracy")
    private BigDecimal minAccuracy;

    @ApiModelProperty(value = "ONNXNumber of format models")
    private Long onnxFormatCount;

    @ApiModelProperty(value = "PyTorchNumber of format models")
    private Long pytorchFormatCount;

    @ApiModelProperty(value = "TensorFlowNumber of format models")
    private Long tensorflowFormatCount;

    @ApiModelProperty(value = "Number of new models this week")
    private Long weeklyNewCount;

    @ApiModelProperty(value = "Number of new models added this month")
    private Long monthlyNewCount;

    @ApiModelProperty(value = "Number of new models this year")
    private Long yearlyNewCount;

    @ApiModelProperty(value = "Most popular model names")
    private String mostPopularModelName;

    @ApiModelProperty(value = "Most popular model downloads")
    private Long mostPopularModelDownloadCount;
}
