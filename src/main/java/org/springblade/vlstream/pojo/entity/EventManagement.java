package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.vlstream.enums.EventLevelEnum;
import org.springblade.vlstream.enums.EventStatusEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.util.Date;

/**
 * event management table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_event_management")
@Schema(description = "VlsEventManagementEntityobject")
@EqualsAndHashCode(callSuper = true)
public class EventManagement extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * event description
	 */
	@Schema(description = "event description")
	private String eventDesc;
	/**
	 * event type
	 */
	@Schema(description = "event type")
	private String eventType;
	/**
	 * Report location
	 */
	@Schema(description = "Report location")
	private String reportLocation;
	/**
	 * Reporting equipment
	 */
	@Schema(description = "Reporting equipment")
	private String reportDevice;
	/**
	 * Report pictures
	 */
	@Schema(description = "Report pictures")
	private String reportImg;
	/**
	 * Reporting time
	 */
	@Schema(description = "Reporting time")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private Date reportTime;
	/**
	 * event level
	 */
	@Schema(description = "event level")
	private EventLevelEnum eventLevel;
	/**
	 * event status
	 */
	@Schema(description = "event status")
	private EventStatusEnum eventStatus;
	/**
	 * event data
	 */
	@Schema(description = "event data")
	private String eventData;
	/**
	 * Processing results
	 */
	@Schema(description = "Processing results")
	private String handleResult;
	/**
	 * feedback information
	 */
	@Schema(description = "feedback information")
	private String feedbackInfo;
	/**
	 * Feedback picture
	 */
	@Schema(description = "Feedback picture")
	private String feedbackImg;
	/**
	 * feedback status
	 */
	@Schema(description = "feedback status")
	private Integer feedbackStatus;

	/**
	 * Has it been reported?
	 */
	@Schema(description = "Has it been reported?")
	private Integer isReport;

}
