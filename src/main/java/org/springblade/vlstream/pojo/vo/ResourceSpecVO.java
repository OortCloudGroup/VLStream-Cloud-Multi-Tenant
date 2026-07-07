package org.springblade.vlstream.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.ResourceSpec;

import java.io.Serial;

/**
 * 资源规格配置表 视图实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceSpecVO extends ResourceSpec {
	@Serial
	private static final long serialVersionUID = 1L;
}
