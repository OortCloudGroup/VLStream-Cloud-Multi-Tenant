package org.springblade.vlstream.protocol.onvif.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ONVIF 设备信息查询参数")
public class OnvifFetchStreamUrisDTO {

	@Schema(description = "设备IP")
	private String ip;

	@Schema(description = "用户名")
	private String username;

	@Schema(description = "密码")
	private String password;
}
