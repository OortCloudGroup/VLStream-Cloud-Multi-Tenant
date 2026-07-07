package org.springblade.vlstream.protocol.gb28181.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.vlstream.pojo.entity.DeviceInfo;
import org.springblade.vlstream.service.IVlsDeviceInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/stream")
@Tag(name = "协议-国标直播API", description = "国标直播API")
public class GbApiStreamController {

	private final IVlsDeviceInfoService deviceInfoService;

	@Operation(summary = "开始直播")
	@GetMapping("/start")
	public Map<String, Object> start(@RequestParam String serial,
								 @RequestParam(required = false) Integer channel,
								 @RequestParam(required = false) String code,
								 @RequestParam(required = false) String cdn,
								 @RequestParam(required = false) String audio,
								 @RequestParam(required = false) String transport,
								 @RequestParam(required = false) String checkchannelstatus,
								 @RequestParam(required = false) String transportmode,
								 @RequestParam(required = false) String timeout) {
		DeviceInfo device = deviceInfoService.getByDeviceId(serial);
		Map<String, Object> result = new HashMap<>();
		if (device == null) {
			result.put("error", "device[ " + serial + " ]未找到");
			return result;
		}
		result.put("StreamID", device.getDeviceId());
		result.put("DeviceID", serial);
		result.put("ChannelID", serial);
		result.put("ChannelName", device.getDeviceName());
		result.put("ChannelCustomName", "");
		result.put("RTSP", device.getStreamUrl());
		result.put("RTMP", "");
		result.put("FLV", "");
		result.put("HLS", "");
		result.put("WEBRTC", "");
		result.put("CDN", "");
		result.put("SnapURL", "");
		result.put("Transport", transport == null ? "UDP" : transport);
		result.put("StartAt", "");
		result.put("Duration", "");
		return result;
	}

	@Operation(summary = "停止直播")
	@GetMapping("/stop")
	@ResponseBody
	public Map<String, Object> stop(@RequestParam String serial,
								 @RequestParam(required = false) Integer channel,
								 @RequestParam(required = false) String code,
								 @RequestParam(required = false) String check_outputs) {
		return new HashMap<>();
	}

	@Operation(summary = "直播保活")
	@GetMapping("/touch")
	@ResponseBody
	public Map<String, Object> touch(@RequestParam String serial,
								  @RequestParam(required = false) Integer channel,
								  @RequestParam(required = false) String code,
								  @RequestParam(required = false) String autorestart,
								  @RequestParam(required = false) String audio,
								  @RequestParam(required = false) String cdn) {
		return new HashMap<>();
	}
}
