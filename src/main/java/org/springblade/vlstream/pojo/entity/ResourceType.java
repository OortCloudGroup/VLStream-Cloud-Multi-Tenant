package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;

/**
 * Resource type configuration table Entity class
 *
 * @author Oort
 */
@Data
@TableName("vls_resource_type")
@Schema(description = "ResourceTypeobject")
@EqualsAndHashCode(callSuper = true)
public class ResourceType extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Resource type encoding")
	private String typeCode;

	@Schema(description = "Resource type name")
	private String typeName;

	@Schema(description = "Whether to enable: 1-enable, 0-Disable")
	private Integer isActive;

	@Schema(description = "sort order")
	private Integer sortOrder;

	@Schema(description = "describe")
	private String description;
}
