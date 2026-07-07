package org.springblade.modules.system.rule.builder;

import org.springblade.core.literule.annotation.RuleEngineComponent;
import org.springblade.core.literule.builder.LiteRule;
import org.springblade.core.literule.builder.RuleBuilder;
import org.springblade.core.literule.builder.chain.RuleChain;

import static org.springblade.modules.system.rule.constant.TenantRuleConstant.*;

/**
 * 租户编排规则链构建器
 *
 * @author Oort
 */
@RuleEngineComponent(id = TENANT_CHAIN_ID)
public class TenantRuleBuilder implements RuleBuilder {
	@Override
	public RuleChain build() {
		// 创建并行规则链
		RuleChain whenRule = LiteRule.WHEN(
			TENANT_ROLE_RULE, TENANT_ROLE_MENU_RULE, TENANT_DEPT_RULE, TENANT_POST_RULE, TENANT_DICT_BIZ_RULE, TENANT_USER_RULE
		).build();

		// 创建完整规则链
		return LiteRule.THEN(TENANT_RULE)
			.THEN(whenRule)
			.build();
	}
}
