package org.springblade.test.literule.rule;

import lombok.extern.slf4j.Slf4j;
import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.test.literule.context.OrderContext;

/**
 * AlipayRule
 *
 * @author Oort
 */
@Slf4j
@LiteRuleComponent(id = "alipayRule")
public class AlipayRule extends RuleComponent {

	@Override
	protected void process() {
		OrderContext context = getContextBean(OrderContext.class);
		log.info("[规则4-支付宝] 处理支付宝支付：订单号={}, 金额={}",
			context.getOrderId(), context.getAmount());

		// 模拟支付处理
		context.setStatus("PAID");
	}
}
