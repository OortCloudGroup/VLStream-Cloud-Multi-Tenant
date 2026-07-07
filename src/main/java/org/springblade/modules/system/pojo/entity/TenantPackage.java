package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * 租户产品表实体类
 *
 * @author Oort
 */
@Data
@TableName("blade_tenant_package")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "租户产品表")
public class TenantPackage extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 产品包名称
	 */
	@Schema(description = "产品包名称")
	private String packageName;
	/**
	 * 菜单ID
	 */
	@Schema(description = "菜单ID")
	private String menuId;
	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;


}
