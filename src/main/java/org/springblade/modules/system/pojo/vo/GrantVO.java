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

	@Schema(description = "roleIds集合")
	private List<Long> roleIds;

	@Schema(description = "menuIds集合")
	private List<Long> menuIds;

	@Schema(description = "topMenuIds集合")
	private List<Long> topMenuIds;

	@Schema(description = "dataScopeIds集合")
	private List<Long> dataScopeIds;

	@Schema(description = "apiScopeIds集合")
	private List<Long> apiScopeIds;

}
