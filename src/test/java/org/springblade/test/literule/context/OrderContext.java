package org.springblade.test.literule.context;

import lombok.*;
import org.springblade.core.literule.core.RuleContextComponent;

import java.math.BigDecimal;

/**
 * 订单上下文
 *
 * @author Oort
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderContext extends RuleContextComponent {
	private String orderId;
	private BigDecimal amount;
	private String paymentType;
	private String status;
	private Address shippingAddress;
}
