package org.springblade.vlstream.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springblade.vlstream.config.VlsMqttProperties;
import org.springblade.vlstream.pojo.entity.AudioAnomalyDetectionSetting;
import org.springblade.vlstream.pojo.vo.AudioAnomalyDetectionSettingVO;
import org.springblade.vlstream.service.IVlsAudioAnomalyDetectionSettingService;
import org.springblade.vlstream.service.VlsMqttPublishService;
import org.springblade.vlstream.wrapper.VlsAudioAnomalyDetectionSettingWrapper;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Audio anomaly detection setting table controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsAudioAnomalyDetectionSetting")
@Tag(name = "Audio anomaly detection settings", description = "Audio anomaly detection setting interface")
public class VlsAudioAnomalyDetectionSettingController extends BladeController {

	private final IVlsAudioAnomalyDetectionSettingService vlsAudioAnomalyDetectionSettingService;
	private final VlsMqttPublishService vlsMqttPublishService;
	private final VlsMqttProperties vlsMqttProperties;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "by deviceIDQuery audio anomaly detection settings")
	public R<AudioAnomalyDetectionSettingVO> detail(@RequestParam Long deviceId) {
		Assert.notNull(deviceId, "Device primary keyIDcannot be empty");
		AudioAnomalyDetectionSetting detail = vlsAudioAnomalyDetectionSettingService.getOne(Wrappers.<AudioAnomalyDetectionSetting>lambdaQuery()
			.eq(AudioAnomalyDetectionSetting::getDeviceId, deviceId)
			.last("limit 1"));
		return R.data(VlsAudioAnomalyDetectionSettingWrapper.build().entityVO(detail));
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Add or modify", description = "by deviceIDSave audio anomaly detection settings")
	public R submit(@Valid @RequestBody AudioAnomalyDetectionSetting audioAnomalyDetectionSetting) {
		Assert.notNull(audioAnomalyDetectionSetting.getDeviceId(), "Device primary keyIDcannot be empty");
		AudioAnomalyDetectionSetting existed = vlsAudioAnomalyDetectionSettingService.getOne(Wrappers.<AudioAnomalyDetectionSetting>lambdaQuery()
			.eq(AudioAnomalyDetectionSetting::getDeviceId, audioAnomalyDetectionSetting.getDeviceId())
			.last("limit 1"));
		if (existed != null) {
			audioAnomalyDetectionSetting.setId(existed.getId());
		}
		boolean success = vlsAudioAnomalyDetectionSettingService.saveOrUpdate(audioAnomalyDetectionSetting);
		if (!success) {
			return R.status(false);
		}
		boolean publishSuccess = vlsMqttPublishService.publish(vlsMqttProperties.getVlsAudioAnomalyDetectionSettingTopic(), audioAnomalyDetectionSetting);
		if (!publishSuccess) {
			return R.fail("Saved successfully, butMQTTMessage sending failed");
		}
		return R.status(true);
	}
}
