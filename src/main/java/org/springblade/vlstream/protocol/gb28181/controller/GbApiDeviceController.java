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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/device")
@Tag(name = "协议-国标设备API", description = "国标设备API")
public class GbApiDeviceController {

	private final IVlsDeviceInfoService deviceInfoService;

	@Operation(summary = "设备列表")
	@GetMapping("/list")
	public Map<String, Object> list(@RequestParam(required = false) Integer start,
									 @RequestParam(required = false) Integer limit,
									 @RequestParam(required = false) String q,
									 @RequestParam(required = false) Boolean online) {
		List<DeviceInfo> devices = deviceInfoService.list();
		List<Map<String, Object>> deviceList = new ArrayList<>();
		for (DeviceInfo device : devices) {
			Map<String, Object> item = new HashMap<>();
			item.put("ID", device.getDeviceId());
			item.put("Name", device.getDeviceName());
			item.put("Type", "GB");
			item.put("ChannelCount", 1);
			item.put("RecvStreamIP", "");
			item.put("CatalogInterval", 3600);
			item.put("SubscribeInterval", 3600);
			item.put("Online", online == null ? true : online);
			item.put("Password", "");
			item.put("MediaTransport", "UDP");
			item.put("RemoteIP", "");
			item.put("RemotePort", "");
			item.put("LastRegisterAt", "");
			item.put("LastKeepaliveAt", "");
			item.put("UpdatedAt", "");
			item.put("CreatedAt", "");
			deviceList.add(item);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("DeviceCount", deviceList.size());
		result.put("DeviceList", deviceList);
		return result;
	}

	@Operation(summary = "通道列表")
	@GetMapping("/channellist")
	public Map<String, Object> channellist(@RequestParam String serial,
									 @RequestParam(required = false) String channel_type,
									 @RequestParam(required = false) String code,
									 @RequestParam(required = false) String dir_serial,
									 @RequestParam(required = false) Integer start,
									 @RequestParam(required = false) Integer limit,
									 @RequestParam(required = false) String q,
									 @RequestParam(required = false) Boolean online) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> channelList = new ArrayList<>();
		DeviceInfo device = deviceInfoService.getByDeviceId(serial);
		if (device != null) {
			Map<String, Object> channel = new HashMap<>();
			channel.put("ID", device.getDeviceId());
			channel.put("DeviceID", device.getDeviceId());
			channel.put("DeviceName", device.getDeviceName());
			channel.put("DeviceOnline", online == null ? true : online);
			channel.put("Channel", 1);
			channel.put("Name", device.getDeviceName());
			channel.put("Custom", false);
			channel.put("CustomName", "");
			channel.put("SubCount", 0);
			channel.put("SnapURL", "");
			channel.put("Manufacturer", "");
			channel.put("Model", "");
			channel.put("Owner", "");
			channel.put("CivilCode", "");
			channel.put("Address", "");
			channel.put("Parental", 0);
			channel.put("ParentID", "");
			channel.put("Secrecy", "");
			channel.put("RegisterWay", 1);
			channel.put("Status", "ON");
			channel.put("Longitude", device.getLongitude());
			channel.put("Latitude", device.getLatitude());
			channel.put("PTZType", "0");
			channel.put("CustomPTZType", "");
			channel.put("StreamID", "");
			channel.put("NumOutputs", -1);
			channelList.add(channel);
		}
		result.put("ChannelCount", channelList.size());
		result.put("ChannelList", channelList);
		return result;
	}
}
