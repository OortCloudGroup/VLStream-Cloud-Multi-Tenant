package org.springblade.modules.system.rule.tenant;

import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.modules.system.pojo.entity.Dept;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.rule.context.TenantContext;

import static org.springblade.modules.system.rule.constant.TenantRuleConstant.TENANT_DEPT_RULE;

/**
 * 租户机构构建
 *
 * @author Chill
 */
@LiteRuleComponent(id = TENANT_DEPT_RULE, name = "租户机构构建")
public class TenantDeptRule extends RuleComponent {
	@Override
	public void process() {
		// 获取上下文
		TenantContext contextBean = this.getContextBean(TenantContext.class);
		Tenant tenant = contextBean.getTenant();

		// 新建租户对应的默认部门
		Dept dept = new Dept();
		dept.setTenantId(tenant.getTenantId());
		dept.setParentId(BladeConstant.TOP_PARENT_ID);
		dept.setAncestors(String.valueOf(BladeConstant.TOP_PARENT_ID));
		dept.setDeptName(tenant.getTenantName());
		dept.setFullName(tenant.getTenantName());
		dept.setDeptCategory(1);
		dept.setSort(2);
		dept.setIsDeleted(BladeConstant.DB_NOT_DELETED);

		// 设置上下文
		contextBean.setDept(dept);

	}
}
