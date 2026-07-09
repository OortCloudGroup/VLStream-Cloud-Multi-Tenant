package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;

/**
 * Audio anomaly detection setting table Entity class
 */
@Data
@TableName("vls_audio_anomaly_detection_setting")
@Schema(description = "AudioAnomalyDetectionSettingobject")
@EqualsAndHashCode(callSuper = true)
public class AudioAnomalyDetectionSetting extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Device primary keyID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long deviceId;

	@Schema(description = "Audio input abnormality:0no1yes")
	private Integer audioInputAnomalyEnabled;

	@Schema(description = "Sound intensity rises sharply:0no1yes")
	private Integer soundRiseEnabled;

	@Schema(description = "Sound intensity sudden rise sensitivity")
	private Integer soundRiseSensitivity;

	@Schema(description = "sound intensity threshold")
	private Integer soundIntensityThreshold;

	@Schema(description = "Sound intensity drops sharply:0no1yes")
	private Integer soundDropEnabled;

	@Schema(description = "Sound intensity drop sensitivity")
	private Integer soundDropSensitivity;

	@Schema(description = "Remark")
	private String remark;
}
