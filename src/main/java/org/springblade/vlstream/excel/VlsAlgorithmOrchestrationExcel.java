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
 * Algorithm layout table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsAlgorithmOrchestrationExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * arrangement name
	 */
	@ColumnWidth(20)
	@ExcelProperty("arrangement name")
	private String orchestrationName;
	/**
	 * 编排describe
	 */
	@ColumnWidth(20)
	@ExcelProperty("编排describe")
	private String orchestrationDesc;
	/**
	 * Trigger type: realtime-real time,scheduled-timing,manual-Manual
	 */
	@ColumnWidth(20)
	@ExcelProperty("Trigger type: realtime-real time,scheduled-timing,manual-Manual")
	private String triggerType;
	/**
	 * execution mode: serial-serial,parallel-parallel
	 */
	@ColumnWidth(20)
	@ExcelProperty("execution mode: serial-serial,parallel-parallel")
	private String executeMode;
	/**
	 * Algorithm step configuration
	 */
	@ColumnWidth(20)
	@ExcelProperty("Algorithm step configuration")
	private String algorithmSteps;
	/**
	 * Enter configuration
	 */
	@ColumnWidth(20)
	@ExcelProperty("Enter configuration")
	private String inputConfig;
	/**
	 * 输出Configuration
	 */
	@ColumnWidth(20)
	@ExcelProperty("输出Configuration")
	private String outputConfig;
	/**
	 * Number of associated devices
	 */
	@ColumnWidth(20)
	@ExcelProperty("Number of associated devices")
	private Integer deviceCount;
	/**
	 * Number of runs
	 */
	@ColumnWidth(20)
	@ExcelProperty("Number of runs")
	private Integer runCount;
	/**
	 * state: active-active,inactive-inactive,draft-draft
	 */
	@ColumnWidth(20)
	@ExcelProperty("state: active-active,inactive-inactive,draft-draft")
	private String orchestrationStatus;
	/**
	 * Last running time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Last running time")
	private LocalDateTime lastRunTime;
	/**
	 * average running time(Second)
	 */
	@ColumnWidth(20)
	@ExcelProperty("average running time(Second)")
	private Integer avgRunTime;
	/**
	 * success rate
	 */
	@ColumnWidth(20)
	@ExcelProperty("success rate")
	private BigDecimal successRate;

}
