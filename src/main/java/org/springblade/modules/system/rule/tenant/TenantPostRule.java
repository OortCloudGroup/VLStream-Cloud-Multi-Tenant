package org.springblade.modules.system.rule.tenant;

import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.modules.system.pojo.entity.Post;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.rule.context.TenantContext;

import static org.springblade.modules.system.rule.constant.TenantRuleConstant.TENANT_POST_RULE;

/**
 * 租户岗位构建
 *
 * @author Chill
 */
@LiteRuleComponent(id = TENANT_POST_RULE, name = "租户岗位构建")
public class TenantPostRule extends RuleComponent {
	@Override
	public void process() {
		// 获取上下文
		TenantContext contextBean = this.getContextBean(TenantContext.class);
		Tenant tenant = contextBean.getTenant();

		// 新建租户对应的默认岗位
		Post post = new Post();
		post.setTenantId(tenant.getTenantId());
		post.setCategory(1);
		post.setPostCode("ceo");
		post.setPostName("首席执行官");
		post.setSort(1);

		// 设置上下文
		contextBean.setPost(post);

	}
}
