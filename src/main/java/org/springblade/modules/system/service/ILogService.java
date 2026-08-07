package org.springblade.modules.system.service;

import org.springblade.core.log.model.LogApi;
import org.springblade.core.log.model.LogError;
import org.springblade.core.log.model.LogUsual;

/**
 * 服务类
 *
 * @author Chill
 */
public interface ILogService {

	/**
	 * 保存通用日志
	 *
	 * @param log
	 * @return
	 */
	Boolean saveUsualLog(LogUsual log);

	/**
	 * 保存操作日志
	 *
	 * @param log
	 * @return
	 */
	Boolean saveApiLog(LogApi log);

	/**
	 * 保存错误日志
	 *
	 * @param log
	 * @return
	 */
	Boolean saveErrorLog(LogError log);

}
