package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Algorithm model updateDTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlgorithmModelUpdateDTO", description = "Algorithm model update parameters")
public class AlgorithmModelUpdateDTO {

    @ApiModelProperty(value = "ModelID", required = true, example = "1")
    private Long id;

    @ApiModelProperty(value = "Model name", example = "Target detection modelv1.0")
    private String modelName;

    @ApiModelProperty(value = "model version", example = "1")
    private Integer version;

    @ApiModelProperty(value = "Model format", example = "onnx")
    private String modelFormat;

    @ApiModelProperty(value = "Model size", example = "100MB")
    private String modelSize;

    @ApiModelProperty(value = "Model file path", example = "/models/detection/v1.0/model.onnx")
    private String modelPath;

    @ApiModelProperty(value = "Model accuracy", example = "0.95")
    private BigDecimal accuracy;

    @ApiModelProperty(value = "Model description", example = "based onYOLOv5target detection model")
    private String description;

    @ApiModelProperty(value = "state", example = "draft")
    private Integer status;
}
