package org.springblade.vlstream.protocol.onvif.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ONVIF 预置点参数")
public class OnvifPresetsDTO {

	@Schema(description = "设备IP")
	private String ip;

	@Schema(description = "用户名")
	private String username;

	@Schema(description = "密码")
	private String password;

	@Schema(description = "ProfileToken")
	private String profileToken;

	@Schema(description = "PresetToken")
	private String presetToken;
}
