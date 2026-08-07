package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;

/**
 * 顶部菜单表实体类
 *
 * @author Oort
 */
@Data
@TableName("blade_top_menu")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "顶部菜单表")
public class TopMenu extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 顶部菜单编号
	 */
	@Schema(description = "顶部菜单编号")
	private String code;
	/**
	 * 顶部菜单名
	 */
	@Schema(description = "顶部菜单名")
	private String name;
	/**
	 * 顶部菜单资源
	 */
	@Schema(description = "顶部菜单资源")
	private String source;
	/**
	 * 顶部菜单路由
	 */
	@Schema(description = "顶部菜单路由")
	private String path;
	/**
	 * 顶部菜单排序
	 */
	@Schema(description = "顶部菜单排序")
	private Integer sort;


}
