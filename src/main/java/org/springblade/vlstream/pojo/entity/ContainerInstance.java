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
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Container instance table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_container_instance")
@Schema(description = "VlsContainerInstanceEntityobject")
@EqualsAndHashCode(callSuper = true)
public class ContainerInstance extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Instance name
	 */
	@Schema(description = "Instance name")
	private String instanceName;
	/**
	 * containerID
	 */
	@Schema(description = "containerID")
	@JsonSerialize(using = ToStringSerializer.class)
	private String containerId;
	/**
	 * Image name
	 */
	@Schema(description = "Image name")
	private String imageName;
	/**
	 * Image type: base-base image,app-Application image,custom-Custom image,url-Mirror address
	 */
	@Schema(description = "Image type: base-base image,app-Application image,custom-Custom image,url-Mirror address")
	private String imageType;
	/**
	 * Mirror tag
	 */
	@Schema(description = "Mirror tag")
	private String imageTag;
	/**
	 * Resource typeID
	 */
	@Schema(description = "Resource typeID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long resourceTypeId;
	/**
	 * Resource specificationID
	 */
	@Schema(description = "Resource specificationID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long resourceSpecId;
	/**
	 * Number of instances
	 */
	@Schema(description = "Number of instances")
	private Integer instanceCount;
	/**
	 * algorithmID
	 */
	@Schema(description = "algorithmID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long algorithmId;
	/**
	 * Instance type
	 */
	@Schema(description = "Instance type")
	private String instanceType;
	/**
	 * CPUlimit
	 */
	@Schema(description = "CPUlimit")
	private String cpuLimit;
	/**
	 * memory limit
	 */
	@Schema(description = "memory limit")
	private String memoryLimit;
	/**
	 * GPUlimit
	 */
	@Schema(description = "GPUlimit")
	private String gpuLimit;
	/**
	 * Port configuration
	 */
	@Schema(description = "Port configuration")
	private String portConfig;
	/**
	 * Environment variable configuration
	 */
	@Schema(description = "Environment variable configuration")
	private String envConfig;
	/**
	 * Storage volume configuration
	 */
	@Schema(description = "Storage volume configuration")
	private String volumeConfig;
	/**
	 * Instance status: running-Running,stopped-Stopped,error-mistake,starting-Starting,stopping-Stopping
	 */
	@Schema(description = "Instance status: running-Running,stopped-Stopped,error-mistake,starting-Starting,stopping-Stopping")
	private String instanceStatus;
	/**
	 * health status: healthy-healthy,unhealthy-unhealthy,unknown-unknown
	 */
	@Schema(description = "health status: healthy-healthy,unhealthy-unhealthy,unknown-unknown")
	private String healthStatus;
	/**
	 * Start time
	 */
	@Schema(description = "Start time")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private Date startTime;
	/**
	 * stop time
	 */
	@Schema(description = "stop time")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private Date stopTime;
	/**
	 * Number of restarts
	 */
	@Schema(description = "Number of restarts")
	private Integer restartCount;
	/**
	 * CPUUsage rate
	 */
	@Schema(description = "CPUUsage rate")
	private BigDecimal cpuUsage;
	/**
	 * memory usage
	 */
	@Schema(description = "memory usage")
	private BigDecimal memoryUsage;
	/**
	 * GPUUsage rate
	 */
	@Schema(description = "GPUUsage rate")
	private BigDecimal gpuUsage;
	/**
	 * Log path
	 */
	@Schema(description = "Log path")
	private String logsPath;

}
