package org.springblade.modules.system.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.RoleMenu;

import java.io.Serial;

/**
 * Data transfer object entity class
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenuDTO extends RoleMenu {
	@Serial
	private static final long serialVersionUID = 1L;

}
