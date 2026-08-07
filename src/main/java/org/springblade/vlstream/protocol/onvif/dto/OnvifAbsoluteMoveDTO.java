package org.springblade.vlstream.protocol.onvif.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ONVIF 云台移动参数")
public class OnvifAbsoluteMoveDTO {

	@Schema(description = "设备IP")
	private String ip;

	@Schema(description = "用户名")
	private String username;

	@Schema(description = "密码")
	private String password;

	@Schema(description = "ProfileToken")
	private String profileToken;

	@Schema(description = "X坐标")
	private double x;

	@Schema(description = "Y坐标")
	private double y;
}
