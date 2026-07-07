package org.springblade.test.literule.rule;

import lombok.extern.slf4j.Slf4j;
import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleSwitchComponent;
import org.springblade.test.literule.context.OrderContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PaymentRouteRule
 *
 * @author Oort
 */
@Slf4j
@LiteRuleComponent(id = "paymentSwitchRule")
public class PaymentSwitchRule extends RuleSwitchComponent {

	private static final Map<String, String> PAYMENT_RULE_MAP = new HashMap<>();

	static {
		PAYMENT_RULE_MAP.put("ALIPAY", "alipayRule");
		PAYMENT_RULE_MAP.put("WECHAT", "wechatPayRule");
		PAYMENT_RULE_MAP.put("BANK", "bankPayRule");
	}

	@Override
	protected List<String> process() {
		OrderContext context = getContextBean(OrderContext.class);
		String paymentType = context.getPaymentType();

		log.info("[规则3] 支付路由选择：{}", paymentType);

		String payName = PAYMENT_RULE_MAP.get(paymentType);
		if (payName != null) {
			return Collections.singletonList(payName);
		} else {
			context.addError("不支持的支付方式：" + paymentType);
			return Collections.emptyList();
		}
	}
}
