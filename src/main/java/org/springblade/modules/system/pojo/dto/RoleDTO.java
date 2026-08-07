package org.springblade.modules.system.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.Role;

import java.io.Serial;

/**
 * 数据传输对象实体类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends Role {
	@Serial
	private static final long serialVersionUID = 1L;

}
