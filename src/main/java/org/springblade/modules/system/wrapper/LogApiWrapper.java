package org.springblade.modules.system.wrapper;

import org.springblade.core.log.model.LogApi;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.pojo.vo.LogApiVO;

import java.util.Objects;

/**
 * Log包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class LogApiWrapper extends BaseEntityWrapper<LogApi, LogApiVO> {

	public static LogApiWrapper build() {
		return new LogApiWrapper();
	}

	@Override
	public LogApiVO entityVO(LogApi logApi) {
		return Objects.requireNonNull(BeanUtil.copyProperties(logApi, LogApiVO.class));
	}

}
