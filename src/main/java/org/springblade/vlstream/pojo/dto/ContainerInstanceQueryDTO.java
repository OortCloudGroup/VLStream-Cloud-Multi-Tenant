package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Container instance query conditionsDTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ContainerInstanceQueryDTO", description = "Container instance query conditions")
public class ContainerInstanceQueryDTO {

    @ApiModelProperty(value = "Instance name")
    private String instanceName;

    @ApiModelProperty(value = "containerID")
    private String containerId;

    @ApiModelProperty(value = "Image name")
    private String imageName;

    @ApiModelProperty(value = "algorithmID")
    private Long algorithmId;

    @ApiModelProperty(value = "Instance type")
    private String instanceType;

    @ApiModelProperty(value = "Instance status: running-Running,stopped-Stopped,error-mistake,starting-Starting,stopping-Stopping")
    private String instanceStatus;

    @ApiModelProperty(value = "health status: healthy-healthy,unhealthy-unhealthy,unknown-unknown")
    private String healthStatus;

    @ApiModelProperty(value = "Creator")
    private Long createdBy;

    @ApiModelProperty(value = "Creation time starts")
    private LocalDateTime createdTimeStart;

    @ApiModelProperty(value = "Creation time ends")
    private LocalDateTime createdTimeEnd;

    @ApiModelProperty(value = "Start time starts")
    private LocalDateTime startTimeStart;

    @ApiModelProperty(value = "Start time ends")
    private LocalDateTime startTimeEnd;

    @ApiModelProperty(value = "sort field")
    private String orderBy;

    @ApiModelProperty(value = "sort by: asc-Ascending order, desc-descending order")
    private String order;
}
