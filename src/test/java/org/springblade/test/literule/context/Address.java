package org.springblade.test.literule.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地址实体
 *
 * @author Oort
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	private String country;
	private String province;
	private String city;
	private String detail;
}
