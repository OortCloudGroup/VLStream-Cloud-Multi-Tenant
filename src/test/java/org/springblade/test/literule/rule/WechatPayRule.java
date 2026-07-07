package org.springblade.test.literule.rule;

import lombok.extern.slf4j.Slf4j;
import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.test.literule.context.OrderContext;

/**
 * WechatPayRule
 *
 * @author Oort
 */
@Slf4j
@LiteRuleComponent(id = "wechatPayRule")
public class WechatPayRule extends RuleComponent {

	@Override
	protected void process() {
		OrderContext context = getContextBean(OrderContext.class);
		log.info("[规则4-微信] 处理微信支付：订单号={}, 金额={}",
			context.getOrderId(), context.getAmount());

		// 模拟支付处理
		context.setStatus("PAID");
	}
}
