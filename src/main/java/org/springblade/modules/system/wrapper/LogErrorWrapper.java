package org.springblade.modules.system.wrapper;

import org.springblade.core.log.model.LogError;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.pojo.vo.LogErrorVO;

import java.util.Objects;

/**
 * Log包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class LogErrorWrapper extends BaseEntityWrapper<LogError, LogErrorVO> {

	public static LogErrorWrapper build() {
		return new LogErrorWrapper();
	}

	@Override
	public LogErrorVO entityVO(LogError logError) {
		return Objects.requireNonNull(BeanUtil.copyProperties(logError, LogErrorVO.class));
	}
}
