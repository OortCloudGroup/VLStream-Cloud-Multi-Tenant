package org.springblade.vlstream.service.impl;

import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.vlstream.mapper.VlsAudioLinkageModeSettingMapper;
import org.springblade.vlstream.pojo.entity.AudioLinkageModeSetting;
import org.springblade.vlstream.service.IVlsAudioLinkageModeSettingService;
import org.springframework.stereotype.Service;

/**
 * 音频联动方式设置表 服务实现类
 */
@Service
public class VlsAudioLinkageModeSettingServiceImpl extends BaseServiceImpl<VlsAudioLinkageModeSettingMapper, AudioLinkageModeSetting> implements IVlsAudioLinkageModeSettingService {
}
