package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 容器实例更新DTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ContainerInstanceUpdateDTO", description = "容器实例更新参数")
public class ContainerInstanceUpdateDTO {

    @ApiModelProperty(value = "容器实例ID", required = true)
    private Long id;

    @ApiModelProperty(value = "实例名称")
    private String instanceName;

    @ApiModelProperty(value = "镜像类型：base-基础镜像,app-应用镜像,custom-自定义镜像,url-镜像地址")
    private String imageType;

    @ApiModelProperty(value = "容器ID")
    private String containerId;

    @ApiModelProperty(value = "实例状态：running-运行中,stopped-已停止,error-错误,starting-启动中,stopping-停止中")
    private String instanceStatus;

    @ApiModelProperty(value = "资源类型ID")
    private Long resourceTypeId;

    @ApiModelProperty(value = "资源规格ID")
    private Long resourceSpecId;

    @ApiModelProperty(value = "实例数量")
    private Integer instanceCount;

    @ApiModelProperty(value = "健康状态：healthy-健康,unhealthy-不健康,unknown-未知")
    private String healthStatus;

    @ApiModelProperty(value = "重启次数")
    private Integer restartCount;

    @ApiModelProperty(value = "CPU使用率")
    private BigDecimal cpuUsage;

    @ApiModelProperty(value = "内存使用率")
    private BigDecimal memoryUsage;

    @ApiModelProperty(value = "GPU使用率")
    private BigDecimal gpuUsage;

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
