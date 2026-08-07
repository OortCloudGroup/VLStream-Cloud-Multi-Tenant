package org.springblade.vlstream.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springblade.vlstream.config.VlsMqttProperties;
import org.springblade.vlstream.pojo.entity.AudioLinkageModeSetting;
import org.springblade.vlstream.pojo.vo.AudioLinkageModeSettingVO;
import org.springblade.vlstream.service.IVlsAudioLinkageModeSettingService;
import org.springblade.vlstream.service.VlsMqttPublishService;
import org.springblade.vlstream.wrapper.VlsAudioLinkageModeSettingWrapper;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 音频联动方式设置表 控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsAudioLinkageModeSetting")
@Tag(name = "音频联动方式设置", description = "音频联动方式设置接口")
public class VlsAudioLinkageModeSettingController extends BladeController {

	private final IVlsAudioLinkageModeSettingService vlsAudioLinkageModeSettingService;
	private final VlsMqttPublishService vlsMqttPublishService;
	private final VlsMqttProperties vlsMqttProperties;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "按设备ID查询音频联动方式设置")
	public R<AudioLinkageModeSettingVO> detail(@RequestParam Long deviceId) {
		Assert.notNull(deviceId, "设备主键ID不能为空");
		AudioLinkageModeSetting detail = vlsAudioLinkageModeSettingService.getOne(Wrappers.<AudioLinkageModeSetting>lambdaQuery()
			.eq(AudioLinkageModeSetting::getDeviceId, deviceId)
			.last("limit 1"));
		return R.data(VlsAudioLinkageModeSettingWrapper.build().entityVO(detail));
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "新增或修改", description = "按设备ID保存音频联动方式设置")
	public R submit(@Valid @RequestBody AudioLinkageModeSetting audioLinkageModeSetting) {
		Assert.notNull(audioLinkageModeSetting.getDeviceId(), "设备主键ID不能为空");
		if (Integer.valueOf(1).equals(audioLinkageModeSetting.getAlarmOutputLinkageEnabled())) {
			Assert.isTrue(StringUtils.isNotBlank(audioLinkageModeSetting.getAlarmOutputChannel()), "联动报警输出开启时，报警输出通道不能为空");
		}
		if (Integer.valueOf(1).equals(audioLinkageModeSetting.getRecordLinkageEnabled())) {
			Assert.isTrue(StringUtils.isNotBlank(audioLinkageModeSetting.getRecordChannel()), "录像联动开启时，录像通道不能为空");
		}
		AudioLinkageModeSetting existed = vlsAudioLinkageModeSettingService.getOne(Wrappers.<AudioLinkageModeSetting>lambdaQuery()
			.eq(AudioLinkageModeSetting::getDeviceId, audioLinkageModeSetting.getDeviceId())
			.last("limit 1"));
		if (existed != null) {
			audioLinkageModeSetting.setId(existed.getId());
		}
		boolean success = vlsAudioLinkageModeSettingService.saveOrUpdate(audioLinkageModeSetting);
		if (!success) {
			return R.status(false);
		}
		boolean publishSuccess = vlsMqttPublishService.publish(vlsMqttProperties.getVlsAudioLinkageModeSettingTopic(), audioLinkageModeSetting);
		if (!publishSuccess) {
			return R.fail("保存成功，但MQTT消息发送失败");
		}
		return R.status(true);
	}
}
