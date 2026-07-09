package org.springblade.vlstream.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springblade.vlstream.pojo.entity.SceneGovernance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Scenario management table View entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SceneGovernanceVO extends SceneGovernance {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Algorithm name")
	private String algorithmName;

	@Schema(description = "Camera name")
	private String camerasName;

}
