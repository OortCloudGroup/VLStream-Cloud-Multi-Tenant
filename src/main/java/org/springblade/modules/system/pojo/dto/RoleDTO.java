package org.springblade.modules.system.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.Role;

import java.io.Serial;

/**
 * Data transfer object entity class
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends Role {
	@Serial
	private static final long serialVersionUID = 1L;

}
