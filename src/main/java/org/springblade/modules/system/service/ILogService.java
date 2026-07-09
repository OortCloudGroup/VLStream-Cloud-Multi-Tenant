package org.springblade.modules.system.service;

import org.springblade.core.log.model.LogApi;
import org.springblade.core.log.model.LogError;
import org.springblade.core.log.model.LogUsual;

/**
 * Service category
 *
 * @author Chill
 */
public interface ILogService {

	/**
	 * Save general log
	 *
	 * @param log
	 * @return
	 */
	Boolean saveUsualLog(LogUsual log);

	/**
	 * Save operation log
	 *
	 * @param log
	 * @return
	 */
	Boolean saveApiLog(LogApi log);

	/**
	 * Save error log
	 *
	 * @param log
	 * @return
	 */
	Boolean saveErrorLog(LogError log);

}
