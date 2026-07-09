package org.springblade.vlstream.protocol.onvif.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ONVIF PTZ movement parameters")
public class OnvifAbsoluteMoveDTO {

	@Schema(description = "equipmentIP")
	private String ip;

	@Schema(description = "username")
	private String username;

	@Schema(description = "password")
	private String password;

	@Schema(description = "ProfileToken")
	private String profileToken;

	@Schema(description = "Xcoordinate")
	private double x;

	@Schema(description = "Ycoordinate")
	private double y;
}
