package org.springblade.vlstream.protocol.onvif.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Schema(description = "ONVIF 设备信息与流地址")
public class OnvifStreamUrisVO {

	@Schema(description = "设备厂商")
	private String firm;

	@Schema(description = "设备型号")
	private String model;

	@Schema(description = "固件版本")
	private String firmwareVersion;

	@Schema(description = "流地址列表")
	private Map<String, String> streamUris = new LinkedHashMap<>();

	public void addStreamUri(String token, String uri) {
		if (token == null || token.isBlank() || uri == null || uri.isBlank()) {
			return;
		}
		streamUris.put(token, uri);
	}
}
