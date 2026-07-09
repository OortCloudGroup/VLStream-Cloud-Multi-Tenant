package org.springblade.modules.system.service;

import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.system.pojo.entity.Param;

/**
 * Service category
 *
 * @author Chill
 */
public interface IParamService extends BaseService<Param> {

	/**
	 * Get parameter value
	 *
	 * @param paramKey parameterkey
	 * @return String
	 */
	String getValue(String paramKey);

}
