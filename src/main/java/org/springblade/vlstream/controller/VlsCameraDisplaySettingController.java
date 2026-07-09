package org.springblade.vlstream.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.vlstream.config.VlsMqttProperties;
import org.springblade.vlstream.pojo.entity.CameraDisplaySetting;
import org.springblade.vlstream.pojo.vo.CameraDisplaySettingVO;
import org.springblade.vlstream.service.IVlsCameraDisplaySettingService;
import org.springblade.vlstream.service.VlsMqttPublishService;
import org.springblade.vlstream.wrapper.VlsCameraDisplaySettingWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Camera display setting table controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsCameraDisplaySetting")
@Tag(name = "Camera display settings", description = "Camera display setting interface")
public class VlsCameraDisplaySettingController extends BladeController {

	private final IVlsCameraDisplaySettingService vlsCameraDisplaySettingService;
	private final VlsMqttPublishService vlsMqttPublishService;
	private final VlsMqttProperties vlsMqttProperties;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingcameraDisplaySetting")
	public R<CameraDisplaySettingVO> detail(CameraDisplaySetting cameraDisplaySetting) {
		CameraDisplaySetting detail = vlsCameraDisplaySettingService.getOne(Condition.getQueryWrapper(cameraDisplaySetting));
		if (detail == null) {
			detail = buildDefaultDisplaySetting(cameraDisplaySetting.getDeviceId());
		}
		return R.data(VlsCameraDisplaySettingWrapper.build().entityVO(detail));
	}

	private CameraDisplaySetting buildDefaultDisplaySetting(Long deviceId) {
		CameraDisplaySetting defaultSetting = new CameraDisplaySetting();
		defaultSetting.setDeviceId(deviceId);
		defaultSetting.setScene("indoor");
		defaultSetting.setBrightness(50);
		defaultSetting.setContrast(50);
		defaultSetting.setSaturation(50);
		defaultSetting.setSharpness(50);
		defaultSetting.setExposureMode("automatic");
		defaultSetting.setMaxShutterLimit("1/25");
		defaultSetting.setMinShutterLimit("1/3000");
		defaultSetting.setGainLimit(50);
		defaultSetting.setLowLightElectronicShutter("closure");
		defaultSetting.setFocusMode("Semi-automatic");
		defaultSetting.setMinFocusDistance("1.5m");
		defaultSetting.setDayNightSwitch("automatic");
		defaultSetting.setSensitivity(2);
		defaultSetting.setAntiFillLightOverExposure("closure");
		defaultSetting.setInfraredLampMode("automatic");
		defaultSetting.setBrightnessLimit(50);
		defaultSetting.setBacklightCompensation("closure");
		defaultSetting.setWideDynamic("closure");
		defaultSetting.setStrongLightSuppression("closure");
		defaultSetting.setWhiteBalance("Auto white balance");
		defaultSetting.setDigitalNoiseReduction("Normal mode");
		defaultSetting.setNoiseReductionLevel(50);
		defaultSetting.setDefogMode("closure");
		defaultSetting.setElectronicStabilization("closure");
		defaultSetting.setMirrorMode("closure");
		defaultSetting.setPal50hz("closure");
		defaultSetting.setLensInitialization("closure");
		defaultSetting.setZoomLimit(2);
		return defaultSetting;
	}

	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingcameraDisplaySetting")
	public R<IPage<CameraDisplaySettingVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> cameraDisplaySetting, Query query) {
		IPage<CameraDisplaySetting> pages = vlsCameraDisplaySettingService.page(Condition.getPage(query), Condition.getQueryWrapper(cameraDisplaySetting, CameraDisplaySetting.class));
		return R.data(VlsCameraDisplaySettingWrapper.build().pageVO(pages));
	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "New", description = "incomingcameraDisplaySetting")
	public R save(@Valid @RequestBody CameraDisplaySetting cameraDisplaySetting) {
		boolean success = vlsCameraDisplaySettingService.save(cameraDisplaySetting);
		if (!success) {
			return R.status(false);
		}
		boolean publishSuccess = vlsMqttPublishService.publish(vlsMqttProperties.getVlsCameraDisplaySettingTopic(), cameraDisplaySetting);
		if (!publishSuccess) {
			return R.fail("Saved successfully, butMQTTMessage sending failed");
		}
		return R.status(true);
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Revise", description = "incomingcameraDisplaySetting")
	public R update(@Valid @RequestBody CameraDisplaySetting cameraDisplaySetting) {
		return R.status(vlsCameraDisplaySettingService.updateById(cameraDisplaySetting));
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Add or modify", description = "incomingcameraDisplaySetting")
	public R submit(@Valid @RequestBody CameraDisplaySetting cameraDisplaySetting) {
		boolean success = vlsCameraDisplaySettingService.saveOrUpdate(cameraDisplaySetting);
		if (!success) {
			return R.status(false);
		}
		boolean publishSuccess = vlsMqttPublishService.publish(vlsMqttProperties.getVlsCameraDisplaySettingTopic(), cameraDisplaySetting);
		if (!publishSuccess) {
			return R.fail("Saved successfully, butMQTTMessage sending failed");
		}
		return R.status(true);
	}

	@GetMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsCameraDisplaySettingService.deleteLogic(Func.toLongList(ids)));
	}

	@GetMapping("/restoreDefault")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "Restore defaults", description = "incomingdeviceId")
	public R<Boolean> restoreDefault(@RequestParam Long deviceId) {
		if (deviceId == null) {
			return R.fail("Device primary keyIDcannot be empty");
		}
		CameraDisplaySetting defaultSetting = buildDefaultDisplaySetting(deviceId);
		CameraDisplaySetting existedSetting = vlsCameraDisplaySettingService.getOne(Wrappers.<CameraDisplaySetting>lambdaQuery()
			.eq(CameraDisplaySetting::getDeviceId, deviceId));
		if (existedSetting != null) {
			defaultSetting.setId(existedSetting.getId());
		}
		return R.status(vlsCameraDisplaySettingService.saveOrUpdate(defaultSetting));
	}
}
