package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * Mobile terminal scene management sub-cycle task list Entity class
 */
@Data
@TableName("vls_mobile_scene_governance_sub_task")
@Schema(description = "MobileSceneGovernanceSubTaskobject")
@EqualsAndHashCode(callSuper = true)
public class MobileSceneGovernanceSubTask extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "main taskID")
	private Long governanceId;

	@Schema(description = "Subtask name")
	private String name;

	@Schema(description = "Execution time")
	private LocalDateTime executeTime;

	@Schema(description = "Task status(pending/done/cancel)")
	private String taskStatus;

	@Schema(description = "analysis areaIDgather(comma separated)")
	private String locationIds;

	@Schema(description = "algorithmIDgather(comma separated)")
	private String algorithmIds;

	@Schema(description = "CameraIDgather(comma separated)")
	private String cameraIds;
}
