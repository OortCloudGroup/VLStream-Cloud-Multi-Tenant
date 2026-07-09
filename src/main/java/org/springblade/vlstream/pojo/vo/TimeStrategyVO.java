package org.springblade.vlstream.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.TimeStrategy;

import java.io.Serial;

/**
 * time strategy table View entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TimeStrategyVO extends TimeStrategy {
	@Serial
	private static final long serialVersionUID = 1L;

}
