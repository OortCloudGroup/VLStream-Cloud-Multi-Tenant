package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Intelligent analysis request form ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsAnalysisRequestExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Analysis name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Analysis name")
	private String analysisName;
	/**
	 * Analysis type
	 */
	@ColumnWidth(20)
	@ExcelProperty("Analysis type")
	private String analysisType;
	/**
	 * scene name
	 */
	@ColumnWidth(20)
	@ExcelProperty("scene name")
	private String sceneName;
	/**
	 * equipmentIDlist, comma separated
	 */
	@ColumnWidth(20)
	@ExcelProperty("equipmentIDlist, comma separated")
	private String deviceIds;
	/**
	 * Device information
	 */
	@ColumnWidth(20)
	@ExcelProperty("Device information")
	private String deviceInfo;
	/**
	 * algorithmIDlist, comma separated
	 */
	@ColumnWidth(20)
	@ExcelProperty("algorithmIDlist, comma separated")
	private String algorithmIds;
	/**
	 * Area information
	 */
	@ColumnWidth(20)
	@ExcelProperty("Area information")
	private String regionInfo;
	/**
	 * time range
	 */
	@ColumnWidth(20)
	@ExcelProperty("time range")
	private String timeRange;
	/**
	 * Screenshot information
	 */
	@ColumnWidth(20)
	@ExcelProperty("Screenshot information")
	private String screenshots;
	/**
	 * Request status: pending-Pending,processing-Processing,completed-Completed,failed-Processing failed
	 */
	@ColumnWidth(20)
	@ExcelProperty("Request status: pending-Pending,processing-Processing,completed-Completed,failed-Processing failed")
	private String requestStatus;
	/**
	 * Processing progress percentage
	 */
	@ColumnWidth(20)
	@ExcelProperty("Processing progress percentage")
	private Integer progress;
	/**
	 * Result file path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Result file path")
	private String resultPath;
	/**
	 * Start processing time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Start processing time")
	private LocalDateTime startTime;
	/**
	 * completion time
	 */
	@ColumnWidth(20)
	@ExcelProperty("completion time")
	private LocalDateTime completeTime;
	/**
	 * error message
	 */
	@ColumnWidth(20)
	@ExcelProperty("error message")
	private String errorMessage;
	/**
	 * Description information
	 */
	@ColumnWidth(20)
	@ExcelProperty("Description information")
	private String description;

}
