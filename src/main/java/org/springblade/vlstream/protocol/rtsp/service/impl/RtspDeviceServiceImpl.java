package org.springblade.vlstream.protocol.rtsp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.vlstream.protocol.rtsp.constant.RtspFirmConstants;
import org.springblade.vlstream.protocol.rtsp.dto.AlarmClockDTO;
import org.springblade.vlstream.protocol.rtsp.entity.RtspDeviceEntity;
import org.springblade.vlstream.protocol.rtsp.mapper.RtspDeviceMapper;
import org.springblade.vlstream.protocol.rtsp.service.IRtspDeviceService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class RtspDeviceServiceImpl extends ServiceImpl<RtspDeviceMapper, RtspDeviceEntity>
	implements IRtspDeviceService {

	@Override
	public String buildPlaybackUrl(AlarmClockDTO alarmClockDTO) {
		if (alarmClockDTO == null || alarmClockDTO.getId() == null) {
			throw new IllegalArgumentException("设备ID不能为空");
		}
		RtspDeviceEntity device = getById(alarmClockDTO.getId());
		if (device == null) {
			throw new IllegalArgumentException("设备不存在");
		}
		String firm = device.getFirm();
		if (RtspFirmConstants.HIKVISION.equalsIgnoreCase(firm)) {
			String startTime = formatAlarmTimeHikvision(alarmClockDTO.getStartTime());
			String endTime = formatAlarmTimeHikvision(alarmClockDTO.getEndTime());
			return "rtsp://" + device.getUserName() + ":" + device.getPassword() + "@" + device.getIp()
				+ ":554/Streaming/tracks/" + device.getChannel()
				+ "?starttime=" + startTime + "&endtime=" + endTime;
		}
		if (RtspFirmConstants.DAHUA.equalsIgnoreCase(firm)) {
			String startTime = formatAlarmTimeDaHua(alarmClockDTO.getStartTime());
			String endTime = formatAlarmTimeDaHua(alarmClockDTO.getEndTime());
			return "rtsp://" + device.getUserName() + ":" + device.getPassword() + "@" + device.getIp()
				+ ":554/cam/playback?channel=" + device.getChannel()
				+ "&subtype=1&starttime=" + startTime + "&endtime=" + endTime;
		}
		throw new IllegalArgumentException("暂不支持该厂商");
	}

	@Override
	public boolean saveWithUrl(RtspDeviceEntity entity) {
		if (entity != null) {
			entity.setUrl(generateRtspUrl(entity));
		}
		return save(entity);
	}

	@Override
	public boolean updateWithUrl(RtspDeviceEntity entity) {
		if (entity != null) {
			entity.setUrl(generateRtspUrl(entity));
		}
		return updateById(entity);
	}

	private String generateRtspUrl(RtspDeviceEntity entity) {
		if (entity == null) {
			return null;
		}
		if (entity.getIp() == null || entity.getUserName() == null || entity.getPassword() == null || entity.getChannel() == null) {
			return entity.getUrl();
		}
		return "rtsp://" + entity.getUserName() + ":" + entity.getPassword() + "@" + entity.getIp()
			+ ":554/Streaming/Channels/" + entity.getChannel();
	}

	private static String formatAlarmTimeDaHua(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return formatter.format(date);
	}

	private static String formatAlarmTimeHikvision(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatter.format(date);
	}
}
