package org.springblade.modules.system.wrapper;

import org.springblade.core.log.model.LogUsual;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.pojo.vo.LogUsualVO;

import java.util.Objects;

/**
 * LogPackaging,Returns the fields required by the view layer
 *
 * @author Chill
 */
public class LogUsualWrapper extends BaseEntityWrapper<LogUsual, LogUsualVO> {

	public static LogUsualWrapper build() {
		return new LogUsualWrapper();
	}

	@Override
	public LogUsualVO entityVO(LogUsual logUsual) {
		return Objects.requireNonNull(BeanUtil.copyProperties(logUsual, LogUsualVO.class));
	}

}
