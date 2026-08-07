package org.springblade.vlstream.service.impl;

import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.vlstream.mapper.VlsCameraOsdSettingMapper;
import org.springblade.vlstream.pojo.entity.CameraOsdSetting;
import org.springblade.vlstream.service.IVlsCameraOsdSettingService;
import org.springframework.stereotype.Service;

/**
 * 摄像机OSD设置表 服务实现类
 */
@Service
public class VlsCameraOsdSettingServiceImpl extends BaseServiceImpl<VlsCameraOsdSettingMapper, CameraOsdSetting> implements IVlsCameraOsdSettingService {
}
