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
 * cameraOSDSetting table Entity class
 */
@Data
@TableName("vls_camera_osd_setting")
@Schema(description = "CameraOsdSettingobject")
@EqualsAndHashCode(callSuper = true)
public class CameraOsdSetting extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Device primary keyID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long deviceId;

	@Schema(description = "display name:0no1yes")
	private Integer showName;

	@Schema(description = "Show date:0no1yes")
	private Integer showDate;

	@Schema(description = "Show day of the week:0no1yes")
	private Integer showWeek;

	@Schema(description = "Channel name")
	private String channelName;

	@Schema(description = "time format")
	private String timeFormat;

	@Schema(description = "date format")
	private String dateFormat;

	@Schema(description = "character overlay1enable:0no1yes")
	private Integer overlay1Enabled;

	@Schema(description = "character overlay1content")
	private String overlay1Text;

	@Schema(description = "character overlay2enable:0no1yes")
	private Integer overlay2Enabled;

	@Schema(description = "character overlay2content")
	private String overlay2Text;

	@Schema(description = "character overlay3enable:0no1yes")
	private Integer overlay3Enabled;

	@Schema(description = "character overlay3content")
	private String overlay3Text;

	@Schema(description = "OSDproperty")
	private String osdProperty;

	@Schema(description = "OSDfont")
	private String osdFont;

	@Schema(description = "OSDcolor")
	private String osdColor;

	@Schema(description = "Alignment")
	private String alignMode;

	@Schema(description = "Remark")
	private String remark;
}
