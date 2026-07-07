package org.springblade.vlstream.protocol.rtsp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.vlstream.protocol.rtsp.dto.AlarmClockDTO;
import org.springblade.vlstream.protocol.rtsp.entity.RtspDeviceEntity;

public interface IRtspDeviceService extends IService<RtspDeviceEntity> {

	String buildPlaybackUrl(AlarmClockDTO alarmClockDTO);

	boolean saveWithUrl(RtspDeviceEntity entity);

	boolean updateWithUrl(RtspDeviceEntity entity);
}
