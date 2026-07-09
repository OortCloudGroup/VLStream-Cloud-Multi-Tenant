package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.vlstream.enums.AlgorithmTrainingStatusEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Algorithm training task list Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_algorithm_training")
@Schema(description = "VlsAlgorithmTrainingEntityobject")
@EqualsAndHashCode(callSuper = true)
public class AlgorithmTraining extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Task name
	 */
	@Schema(description = "Task name")
	private String taskName;
	/**
	 * algorithmID
	 */
	@Schema(description = "algorithmID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long algorithmId;
	/**
	 * DatasetID
	 */
	@Schema(description = "DatasetID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long datasetId;
	/**
	 * training status: pending-wait,training-in training,completed-Finish,failed-fail
	 */
	@Schema(description = "training status")
	private AlgorithmTrainingStatusEnum trainStatus;
	/**
	 * Training progress percentage
	 */
	@Schema(description = "Training progress percentage")
	private Integer progress;
	/**
	 * current round
	 */
	@Schema(description = "current round")
	private Integer epochCurrent;
	/**
	 * total rounds
	 */
	@Schema(description = "total rounds")
	private Integer epochTotal;
	/**
	 * Accuracy
	 */
	@Schema(description = "Accuracy")
	private BigDecimal accuracy;
	/**
	 * Accuracy
	 */
	@Schema(description = "Accuracy")
	private BigDecimal precisionValue;
	/**
	 * Recall
	 */
	@Schema(description = "Recall")
	private BigDecimal recallValue;
	/**
	 * mAPvalue
	 */
	@Schema(description = "mAPvalue")
	private BigDecimal mapValue;
	/**
	 * loss value
	 */
	@Schema(description = "loss value")
	private BigDecimal lossValue;
	/**
	 * GPUUsage rate
	 */
	@Schema(description = "GPUUsage rate")
	private String gpuUsage;
	/**
	 * start time
	 */
	@Schema(description = "start time")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private Date startTime;
	/**
	 * end time
	 */
	@Schema(description = "end time")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private Date endTime;
	/**
	 * Estimated time
	 */
	@Schema(description = "Estimated time")
	private String estimatedTime;
	/**
	 * Model output path
	 */
	@Schema(description = "Model output path")
	private String modelOutputPath;
	/**
	 * onnxModel output path
	 */
	@Schema(description = "onnxModel output path")
	private String onnxModelOutputPath;
	/**
	 * rknnModel output path
	 */
	@Schema(description = "rknnModel output path")
	private String rknnModelOutputPath;
	/**
	 * int8 rknnModel output path
	 */
	@Schema(description = "int8 rknnModel output path")
	private String int8RknnModelOutputPath;
	/**
	 * Log path
	 */
	@Schema(description = "Log path")
	private String logPath;
	/**
	 * training parameters
	 */
	@Schema(description = "training parameters")
	private String configParams;
	/**
	 * error message
	 */
	@Schema(description = "error message")
	private String errorMessage;
	/**
	 * Model file path
	 */
	@Schema(description = "Model file path")
	private String modelPath;
	/**
	 * completion time
	 */
	@Schema(description = "completion time")
	private String completedAt;

}
