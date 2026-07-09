package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.vlstream.enums.AnalysisRequestStatusEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.util.Date;

/**
 * Intelligent analysis request form Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_analysis_request")
@Schema(description = "VlsAnalysisRequestEntityobject")
@EqualsAndHashCode(callSuper = true)
public class AnalysisRequest extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Analysis name
	 */
	@Schema(description = "Analysis name")
	private String analysisName;
	/**
	 * Analysis type
	 */
	@Schema(description = "Analysis type")
	private String analysisType;
	/**
	 * equipmentIDlist, comma separated
	 */
	@Schema(description = "equipmentIDlist, comma separated")
	private String deviceIds;
	/**
	 * analysis area
	 */
	@Schema(description = "analysis area, comma separated")
	private String regionInfo;
	/**
	 * time range
	 */
	@Schema(description = "time range")
	private String timeRange;
	/**
	 * Analyze pictures
	 */
	@Schema(description = "Analyze pictures")
	private String images;
	/**
	 * Request status
	 */
	@Schema(description = "Request status")
	private AnalysisRequestStatusEnum requestStatus;
	/**
	 * Processing progress percentage
	 */
	@Schema(description = "Processing progress percentage")
	private Integer progress;
	/**
	 * Result file path
	 */
	@Schema(description = "Result file path")
	private String resultPath;
	/**
	 * Start processing time
	 */
	@Schema(description = "Start processing time")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private Date startTime;
	/**
	 * completion time
	 */
	@Schema(description = "completion time")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private Date completeTime;
	/**
	 * error message
	 */
	@Schema(description = "error message")
	private String errorMessage;
	/**
	 * Description information
	 */
	@Schema(description = "Description information")
	private String description;

}
