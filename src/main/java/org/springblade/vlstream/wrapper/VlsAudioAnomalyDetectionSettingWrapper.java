package org.springblade.vlstream.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.vlstream.pojo.entity.AudioAnomalyDetectionSetting;
import org.springblade.vlstream.pojo.vo.AudioAnomalyDetectionSettingVO;

/**
 * 音频异常侦测设置表 包装类
 */
public class VlsAudioAnomalyDetectionSettingWrapper extends BaseEntityWrapper<AudioAnomalyDetectionSetting, AudioAnomalyDetectionSettingVO> {

	public static VlsAudioAnomalyDetectionSettingWrapper build() {
		return new VlsAudioAnomalyDetectionSettingWrapper();
	}

	@Override
	public AudioAnomalyDetectionSettingVO entityVO(AudioAnomalyDetectionSetting audioAnomalyDetectionSetting) {
		if (audioAnomalyDetectionSetting == null) {
			return null;
		}
		return BeanUtil.copyProperties(audioAnomalyDetectionSetting, AudioAnomalyDetectionSettingVO.class);
	}
}
