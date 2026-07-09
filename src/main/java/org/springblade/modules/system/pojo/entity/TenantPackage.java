package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * Tenant product table entity class
 *
 * @author Oort
 */
@Data
@TableName("blade_tenant_package")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Tenant product table")
public class TenantPackage extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Product package name
	 */
	@Schema(description = "Product package name")
	private String packageName;
	/**
	 * menuID
	 */
	@Schema(description = "menuID")
	private String menuId;
	/**
	 * Remark
	 */
	@Schema(description = "Remark")
	private String remark;


}
