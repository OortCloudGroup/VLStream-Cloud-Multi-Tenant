package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.util.Map;

/**
 * Audio arming time setting table Entity class
 */
@Data
@TableName(value = "vls_audio_defense_time_setting", autoResultMap = true)
@Schema(description = "AudioDefenseTimeSettingobject")
@EqualsAndHashCode(callSuper = true)
public class AudioDefenseTimeSetting extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Device primary keyID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long deviceId;

	@Schema(description = "Time policy configuration")
	@TableField(typeHandler = JacksonTypeHandler.class)
	private Map<String, Object> protectionTime;

	@Schema(description = "Remark")
	private String remark;
}
