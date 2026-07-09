package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.ContainerInstance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Container instance table Data transfer object entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContainerInstanceDTO extends ContainerInstance {
	@Serial
	private static final long serialVersionUID = 1L;

}
