package org.springblade.vlstream.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springblade.vlstream.pojo.entity.SceneGovernance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 场景治理表 视图实体类
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SceneGovernanceVO extends SceneGovernance {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "算法名称")
	private String algorithmName;

	@Schema(description = "摄像头名称")
	private String camerasName;

}
