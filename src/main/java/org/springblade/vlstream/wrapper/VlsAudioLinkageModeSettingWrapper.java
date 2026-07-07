package org.springblade.vlstream.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.vlstream.pojo.entity.AudioLinkageModeSetting;
import org.springblade.vlstream.pojo.vo.AudioLinkageModeSettingVO;

/**
 * 音频联动方式设置表 包装类
 */
public class VlsAudioLinkageModeSettingWrapper extends BaseEntityWrapper<AudioLinkageModeSetting, AudioLinkageModeSettingVO> {

	public static VlsAudioLinkageModeSettingWrapper build() {
		return new VlsAudioLinkageModeSettingWrapper();
	}

	@Override
	public AudioLinkageModeSettingVO entityVO(AudioLinkageModeSetting audioLinkageModeSetting) {
		if (audioLinkageModeSetting == null) {
			return null;
		}
		return BeanUtil.copyProperties(audioLinkageModeSetting, AudioLinkageModeSettingVO.class);
	}
}
