package org.springblade.vlstream.service.impl;

import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.vlstream.mapper.VlsAudioAnomalyDetectionSettingMapper;
import org.springblade.vlstream.pojo.entity.AudioAnomalyDetectionSetting;
import org.springblade.vlstream.service.IVlsAudioAnomalyDetectionSettingService;
import org.springframework.stereotype.Service;

/**
 * 音频异常侦测设置表 服务实现类
 */
@Service
public class VlsAudioAnomalyDetectionSettingServiceImpl extends BaseServiceImpl<VlsAudioAnomalyDetectionSettingMapper, AudioAnomalyDetectionSetting> implements IVlsAudioAnomalyDetectionSettingService {
}
