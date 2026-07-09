package org.springblade.modules.system.rule.tenant;

import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.modules.system.pojo.entity.Post;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.rule.context.TenantContext;

import static org.springblade.modules.system.rule.constant.TenantRuleConstant.TENANT_POST_RULE;

/**
 * Tenant job creation
 *
 * @author Chill
 */
@LiteRuleComponent(id = TENANT_POST_RULE, name = "Tenant job creation")
public class TenantPostRule extends RuleComponent {
	@Override
	public void process() {
		// Get context
		TenantContext contextBean = this.getContextBean(TenantContext.class);
		Tenant tenant = contextBean.getTenant();

		// Default positions corresponding to new tenants
		Post post = new Post();
		post.setTenantId(tenant.getTenantId());
		post.setCategory(1);
		post.setPostCode("ceo");
		post.setPostName("CEO");
		post.setSort(1);

		// Set context
		contextBean.setPost(post);

	}
}
