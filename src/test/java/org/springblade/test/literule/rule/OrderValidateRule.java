package org.springblade.test.literule.rule;

import lombok.extern.slf4j.Slf4j;
import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.test.literule.context.Address;
import org.springblade.test.literule.context.OrderContext;

import java.math.BigDecimal;

/**
 * OrderValidateRule
 *
 * @author Oort
 */
@Slf4j
@LiteRuleComponent(id = "orderValidateRule")
public class OrderValidateRule extends RuleComponent {

	@Override
	protected void process() {
		OrderContext context = getContextBean(OrderContext.class);

		// 校验订单ID
		if (isEmpty(context.getOrderId())) {
			context.addError("订单ID不能为空");
			log.info("[规则1] 订单校验不通过：{}", context.getErrorMessages());
			return;
		}

		// 校验金额
		if (context.getAmount() == null || context.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			context.addError("订单金额无效");
			log.info("[规则1] 订单校验不通过：{}", context.getErrorMessages());
			return;
		}

		// 校验地址
		Address address = context.getShippingAddress();
		if (address == null) {
			context.addError("收货地址不能为空");
			log.info("[规则1] 订单校验不通过：{}", context.getErrorMessages());
			return;
		}

		log.info("[规则1] 订单校验通过：{}", context.getOrderId());
	}

	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}
