package org.springblade.vlstream.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.TimeStrategy;

import java.io.Serial;

/**
 * 时间策略表 视图实体类
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
