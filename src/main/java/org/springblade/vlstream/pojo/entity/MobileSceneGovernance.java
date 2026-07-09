package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * Mobile terminal scene management main task list Entity class
 */
@Data
@TableName("vls_mobile_scene_governance")
@Schema(description = "MobileSceneGovernanceobject")
@EqualsAndHashCode(callSuper = true)
public class MobileSceneGovernance extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Governance name")
	private String name;

	@Schema(description = "governance model(immediate/loop)")
	private String governanceMode;

	@Schema(description = "Cycle type(everyday/everyOtherDay/weekly/monthly)")
	private String cycleType;

	@Schema(description = "Number of days between every other day mode")
	private Integer intervalDays;

	@Schema(description = "Weekly execution day(1-7,comma separated)")
	private String weeklyDays;

	@Schema(description = "Monthly execution day(1-31,comma separated)")
	private String monthlyDays;

	@Schema(description = "start time")
	private LocalDateTime startTime;

	@Schema(description = "end time")
	private LocalDateTime endTime;

	@Schema(description = "Trigger time list(HH:mm:ss,comma separated)")
	private String triggerTimes;

	@Schema(description = "analysis areaIDgather(comma separated)")
	private String locationIds;

	@Schema(description = "algorithmIDgather(comma separated)")
	private String algorithmIds;

	@Schema(description = "CameraIDgather(comma separated)")
	private String cameraIds;

	@Schema(description = "Analysis Description")
	private String description;

	@TableField(exist = false)
	@Schema(description = "Algorithm name(comma separated)")
	private String algorithmNames;

	@TableField(exist = false)
	@Schema(description = "Camera name(comma separated)")
	private String cameraNames;
}
