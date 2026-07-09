package org.springblade.modules.system.rule.tenant;

import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.modules.system.pojo.entity.Role;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.rule.context.TenantContext;

import static org.springblade.modules.system.rule.constant.TenantRuleConstant.TENANT_ROLE_RULE;

/**
 * Tenant role building
 *
 * @author Chill
 */
@LiteRuleComponent(id = TENANT_ROLE_RULE, name = "Tenant role building")
public class TenantRoleRule extends RuleComponent {
	@Override
	public void process() {
		// Get context
		TenantContext contextBean = this.getContextBean(TenantContext.class);
		Tenant tenant = contextBean.getTenant();
		// Default roles corresponding to new tenants
		Role role = new Role();
		role.setTenantId(tenant.getTenantId());
		role.setParentId(BladeConstant.TOP_PARENT_ID);
		role.setRoleName("administrator");
		role.setRoleAlias("admin");
		role.setSort(2);
		role.setIsDeleted(BladeConstant.DB_NOT_DELETED);
		// Set context
		contextBean.setRole(role);
	}
}
