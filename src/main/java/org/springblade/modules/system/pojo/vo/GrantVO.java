package org.springblade.modules.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * GrantVO
 *
 * @author Chill
 */
@Data
public class GrantVO implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "roleIdsgather")
	private List<Long> roleIds;

	@Schema(description = "menuIdsgather")
	private List<Long> menuIds;

	@Schema(description = "topMenuIdsgather")
	private List<Long> topMenuIds;

	@Schema(description = "dataScopeIdsgather")
	private List<Long> dataScopeIds;

	@Schema(description = "apiScopeIdsgather")
	private List<Long> apiScopeIds;

}
