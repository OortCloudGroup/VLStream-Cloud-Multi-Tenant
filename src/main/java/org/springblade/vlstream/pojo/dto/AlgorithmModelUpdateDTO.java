package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 算法模型更新DTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlgorithmModelUpdateDTO", description = "算法模型更新参数")
public class AlgorithmModelUpdateDTO {

    @ApiModelProperty(value = "模型ID", required = true, example = "1")
    private Long id;

    @ApiModelProperty(value = "模型名称", example = "目标检测模型v1.0")
    private String modelName;

    @ApiModelProperty(value = "模型版本", example = "1")
    private Integer version;

    @ApiModelProperty(value = "模型格式", example = "onnx")
    private String modelFormat;

    @ApiModelProperty(value = "模型大小", example = "100MB")
    private String modelSize;

    @ApiModelProperty(value = "模型文件路径", example = "/models/detection/v1.0/model.onnx")
    private String modelPath;

    @ApiModelProperty(value = "模型准确率", example = "0.95")
    private BigDecimal accuracy;

    @ApiModelProperty(value = "模型描述", example = "基于YOLOv5的目标检测模型")
    private String description;

    @ApiModelProperty(value = "状态", example = "draft")
    private Integer status;
}
