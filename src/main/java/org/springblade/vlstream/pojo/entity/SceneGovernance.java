package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;
import org.springblade.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.util.Date;

/**
 * Scenario management table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_scene_governance")
@Schema(description = "VlsSceneGovernanceEntityobject")
@EqualsAndHashCode(callSuper = true)
public class SceneGovernance extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "name")
	private String name;

	@Schema(description = "describe")
	private String description;

	@Schema(description = "execution type")
	private String cronExpression;

	@Schema(description = "area")
	private String location;

	@Schema(description = "Camera")
	private String cameras;

}
