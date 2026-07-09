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
 * event management table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsEventManagementExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * event description
	 */
	@ColumnWidth(20)
	@ExcelProperty("event description")
	private String eventDesc;
	/**
	 * event type
	 */
	@ColumnWidth(20)
	@ExcelProperty("event type")
	private String eventType;
	/**
	 * Report location
	 */
	@ColumnWidth(20)
	@ExcelProperty("Report location")
	private String reportLocation;
	/**
	 * Reporting equipment
	 */
	@ColumnWidth(20)
	@ExcelProperty("Reporting equipment")
	private String reportDevice;
	/**
	 * Report pictures
	 */
	@ColumnWidth(20)
	@ExcelProperty("Report pictures")
	private String reportImg;
	/**
	 * Reporting time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Reporting time")
	private LocalDateTime reportTime;
	/**
	 * event level: low-Low,medium-middle,high-high,urgent-urgent
	 */
	@ColumnWidth(20)
	@ExcelProperty("event level: low-Low,medium-middle,high-high,urgent-urgent")
	private String eventLevel;
	/**
	 * event status: pending-Pending,processing-Processing,completed-Completed,closed-Closed
	 */
	@ColumnWidth(20)
	@ExcelProperty("event status: pending-Pending,processing-Processing,completed-Completed,closed-Closed")
	private String eventStatus;
	/**
	 * Executor
	 */
	@ColumnWidth(20)
	@ExcelProperty("Executor")
	private String executor;
	/**
	 * ExecutorIDlist
	 */
	@ColumnWidth(20)
	@ExcelProperty("ExecutorIDlist")
	private String executorIds;
	/**
	 * event data
	 */
	@ColumnWidth(20)
	@ExcelProperty("event data")
	private String eventData;
	/**
	 * Processing results
	 */
	@ColumnWidth(20)
	@ExcelProperty("Processing results")
	private String handleResult;
	/**
	 * feedback information
	 */
	@ColumnWidth(20)
	@ExcelProperty("feedback information")
	private String feedbackInfo;
	/**
	 * Feedback picture
	 */
	@ColumnWidth(20)
	@ExcelProperty("Feedback picture")
	private String feedbackImg;
	/**
	 * feedback status
	 */
	@ColumnWidth(20)
	@ExcelProperty("feedback status")
	private Integer feedbackStatus;

}
