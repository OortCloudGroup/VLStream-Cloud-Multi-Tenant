package org.springblade.vlstream.excel;


import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Video recording record sheet ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-25
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsVideoRecordExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * vhost
	 */
	@ColumnWidth(20)
	@ExcelProperty("vhost")
	private String vhost;
	/**
	 * app
	 */
	@ColumnWidth(20)
	@ExcelProperty("app")
	private String app;
	/**
	 * stream
	 */
	@ColumnWidth(20)
	@ExcelProperty("stream")
	private String stream;
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
	 * Video file name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Video file name")
	private String fileName;
	/**
	 * Video file path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Video file path")
	private String filePath;
	/**
	 * file size(byte)
	 */
	@ColumnWidth(20)
	@ExcelProperty("file size(byte)")
	private Long fileSize;
	/**
	 * on demandurl
	 */
	@ColumnWidth(20)
	@ExcelProperty("on demandurl")
	private String url;
	/**
	 * Video duration(Second)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Video duration(Second)")
	private Integer duration;
	/**
	 * video format
	 */
	@ColumnWidth(20)
	@ExcelProperty("video format")
	private String format;
	/**
	 * Recording start time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Recording start time")
	private LocalDateTime recordStartTime;
	/**
	 * Recording end time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Recording end time")
	private LocalDateTime recordEndTime;
	/**
	 * recording date(for grouping by date)
	 */
	@ColumnWidth(20)
	@ExcelProperty("recording date(for grouping by date)")
	private LocalDate recordDate;
	/**
	 * Recording status
	 */
	@ColumnWidth(20)
	@ExcelProperty("Recording status")
	private String recordStatus;
	/**
	 * thumbnail path
	 */
	@ColumnWidth(20)
	@ExcelProperty("thumbnail path")
	private String thumbnailPath;

}
