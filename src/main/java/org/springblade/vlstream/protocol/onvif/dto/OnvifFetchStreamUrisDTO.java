package org.springblade.vlstream.protocol.onvif.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ONVIF Device information query parameters")
public class OnvifFetchStreamUrisDTO {

	@Schema(description = "equipmentIP")
	private String ip;

	@Schema(description = "username")
	private String username;

	@Schema(description = "password")
	private String password;
}
