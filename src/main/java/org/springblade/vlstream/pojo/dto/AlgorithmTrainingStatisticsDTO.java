package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Algorithm training task statisticsDTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlgorithmTrainingStatisticsDTO", description = "Algorithm training task statistics")
public class AlgorithmTrainingStatisticsDTO {

    @ApiModelProperty(value = "Total number of tasks")
    private Long totalTasks;

    @ApiModelProperty(value = "Number of waiting tasks")
    private Long pendingTasks;

    @ApiModelProperty(value = "Number of tasks in training")
    private Long trainingTasks;

    @ApiModelProperty(value = "Number of tasks completed")
    private Long completedTasks;

    @ApiModelProperty(value = "Number of failed tasks")
    private Long failedTasks;

    @ApiModelProperty(value = "success rate")
    private BigDecimal successRate;

    @ApiModelProperty(value = "average accuracy")
    private BigDecimal averageAccuracy;

    @ApiModelProperty(value = "average precision")
    private BigDecimal averagePrecision;

    @ApiModelProperty(value = "average recall")
    private BigDecimal averageRecall;

    @ApiModelProperty(value = "averagemAPvalue")
    private BigDecimal averageMap;

    @ApiModelProperty(value = "average training time(minute)")
    private BigDecimal averageDuration;

    @ApiModelProperty(value = "Number of new tasks today")
    private Long todayNewTasks;

    @ApiModelProperty(value = "Number of new tasks this week")
    private Long weeklyNewTasks;

    @ApiModelProperty(value = "Number of new tasks this month")
    private Long monthlyNewTasks;
}
