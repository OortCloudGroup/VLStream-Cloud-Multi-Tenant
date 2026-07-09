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
 * Container instance table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsContainerInstanceExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Instance name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Instance name")
	private String instanceName;
	/**
	 * containerID
	 */
	@ColumnWidth(20)
	@ExcelProperty("containerID")
	private String containerId;
	/**
	 * Image name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Image name")
	private String imageName;
	/**
	 * Mirror tag
	 */
	@ColumnWidth(20)
	@ExcelProperty("Mirror tag")
	private String imageTag;
	/**
	 * algorithmID
	 */
	@ColumnWidth(20)
	@ExcelProperty("algorithmID")
	private Long algorithmId;
	/**
	 * Instance type
	 */
	@ColumnWidth(20)
	@ExcelProperty("Instance type")
	private String instanceType;
	/**
	 * CPUlimit
	 */
	@ColumnWidth(20)
	@ExcelProperty("CPUlimit")
	private String cpuLimit;
	/**
	 * memory limit
	 */
	@ColumnWidth(20)
	@ExcelProperty("memory limit")
	private String memoryLimit;
	/**
	 * GPUlimit
	 */
	@ColumnWidth(20)
	@ExcelProperty("GPUlimit")
	private String gpuLimit;
	/**
	 * Port configuration
	 */
	@ColumnWidth(20)
	@ExcelProperty("Port configuration")
	private String portConfig;
	/**
	 * Environment variable configuration
	 */
	@ColumnWidth(20)
	@ExcelProperty("Environment variable configuration")
	private String envConfig;
	/**
	 * Storage volume configuration
	 */
	@ColumnWidth(20)
	@ExcelProperty("Storage volume configuration")
	private String volumeConfig;
	/**
	 * Instance status: running-Running,stopped-Stopped,error-mistake,starting-Starting,stopping-Stopping
	 */
	@ColumnWidth(20)
	@ExcelProperty("Instance status: running-Running,stopped-Stopped,error-mistake,starting-Starting,stopping-Stopping")
	private String instanceStatus;
	/**
	 * health status: healthy-healthy,unhealthy-unhealthy,unknown-unknown
	 */
	@ColumnWidth(20)
	@ExcelProperty("health status: healthy-healthy,unhealthy-unhealthy,unknown-unknown")
	private String healthStatus;
	/**
	 * Start time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Start time")
	private LocalDateTime startTime;
	/**
	 * stop time
	 */
	@ColumnWidth(20)
	@ExcelProperty("stop time")
	private LocalDateTime stopTime;
	/**
	 * Number of restarts
	 */
	@ColumnWidth(20)
	@ExcelProperty("Number of restarts")
	private Integer restartCount;
	/**
	 * CPUUsage rate
	 */
	@ColumnWidth(20)
	@ExcelProperty("CPUUsage rate")
	private BigDecimal cpuUsage;
	/**
	 * memory usage
	 */
	@ColumnWidth(20)
	@ExcelProperty("memory usage")
	private BigDecimal memoryUsage;
	/**
	 * GPUUsage rate
	 */
	@ColumnWidth(20)
	@ExcelProperty("GPUUsage rate")
	private BigDecimal gpuUsage;
	/**
	 * Log path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Log path")
	private String logsPath;

}
