package org.springblade.modules.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@Schema(description = "TenantVO对象")
public class TenantVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private String tenantId;
	/**
	 * 租户名称
	 */
	@Schema(description = "租户名称")
	private String tenantName;


}
