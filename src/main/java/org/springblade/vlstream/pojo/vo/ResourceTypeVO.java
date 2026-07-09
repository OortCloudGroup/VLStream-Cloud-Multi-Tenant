package org.springblade.vlstream.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.ResourceType;

import java.io.Serial;

/**
 * Resource type configuration table View entity class
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceTypeVO extends ResourceType {
	@Serial
	private static final long serialVersionUID = 1L;
}
