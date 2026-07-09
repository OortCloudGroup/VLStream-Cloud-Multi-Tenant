package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Recording schedule ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsRecordingScheduleExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * equipmentID
	 */
	@ColumnWidth(20)
	@ExcelProperty("equipmentID")
	private Long deviceId;
	/**
	 * Device name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Device name")
	private String deviceName;
	/**
	 * Plan name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Plan name")
	private String scheduleName;
	/**
	 * Whether to enable: 0-Disable, 1-enable
	 */
	@ColumnWidth(20)
	@ExcelProperty("Whether to enable: 0-Disable, 1-enable")
	private Byte isEnabled;
	/**
	 * Single recording duration(Second, default10minute)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Single recording duration(Second, default10minute)")
	private Integer recordDuration;
	/**
	 * Recording quality
	 */
	@ColumnWidth(20)
	@ExcelProperty("Recording quality")
	private String recordQuality;
	/**
	 * Recording format
	 */
	@ColumnWidth(20)
	@ExcelProperty("Recording format")
	private String recordFormat;
	/**
	 * storage path
	 */
	@ColumnWidth(20)
	@ExcelProperty("storage path")
	private String storagePath;
	/**
	 * retention days(0Indicates permanent retention)
	 */
	@ColumnWidth(20)
	@ExcelProperty("retention days(0Indicates permanent retention)")
	private Integer retentionDays;
	/**
	 * plan type
	 */
	@ColumnWidth(20)
	@ExcelProperty("plan type")
	private String scheduleType;
	/**
	 * time strategyID(whenschedule_typefortime_strategyhour)
	 */
	@ColumnWidth(20)
	@ExcelProperty("time strategyID(whenschedule_typefortime_strategyhour)")
	private Long timeStrategyId;
	/**
	 * start time(whenschedule_typefortime_rangehour)
	 */
	@ColumnWidth(20)
	@ExcelProperty("start time(whenschedule_typefortime_rangehour)")
	private LocalTime startTime;
	/**
	 * end time(whenschedule_typefortime_rangehour)
	 */
	@ColumnWidth(20)
	@ExcelProperty("end time(whenschedule_typefortime_rangehour)")
	private LocalTime endTime;
	/**
	 * Day of the week recording(1-7,comma separated)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Day of the week recording(1-7,comma separated)")
	private String weekdays;
	/**
	 * Last recording time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Last recording time")
	private LocalDateTime lastRecordTime;
	/**
	 * Next recording time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Next recording time")
	private LocalDateTime nextRecordTime;
	/**
	 * Total number of recordings
	 */
	@ColumnWidth(20)
	@ExcelProperty("Total number of recordings")
	private Integer totalRecords;
	/**
	 * Number of failed recordings
	 */
	@ColumnWidth(20)
	@ExcelProperty("Number of failed recordings")
	private Integer failedRecords;

}
