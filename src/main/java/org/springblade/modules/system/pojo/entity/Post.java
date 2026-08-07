package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;

/**
 * 岗位表实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_post")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "岗位表")
public class Post extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 类型
	 */
	@Schema(description = "类型")
	private Integer category;
	/**
	 * 岗位编号
	 */
	@Schema(description = "岗位编号")
	private String postCode;
	/**
	 * 岗位名称
	 */
	@Schema(description = "岗位名称")
	private String postName;
	/**
	 * 岗位排序
	 */
	@Schema(description = "岗位排序")
	private Integer sort;
	/**
	 * 岗位描述
	 */
	@Schema(description = "岗位描述")
	private String remark;


}
