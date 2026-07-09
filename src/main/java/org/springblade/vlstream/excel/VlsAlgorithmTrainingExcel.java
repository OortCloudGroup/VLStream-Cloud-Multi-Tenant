package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Algorithm training task list ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsAlgorithmTrainingExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Task name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Task name")
	private String taskName;
	/**
	 * algorithmID
	 */
	@ColumnWidth(20)
	@ExcelProperty("algorithmID")
	private Long algorithmId;
	/**
	 * DatasetID
	 */
	@ColumnWidth(20)
	@ExcelProperty("DatasetID")
	private Long datasetId;
	/**
	 * training status: pending-wait,training-in training,completed-Finish,failed-fail
	 */
	@ColumnWidth(20)
	@ExcelProperty("training status: pending-wait,training-in training,completed-Finish,failed-fail")
	private String trainStatus;
	/**
	 * Training progress percentage
	 */
	@ColumnWidth(20)
	@ExcelProperty("Training progress percentage")
	private Integer progress;
	/**
	 * current round
	 */
	@ColumnWidth(20)
	@ExcelProperty("current round")
	private Integer epochCurrent;
	/**
	 * total rounds
	 */
	@ColumnWidth(20)
	@ExcelProperty("total rounds")
	private Integer epochTotal;
	/**
	 * Accuracy
	 */
	@ColumnWidth(20)
	@ExcelProperty("Accuracy")
	private BigDecimal accuracy;
	/**
	 * Accuracy
	 */
	@ColumnWidth(20)
	@ExcelProperty("Accuracy")
	private BigDecimal precisionValue;
	/**
	 * Recall
	 */
	@ColumnWidth(20)
	@ExcelProperty("Recall")
	private BigDecimal recallValue;
	/**
	 * mAPvalue
	 */
	@ColumnWidth(20)
	@ExcelProperty("mAPvalue")
	private BigDecimal mapValue;
	/**
	 * loss value
	 */
	@ColumnWidth(20)
	@ExcelProperty("loss value")
	private BigDecimal lossValue;
	/**
	 * GPUUsage rate
	 */
	@ColumnWidth(20)
	@ExcelProperty("GPUUsage rate")
	private String gpuUsage;
	/**
	 * start time
	 */
	@ColumnWidth(20)
	@ExcelProperty("start time")
	private LocalDateTime startTime;
	/**
	 * end time
	 */
	@ColumnWidth(20)
	@ExcelProperty("end time")
	private LocalDateTime endTime;
	/**
	 * Estimated time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Estimated time")
	private String estimatedTime;
	/**
	 * Model output path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Model output path")
	private String modelOutputPath;
	/**
	 * Log path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Log path")
	private String logPath;
	/**
	 * training parameters
	 */
	@ColumnWidth(20)
	@ExcelProperty("training parameters")
	private String configParams;
	/**
	 * error message
	 */
	@ColumnWidth(20)
	@ExcelProperty("error message")
	private String errorMessage;
	/**
	 * Model file path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Model file path")
	private String modelPath;
	/**
	 * completion time
	 */
	@ColumnWidth(20)
	@ExcelProperty("completion time")
	private String completedAt;

}
