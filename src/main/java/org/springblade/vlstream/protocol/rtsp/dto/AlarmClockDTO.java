package org.springblade.vlstream.protocol.rtsp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "RTSP Historical playback parameters")
public class AlarmClockDTO {
	@Schema(description = "equipmentID")
	private Long id;

	@Schema(description = "start time")
	private Date startTime;

	@Schema(description = "end time")
	private Date endTime;
}
