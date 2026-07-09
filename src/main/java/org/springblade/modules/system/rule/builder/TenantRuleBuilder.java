package org.springblade.modules.system.rule.builder;

import org.springblade.core.literule.annotation.RuleEngineComponent;
import org.springblade.core.literule.builder.LiteRule;
import org.springblade.core.literule.builder.RuleBuilder;
import org.springblade.core.literule.builder.chain.RuleChain;

import static org.springblade.modules.system.rule.constant.TenantRuleConstant.*;

/**
 * Tenant Orchestration Rule Chain Builder
 *
 * @author Oort
 */
@RuleEngineComponent(id = TENANT_CHAIN_ID)
public class TenantRuleBuilder implements RuleBuilder {
	@Override
	public RuleChain build() {
		// Create parallel rule chains
		RuleChain whenRule = LiteRule.WHEN(
			TENANT_ROLE_RULE, TENANT_ROLE_MENU_RULE, TENANT_DEPT_RULE, TENANT_POST_RULE, TENANT_DICT_BIZ_RULE, TENANT_USER_RULE
		).build();

		// Create a complete rule chain
		return LiteRule.THEN(TENANT_RULE)
			.THEN(whenRule)
			.build();
	}
}
