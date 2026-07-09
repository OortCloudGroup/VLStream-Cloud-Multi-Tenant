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
 * Audio linkage mode setting table Entity class
 */
@Data
@TableName("vls_audio_linkage_mode_setting")
@Schema(description = "AudioLinkageModeSettingobject")
@EqualsAndHashCode(callSuper = true)
public class AudioLinkageModeSetting extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Device primary keyID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long deviceId;

	@Schema(description = "Regular linkage:0no1yes")
	private Integer conventionalLinkageEnabled;

	@Schema(description = "Email linkage:0no1yes")
	private Integer emailLinkageEnabled;

	@Schema(description = "Upload center:0no1yes")
	private Integer uploadCenterLinkageEnabled;

	@Schema(description = "Linked alarm output:0no1yes")
	private Integer alarmOutputLinkageEnabled;

	@Schema(description = "Alarm output channel")
	private String alarmOutputChannel;

	@Schema(description = "Video linkage:0no1yes")
	private Integer recordLinkageEnabled;

	@Schema(description = "Video channel")
	private String recordChannel;

	@Schema(description = "Remark")
	private String remark;
}
