package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;

/**
 * Top menu table entity class
 *
 * @author Oort
 */
@Data
@TableName("blade_top_menu")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "top menu table")
public class TopMenu extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Top menu number
	 */
	@Schema(description = "Top menu number")
	private String code;
	/**
	 * Top menu name
	 */
	@Schema(description = "Top menu name")
	private String name;
	/**
	 * Top menu resources
	 */
	@Schema(description = "Top menu resources")
	private String source;
	/**
	 * Top menu routing
	 */
	@Schema(description = "Top menu routing")
	private String path;
	/**
	 * Top menu sorting
	 */
	@Schema(description = "Top menu sorting")
	private Integer sort;


}
