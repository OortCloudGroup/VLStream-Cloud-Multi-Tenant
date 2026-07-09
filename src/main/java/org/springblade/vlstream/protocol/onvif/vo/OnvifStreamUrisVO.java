package org.springblade.vlstream.protocol.onvif.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Schema(description = "ONVIF Device information and stream address")
public class OnvifStreamUrisVO {

	@Schema(description = "Equipment manufacturer")
	private String firm;

	@Schema(description = "Device model")
	private String model;

	@Schema(description = "Firmware version")
	private String firmwareVersion;

	@Schema(description = "stream address list")
	private Map<String, String> streamUris = new LinkedHashMap<>();

	public void addStreamUri(String token, String uri) {
		if (token == null || token.isBlank() || uri == null || uri.isBlank()) {
			return;
		}
		streamUris.put(token, uri);
	}
}
