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
 * Tenant context
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
	 * Menu business
	 */
	private IMenuService menuService;

	/**
	 * dictionary business
	 */
	private IDictBizService dictBizService;

	/**
	 * tenantbusiness
	 */
	private ITenantService tenantService;

	/**
	 * tenantIDgenerator
	 */
	private TenantId tenantIdGenerator;

	/**
	 * tenant
	 */
	private Tenant tenant;

	/**
	 * Role
	 */
	private Role role;

	/**
	 * Character menu collection
	 */
	private List<RoleMenu> roleMenuList;

	/**
	 * mechanism
	 */
	private Dept dept;

	/**
	 * post
	 */
	private Post post;

	/**
	 * Business dictionary collection
	 */
	private List<DictBiz> dictBizList;

	/**
	 * user
	 */
	private User user;

}
