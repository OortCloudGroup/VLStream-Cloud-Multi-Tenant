package org.springblade.modules.system.rule.tenant;

import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.modules.system.pojo.entity.Dept;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.rule.context.TenantContext;

import static org.springblade.modules.system.rule.constant.TenantRuleConstant.TENANT_DEPT_RULE;

/**
 * Tenant organization construction
 *
 * @author Chill
 */
@LiteRuleComponent(id = TENANT_DEPT_RULE, name = "Tenant organization construction")
public class TenantDeptRule extends RuleComponent {
	@Override
	public void process() {
		// Get context
		TenantContext contextBean = this.getContextBean(TenantContext.class);
		Tenant tenant = contextBean.getTenant();

		// The default department corresponding to the new tenant
		Dept dept = new Dept();
		dept.setTenantId(tenant.getTenantId());
		dept.setParentId(BladeConstant.TOP_PARENT_ID);
		dept.setAncestors(String.valueOf(BladeConstant.TOP_PARENT_ID));
		dept.setDeptName(tenant.getTenantName());
		dept.setFullName(tenant.getTenantName());
		dept.setDeptCategory(1);
		dept.setSort(2);
		dept.setIsDeleted(BladeConstant.DB_NOT_DELETED);

		// Set context
		contextBean.setDept(dept);

	}
}
