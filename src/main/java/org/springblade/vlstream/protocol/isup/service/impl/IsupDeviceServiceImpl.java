package org.springblade.vlstream.protocol.isup.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.vlstream.protocol.isup.entity.IsupDeviceEntity;
import org.springblade.vlstream.protocol.isup.mapper.IsupDeviceMapper;
import org.springblade.vlstream.protocol.isup.service.IIsupDeviceService;
import org.springframework.stereotype.Service;

@Service
public class IsupDeviceServiceImpl extends ServiceImpl<IsupDeviceMapper, IsupDeviceEntity>
	implements IIsupDeviceService {

	@Override
	public IsupDeviceEntity selectByLuserIdAndIp(Integer luserId, String ipAddress) {
		if (luserId == null || ipAddress == null || ipAddress.isBlank()) {
			return null;
		}
		return lambdaQuery()
			.eq(IsupDeviceEntity::getLuserId, luserId)
			.eq(IsupDeviceEntity::getIpAddress, ipAddress.trim())
			.one();
	}

	@Override
	public boolean updateStatusByLuserId(Integer luserId, String ipAddress, String status) {
		IsupDeviceEntity device = selectByLuserIdAndIp(luserId, ipAddress);
		if (device == null) {
			return false;
		}
		device.setDeviceStatus(status);
		return updateById(device);
	}

	@Override
	public boolean saveWithUrl(IsupDeviceEntity entity) {
		if (entity != null && entity.getUrl() == null) {
			entity.setUrl(buildRtspUrl(entity));
		}
		return save(entity);
	}

	@Override
	public boolean updateWithUrl(IsupDeviceEntity entity) {
		if (entity != null) {
			entity.setUrl(buildRtspUrl(entity));
		}
		return updateById(entity);
	}

	private String buildRtspUrl(IsupDeviceEntity entity) {
		if (entity == null) {
			return null;
		}
		String ipAddress = entity.getIpAddress();
		String userName = entity.getUserName();
		String password = entity.getPassword();
		String channel = entity.getChannel();
		Integer port = entity.getPort();
		if (ipAddress == null || userName == null || password == null || channel == null) {
			return entity.getUrl();
		}
		int rtspPort = port == null ? 554 : port;
		return "rtsp://" + userName + ":" + password + "@" + ipAddress + ":" + rtspPort + "/Streaming/Channels/" + channel;
	}
}
