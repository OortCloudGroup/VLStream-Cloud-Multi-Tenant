package org.springblade.modules.system.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.ApDeptEntity;

import java.io.Serial;

/**
 * Organization chart View entity class
 *
 * @author BladeX
 * @since 2025-08-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApDeptVO extends ApDeptEntity {
	@Serial
	private static final long serialVersionUID = 1L;

}
