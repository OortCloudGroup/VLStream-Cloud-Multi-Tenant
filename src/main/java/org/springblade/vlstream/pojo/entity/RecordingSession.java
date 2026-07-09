package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * Recording session persistence record
 */
@Data
@TableName("vls_recording_session")
@Schema(description = "RecordingSessionobject")
@EqualsAndHashCode(callSuper = true)
public class RecordingSession extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "time strategyID")
	private Long timeStrategyId;

	@Schema(description = "equipmentID")
	private Long deviceId;

	@Schema(description = "Device name")
	private String deviceName;

	@Schema(description = "stream address")
	private String streamUrl;

	@Schema(description = "Recording output directory")
	private String outputDirectory;

	@Schema(description = "Record outputpattern")
	private String outputPattern;

	@Schema(description = "shard seconds")
	private Integer segmentSeconds;

	@Schema(description = "session signature")
	private String sessionSignature;

	@Schema(description = "session state running/stopped")
	private String sessionStatus;

	@Schema(description = "Last sync time")
	private LocalDateTime lastSyncTime;

	@Schema(description = "Session start time")
	private LocalDateTime sessionStartTime;

	@Schema(description = "session end time")
	private LocalDateTime sessionStopTime;

	@Schema(description = "Stop reason")
	private String stopReason;
}
