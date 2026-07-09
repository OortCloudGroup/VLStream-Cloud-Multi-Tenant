package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;

/**
 * Job table entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_post")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Job list")
public class Post extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * type
	 */
	@Schema(description = "type")
	private Integer category;
	/**
	 * Position number
	 */
	@Schema(description = "Position number")
	private String postCode;
	/**
	 * Job title
	 */
	@Schema(description = "Job title")
	private String postName;
	/**
	 * Position sorting
	 */
	@Schema(description = "Position sorting")
	private Integer sort;
	/**
	 * Job description
	 */
	@Schema(description = "Job description")
	private String remark;


}
