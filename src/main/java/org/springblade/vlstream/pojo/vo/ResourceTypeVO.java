package org.springblade.vlstream.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.ResourceType;

import java.io.Serial;

/**
 * 资源类型配置表 视图实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceTypeVO extends ResourceType {
	@Serial
	private static final long serialVersionUID = 1L;
}
