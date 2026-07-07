package org.springblade.modules.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.RoleMenu;

import java.io.Serial;

/**
 * 视图实体类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "RoleMenuVO对象")
public class RoleMenuVO extends RoleMenu {
	@Serial
	private static final long serialVersionUID = 1L;

}
