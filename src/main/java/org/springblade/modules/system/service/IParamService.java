package org.springblade.modules.system.service;

import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.system.pojo.entity.Param;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IParamService extends BaseService<Param> {

	/**
	 * 获取参数值
	 *
	 * @param paramKey 参数key
	 * @return String
	 */
	String getValue(String paramKey);

}
