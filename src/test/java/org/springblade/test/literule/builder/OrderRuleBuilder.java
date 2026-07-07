package org.springblade.test.literule.builder;

import org.springblade.core.literule.annotation.RuleEngineComponent;
import org.springblade.core.literule.builder.LiteRule;
import org.springblade.core.literule.builder.RuleBuilder;
import org.springblade.core.literule.builder.chain.RuleChain;

/**
 * 订单处理规则链构建器
 */
@RuleEngineComponent(id = "orderChain")
public class OrderRuleBuilder implements RuleBuilder {
	@Override
	public RuleChain build() {
		// 创建支付处理分支规则
		RuleChain paymentRule = LiteRule.SWITCH("paymentSwitchRule")
			.TO("alipayRule", "wechatPayRule", "bankPayRule")
			.build();

		// 创建完整规则链
		return LiteRule.THEN("orderValidateRule", "orderAmountRule")
			.THEN(paymentRule)
			.build();
	}
}
