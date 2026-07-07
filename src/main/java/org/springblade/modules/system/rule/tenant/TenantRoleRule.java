package org.springblade.modules.system.rule.tenant;

import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.modules.system.pojo.entity.Role;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.rule.context.TenantContext;

import static org.springblade.modules.system.rule.constant.TenantRuleConstant.TENANT_ROLE_RULE;

/**
 * 租户角色构建
 *
 * @author Chill
 */
@LiteRuleComponent(id = TENANT_ROLE_RULE, name = "租户角色构建")
public class TenantRoleRule extends RuleComponent {
	@Override
	public void process() {
		// 获取上下文
		TenantContext contextBean = this.getContextBean(TenantContext.class);
		Tenant tenant = contextBean.getTenant();
		// 新建租户对应的默认角色
		Role role = new Role();
		role.setTenantId(tenant.getTenantId());
		role.setParentId(BladeConstant.TOP_PARENT_ID);
		role.setRoleName("管理员");
		role.setRoleAlias("admin");
		role.setSort(2);
		role.setIsDeleted(BladeConstant.DB_NOT_DELETED);
		// 设置上下文
		contextBean.setRole(role);
	}
}
