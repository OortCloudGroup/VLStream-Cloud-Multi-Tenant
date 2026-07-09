package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Container instance updateDTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ContainerInstanceUpdateDTO", description = "Container instance update parameters")
public class ContainerInstanceUpdateDTO {

    @ApiModelProperty(value = "Container instanceID", required = true)
    private Long id;

    @ApiModelProperty(value = "Instance name")
    private String instanceName;

    @ApiModelProperty(value = "Image type: base-base image,app-Application image,custom-Custom image,url-Mirror address")
    private String imageType;

    @ApiModelProperty(value = "containerID")
    private String containerId;

    @ApiModelProperty(value = "Instance status: running-Running,stopped-Stopped,error-mistake,starting-Starting,stopping-Stopping")
    private String instanceStatus;

    @ApiModelProperty(value = "Resource typeID")
    private Long resourceTypeId;

    @ApiModelProperty(value = "Resource specificationID")
    private Long resourceSpecId;

    @ApiModelProperty(value = "Number of instances")
    private Integer instanceCount;

    @ApiModelProperty(value = "health status: healthy-healthy,unhealthy-unhealthy,unknown-unknown")
    private String healthStatus;

    @ApiModelProperty(value = "Number of restarts")
    private Integer restartCount;

    @ApiModelProperty(value = "CPUUsage rate")
    private BigDecimal cpuUsage;

    @ApiModelProperty(value = "memory usage")
    private BigDecimal memoryUsage;

    @ApiModelProperty(value = "GPUUsage rate")
    private BigDecimal gpuUsage;

    @ApiModelProperty(value = "CPUlimit")
    private String cpuLimit;

    @ApiModelProperty(value = "memory limit")
    private String memoryLimit;

    @ApiModelProperty(value = "GPUlimit")
    private String gpuLimit;

    @ApiModelProperty(value = "Port configuration(JSONFormat)")
    private String portConfig;

    @ApiModelProperty(value = "Environment variable configuration(JSONFormat)")
    private String envConfig;

    @ApiModelProperty(value = "Storage volume configuration(JSONFormat)")
    private String volumeConfig;

    @ApiModelProperty(value = "Log path")
    private String logsPath;
}
