package org.springblade.modules.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Entity class
 *
 * @author Chill
 */
@Data
@Schema(description = "TenantVOobject")
public class TenantVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * tenantID
	 */
	@Schema(description = "tenantID")
	private String tenantId;
	/**
	 * Tenant name
	 */
	@Schema(description = "Tenant name")
	private String tenantName;


}
