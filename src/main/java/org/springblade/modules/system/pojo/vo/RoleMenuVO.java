package org.springblade.modules.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.RoleMenu;

import java.io.Serial;

/**
 * View entity class
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "RoleMenuVOobject")
public class RoleMenuVO extends RoleMenu {
	@Serial
	private static final long serialVersionUID = 1L;

}
