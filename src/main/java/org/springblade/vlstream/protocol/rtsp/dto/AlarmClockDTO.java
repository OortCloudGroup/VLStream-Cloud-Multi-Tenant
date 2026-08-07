package org.springblade.vlstream.protocol.rtsp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "RTSP 历史播放参数")
public class AlarmClockDTO {
	@Schema(description = "设备ID")
	private Long id;

	@Schema(description = "开始时间")
	private Date startTime;

	@Schema(description = "结束时间")
	private Date endTime;
}
