package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.SceneGovernance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 场景治理表 数据传输对象实体类
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SceneGovernanceDTO extends SceneGovernance {
	@Serial
	private static final long serialVersionUID = 1L;

}
