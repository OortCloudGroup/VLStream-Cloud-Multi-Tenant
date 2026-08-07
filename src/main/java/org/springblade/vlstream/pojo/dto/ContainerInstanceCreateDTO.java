package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 容器实例创建DTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ContainerInstanceCreateDTO", description = "容器实例创建参数")
public class ContainerInstanceCreateDTO {

    @ApiModelProperty(value = "实例名称", required = true)
    @NotBlank(message = "实例名称不能为空")
    private String instanceName;

    @ApiModelProperty(value = "镜像名称", required = true)
    @NotBlank(message = "镜像名称不能为空")
    private String imageName;

    @ApiModelProperty(value = "镜像类型：base-基础镜像,app-应用镜像,custom-自定义镜像,url-镜像地址", required = true)
    @NotBlank(message = "镜像类型不能为空")
    private String imageType;

    @ApiModelProperty(value = "镜像标签")
    private String imageTag = "latest";

    @ApiModelProperty(value = "算法ID")
    private Long algorithmId;

    @ApiModelProperty(value = "实例类型", required = true)
    @NotBlank(message = "实例类型不能为空")
    private String instanceType;

    @ApiModelProperty(value = "资源类型ID", required = true)
    @NotNull(message = "资源类型不能为空")
    private Long resourceTypeId;

    @ApiModelProperty(value = "资源规格ID", required = true)
    @NotNull(message = "资源规格不能为空")
    private Long resourceSpecId;

    @ApiModelProperty(value = "实例数量", required = true)
    @NotNull(message = "实例数量不能为空")
    private Integer instanceCount;

    @ApiModelProperty(value = "CPU限制")
    private String cpuLimit;

    @ApiModelProperty(value = "内存限制")
    private String memoryLimit;

    @ApiModelProperty(value = "GPU限制")
    private String gpuLimit;

    @ApiModelProperty(value = "端口配置（JSON格式）")
    private String portConfig;

    @ApiModelProperty(value = "环境变量配置（JSON格式）")
    private String envConfig;

    @ApiModelProperty(value = "存储卷配置（JSON格式）")
    private String volumeConfig;

    @ApiModelProperty(value = "日志路径")
    private String logsPath;
}
