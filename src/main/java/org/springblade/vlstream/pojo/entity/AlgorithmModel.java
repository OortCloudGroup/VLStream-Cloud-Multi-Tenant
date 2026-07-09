package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Algorithm model table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_algorithm_model")
@Schema(description = "VlsAlgorithmModelEntityobject")
@EqualsAndHashCode(callSuper = true)
public class AlgorithmModel extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Model name
	 */
	@Schema(description = "Model name")
	private String modelName;
	/**
	 * algorithmID
	 */
	@Schema(description = "algorithmID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long algorithmId;
	/**
	 * training tasksID
	 */
	@Schema(description = "training tasksID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long trainingId;
	/**
	 * model version
	 */
	@Schema(description = "model version")
	private Integer version;
	/**
	 * Model format: ONNX,PyTorch,TensorFlow
	 */
	@Schema(description = "Model format: ONNX,PyTorch,TensorFlow")
	private String modelFormat;
	/**
	 * Model size
	 */
	@Schema(description = "Model size")
	private String modelSize;
	/**
	 * Model file path
	 */
	@Schema(description = "Model file path")
	private String modelPath;
	/**
	 * onnxModel file path
	 */
	@Schema(description = "onnxModel file path")
	private String onnxModelPath;
	/**
	 * rknnModel file path
	 */
	@Schema(description = "rknnModel file path")
	private String rknnModelPath;
	/**
	 * int8 rknnModel output path
	 */
	@Schema(description = "int8 rknnModel output path")
	private String int8RknnModelOutputPath;
	/**
	 * Model accuracy
	 */
	@Schema(description = "Model accuracy")
	private BigDecimal accuracy;
	/**
	 * Model description
	 */
	@Schema(description = "Model description")
	private String description;
	/**
	 * Number of downloads
	 */
	@Schema(description = "Number of downloads")
	private Integer downloadCount;
	/**
	 * Deployment times
	 */
	@Schema(description = "Deployment times")
	private Integer deployCount;
	/**
	 * Release time
	 */
	@Schema(description = "Release time")
	private LocalDateTime publishTime;

}
