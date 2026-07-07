package org.springblade.vlstream.protocol.onvif.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.vlstream.protocol.onvif.entity.OnvifDeviceEntity;

public interface IOnvifDeviceService extends IService<OnvifDeviceEntity> {
	OnvifDeviceEntity getOneByIp(String ip);
}
