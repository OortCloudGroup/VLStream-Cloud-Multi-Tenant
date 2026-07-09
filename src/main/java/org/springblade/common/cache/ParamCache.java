package org.springblade.common.cache;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.modules.system.pojo.entity.Param;
import org.springblade.modules.system.service.IParamService;

import static org.springblade.core.cache.constant.CacheConstant.PARAM_CACHE;

/**
 * Parameter cache tool class
 *
 * @author Chill
 */
public class ParamCache {

	private static final String PARAM_ID = "param:id:";
	private static final String PARAM_VALUE = "param:value:";

	private static final IParamService paramService;

	static {
		paramService = SpringUtil.getBean(IParamService.class);
	}

	/**
	 * Get parameter entity
	 *
	 * @param id primary key
	 * @return Param
	 */
	public static Param getById(Long id) {
		return CacheUtil.get(PARAM_CACHE, PARAM_ID, id, () -> paramService.getById(id));
	}

	/**
	 * Get parameter configuration
	 *
	 * @param paramKey Parameter value
	 * @return String
	 */
	public static String getValue(String paramKey) {
		return CacheUtil.get(PARAM_CACHE, PARAM_VALUE, paramKey, () -> paramService.getValue(paramKey));
	}

}
