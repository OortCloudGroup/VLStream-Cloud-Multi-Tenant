package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.DeviceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Equipment information table View entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceInfoVO extends DeviceInfo {
	@Serial
	private static final long serialVersionUID = 1L;

	private String algorithmName;

}
