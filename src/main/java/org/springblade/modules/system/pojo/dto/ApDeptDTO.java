package org.springblade.modules.system.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.ApDeptEntity;

import java.io.Serial;

/**
 * 组织机构表 数据传输对象实体类
 *
 * @author Oort
 * @since 2025-08-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApDeptDTO extends ApDeptEntity {
	@Serial
	private static final long serialVersionUID = 1L;

}
