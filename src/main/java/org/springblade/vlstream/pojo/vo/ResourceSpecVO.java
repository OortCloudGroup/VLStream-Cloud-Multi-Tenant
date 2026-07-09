package org.springblade.vlstream.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.ResourceSpec;

import java.io.Serial;

/**
 * Resource specification configuration table View entity class
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceSpecVO extends ResourceSpec {
	@Serial
	private static final long serialVersionUID = 1L;
}
