package org.springblade.modules.system.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.ApDeptEntity;

import java.io.Serial;

/**
 * 组织机构表 视图实体类
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
