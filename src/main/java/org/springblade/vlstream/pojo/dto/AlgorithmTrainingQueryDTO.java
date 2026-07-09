package org.springblade.vlstream.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Algorithm training task queryDTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlgorithmTrainingQueryDTO", description = "Algorithm training task query parameters")
public class AlgorithmTrainingQueryDTO {

    @ApiModelProperty(value = "Current page number", example = "1")
    private Integer current = 1;

    @ApiModelProperty(value = "page size", example = "10")
    private Integer size = 10;

    @ApiModelProperty(value = "Task name(fuzzy search)")
    private String taskName;

    @ApiModelProperty(value = "algorithmID")
    private Long algorithmId;

    @ApiModelProperty(value = "DatasetID")
    private Long datasetId;

    @ApiModelProperty(value = "training type")
    private String trainType;

    @ApiModelProperty(value = "training status")
    private String trainStatus;

    @ApiModelProperty(value = "Creator")
    private Long createdBy;

    @ApiModelProperty(value = "start time-start range")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTimeBegin;

    @ApiModelProperty(value = "start time-end range")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTimeEnd;

    @ApiModelProperty(value = "creation time-start range")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTimeBegin;

    @ApiModelProperty(value = "creation time-end range")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTimeEnd;

    @ApiModelProperty(value = "sort field", example = "created_time")
    private String orderBy = "created_time";

    @ApiModelProperty(value = "sort by", example = "desc")
    private String order = "desc";
}
