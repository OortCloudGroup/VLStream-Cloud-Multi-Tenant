package org.springblade.vlstream.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Algorithm model creationDTO
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlgorithmModelCreateDTO", description = "Algorithm model creation parameters")
public class AlgorithmModelCreateDTO {

    @ApiModelProperty(value = "Model name", required = true, example = "Target detection modelv1.0")
    @NotBlank(message = "Model name cannot be empty")
    @Size(max = 100, message = "The model name cannot be longer than100character")
    private String modelName;

    @ApiModelProperty(value = "algorithmID", required = true, example = "1")
    @NotNull(message = "algorithmIDcannot be empty")
    private Long algorithmId;

    @ApiModelProperty(value = "training tasksID", example = "1")
    private Long trainingId;

    @ApiModelProperty(value = "model version", required = true, example = "1")
    private Integer version;

    @ApiModelProperty(value = "Model format", example = "onnx")
    @Pattern(regexp = "^(onnx|pt|rknn)$", message = "The model format can only beonnx、pt、rknn")
    private String modelFormat;

    @ApiModelProperty(value = "Model size", example = "100MB")
    @Size(max = 20, message = "The length of the model size description cannot exceed20character")
    private String modelSize;

    @ApiModelProperty(value = "Model file path", required = true, example = "/models/detection/v1.0/model.onnx")
    @NotBlank(message = "Model file path cannot be empty")
    @Size(max = 500, message = "The model file path length cannot exceed500character")
    private String modelPath;

    @ApiModelProperty(value = "Model accuracy", example = "0.95")
    private BigDecimal accuracy;

    @ApiModelProperty(value = "Model description", example = "based onYOLOv5target detection model")
    private String description;

    @ApiModelProperty(value = "state", example = "draft")
    @Pattern(regexp = "^(draft|testing|published)$", message = "The status can only bedraft、testingorpublished")
    private String status = "draft";
}
