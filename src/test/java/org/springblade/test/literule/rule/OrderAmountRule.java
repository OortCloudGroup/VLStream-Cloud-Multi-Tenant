package org.springblade.test.literule.rule;

import lombok.extern.slf4j.Slf4j;
import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.test.literule.context.Address;
import org.springblade.test.literule.context.OrderContext;

import java.math.BigDecimal;

/**
 * OrderAmountRule
 *
 * @author Oort
 */
@Slf4j
@LiteRuleComponent(id = "orderAmountRule")
public class OrderAmountRule extends RuleComponent {

	@Override
	protected void process() {
		OrderContext context = getContextBean(OrderContext.class);
		BigDecimal amount = context.getAmount();

		// 计算折扣
		BigDecimal discount = calculateDiscount(amount);

		// 计算运费
		BigDecimal shipping = calculateShipping(context.getShippingAddress());

		// 更新订单金额
		BigDecimal finalAmount = amount.subtract(discount).add(shipping);
		context.setAmount(finalAmount);

		log.info("[规则2] 订单金额计算完成：{} = {} - {} + {}", finalAmount, amount, discount, shipping);
	}

	private BigDecimal calculateDiscount(BigDecimal amount) {
		// 折扣计算逻辑：金额大于1000打9折
		if (amount.compareTo(new BigDecimal("1000")) > 0) {
			return amount.multiply(new BigDecimal("0.1"));
		}
		return BigDecimal.ZERO;
	}

	private BigDecimal calculateShipping(Address address) {
		// 运费计算逻辑：根据地址计算
		if ("China".equals(address.getCountry())) {
			return new BigDecimal("10");
		}
		return new BigDecimal("50");
	}
}
