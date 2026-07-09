package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.util.Map;

/**
 * Camera incident policy(Trigger recording/Snapshot) Entity class
 *
 * @author Oort
 * @since 2026-02-04
 */
@Data
@TableName(value = "vls_record_event_strategy", autoResultMap = true)
@Schema(description = "RecordEventStrategyobject")
@EqualsAndHashCode(callSuper = true)
public class RecordEventStrategy extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Device number(DeviceInfo.deviceId)")
	private String deviceId;

	@Schema(description = "Enable motion detection")
	private Boolean motionDetectionEnabled;

	@Schema(description = "enablePTZMotion alarm reporting")
	private Boolean ptzAlarmReportEnabled;

	@Schema(description = "Enable dynamic analysis")
	private Boolean dynamicAnalysisEnabled;

	@Schema(description = "Enable occlusion alarm")
	private Boolean occlusionAlarmEnabled;

	@Schema(description = "trigger action(record/snapshot)")
	private String triggerAction;

	@Schema(description = "Number of seconds to record before triggering the alarm")
	private Integer preRecordSeconds;

	@Schema(description = "Recording seconds after alarm is triggered")
	private Integer postRecordSeconds;

	@Schema(description = "Alarm frequency(minute/Second-rate)")
	private Integer alarmFrequencyMinutes;

	@Schema(description = "Alarm level(tip/general/important/urgent)")
	private String alarmLevel;

	@Schema(description = "Alarm mode(site/message/sms/email/phone,comma separated)")
	private String alarmMethod;

	@Schema(description = "recipientIDgather(comma separated)")
	private String receiverIds;

	@Schema(description = "Policy configuration")
	@TableField(typeHandler = JacksonTypeHandler.class)
	private Map<String, Object> protectionTime;

}
