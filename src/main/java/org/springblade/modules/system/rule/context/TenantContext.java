package org.springblade.modules.system.rule.context;

import lombok.*;
import org.springblade.core.literule.core.RuleContextComponent;
import org.springblade.core.tenant.TenantId;
import org.springblade.modules.system.pojo.entity.*;
import org.springblade.modules.system.service.IDictBizService;
import org.springblade.modules.system.service.IMenuService;
import org.springblade.modules.system.service.ITenantService;

import java.util.List;

/**
 * 租户上下文
 *
 * @author Chill
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TenantContext extends RuleContextComponent {

	/**
	 * 菜单业务
	 */
	private IMenuService menuService;

	/**
	 * 字典业务
	 */
	private IDictBizService dictBizService;

	/**
	 * 租户业务
	 */
	private ITenantService tenantService;

	/**
	 * 租户ID生成器
	 */
	private TenantId tenantIdGenerator;

	/**
	 * 租户
	 */
	private Tenant tenant;

	/**
	 * 角色
	 */
	private Role role;

	/**
	 * 角色菜单合集
	 */
	private List<RoleMenu> roleMenuList;

	/**
	 * 机构
	 */
	private Dept dept;

	/**
	 * 岗位
	 */
	private Post post;

	/**
	 * 业务字典合集
	 */
	private List<DictBiz> dictBizList;

	/**
	 * 用户
	 */
	private User user;

}
