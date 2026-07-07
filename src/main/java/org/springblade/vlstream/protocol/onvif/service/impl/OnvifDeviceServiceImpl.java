package org.springblade.vlstream.protocol.onvif.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.vlstream.protocol.onvif.entity.OnvifDeviceEntity;
import org.springblade.vlstream.protocol.onvif.mapper.OnvifDeviceMapper;
import org.springblade.vlstream.protocol.onvif.service.IOnvifDeviceService;
import org.springframework.stereotype.Service;

@Service
public class OnvifDeviceServiceImpl extends ServiceImpl<OnvifDeviceMapper, OnvifDeviceEntity>
	implements IOnvifDeviceService {

	@Override
	public OnvifDeviceEntity getOneByIp(String ip) {
		if (ip == null || ip.trim().isEmpty()) {
			return null;
		}
		return lambdaQuery().eq(OnvifDeviceEntity::getIp, ip.trim()).one();
	}
}
