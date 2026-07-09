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
 * Camera display setting table Entity class
 */
@Data
@TableName("vls_camera_display_setting")
@Schema(description = "CameraDisplaySettingobject")
@EqualsAndHashCode(callSuper = true)
public class CameraDisplaySetting extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Device primary keyID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long deviceId;

	@Schema(description = "scene")
	private String scene;

	@Schema(description = "brightness")
	private Integer brightness;

	@Schema(description = "Contrast")
	private Integer contrast;

	@Schema(description = "saturation")
	private Integer saturation;

	@Schema(description = "sharpness")
	private Integer sharpness;

	@Schema(description = "exposure mode")
	private String exposureMode;

	@Schema(description = "Maximum shutter limit")
	private String maxShutterLimit;

	@Schema(description = "Minimum shutter limit")
	private String minShutterLimit;

	@Schema(description = "gain limit")
	private Integer gainLimit;

	@Schema(description = "Low light electronic shutter")
	private String lowLightElectronicShutter;

	@Schema(description = "focus mode")
	private String focusMode;

	@Schema(description = "Minimum focus distance")
	private String minFocusDistance;

	@Schema(description = "Day and night transition")
	private String dayNightSwitch;

	@Schema(description = "Sensitivity")
	private Integer sensitivity;

	@Schema(description = "Prevent fill light from overexposure")
	private String antiFillLightOverExposure;

	@Schema(description = "Infrared light mode")
	private String infraredLampMode;

	@Schema(description = "Brightness limit")
	private Integer brightnessLimit;

	@Schema(description = "Backlight compensation")
	private String backlightCompensation;

	@Schema(description = "wide dynamic range")
	private String wideDynamic;

	@Schema(description = "Strong light suppression")
	private String strongLightSuppression;

	@Schema(description = "white balance")
	private String whiteBalance;

	@Schema(description = "digital noise reduction")
	private String digitalNoiseReduction;

	@Schema(description = "Noise reduction level")
	private Integer noiseReductionLevel;

	@Schema(description = "Fog mode")
	private String defogMode;

	@Schema(description = "Electronic image stabilization")
	private String electronicStabilization;

	@Schema(description = "mirror")
	private String mirrorMode;

	@Schema(description = "PAL(50HZ)")
	private String pal50hz;

	@Schema(description = "Lens initialization")
	private String lensInitialization;

	@Schema(description = "Zoom limit")
	private Integer zoomLimit;

	@Schema(description = "Remark")
	private String remark;
}
