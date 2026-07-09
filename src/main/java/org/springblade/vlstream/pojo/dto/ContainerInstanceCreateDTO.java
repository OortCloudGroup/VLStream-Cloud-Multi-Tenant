package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Container instance creationDTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ContainerInstanceCreateDTO", description = "Container instance creation parameters")
public class ContainerInstanceCreateDTO {

    @ApiModelProperty(value = "Instance name", required = true)
    @NotBlank(message = "Instance name cannot be empty")
    private String instanceName;

    @ApiModelProperty(value = "Image name", required = true)
    @NotBlank(message = "Image name cannot be empty")
    private String imageName;

    @ApiModelProperty(value = "Image type: base-base image,app-Application image,custom-Custom image,url-Mirror address", required = true)
    @NotBlank(message = "Image type cannot be empty")
    private String imageType;

    @ApiModelProperty(value = "Mirror tag")
    private String imageTag = "latest";

    @ApiModelProperty(value = "algorithmID")
    private Long algorithmId;

    @ApiModelProperty(value = "Instance type", required = true)
    @NotBlank(message = "Instance type cannot be empty")
    private String instanceType;

    @ApiModelProperty(value = "Resource typeID", required = true)
    @NotNull(message = "Resource type cannot be empty")
    private Long resourceTypeId;

    @ApiModelProperty(value = "Resource specificationID", required = true)
    @NotNull(message = "Resource specification cannot be empty")
    private Long resourceSpecId;

    @ApiModelProperty(value = "Number of instances", required = true)
    @NotNull(message = "The number of instances cannot be empty")
    private Integer instanceCount;

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
