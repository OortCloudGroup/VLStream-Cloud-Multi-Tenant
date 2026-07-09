package org.springblade.common.cache;

import org.springblade.common.enums.DictBizEnum;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.system.pojo.entity.DictBiz;
import org.springblade.modules.system.service.IDictBizService;

import java.util.List;

import static org.springblade.core.cache.constant.CacheConstant.DICT_CACHE;

/**
 * Business dictionary cache tool class
 *
 * @author Chill
 */
public class DictBizCache {

	private static final String DICT_ID = "dictBiz:id";
	private static final String DICT_VALUE = "dictBiz:value";
	private static final String DICT_LIST = "dictBiz:list";

	private static final IDictBizService dictService;

	static {
		dictService = SpringUtil.getBean(IDictBizService.class);
	}

	/**
	 * Get dictionary entity
	 *
	 * @param id primary key
	 * @return DictBiz
	 */
	public static DictBiz getById(Long id) {
		String keyPrefix = DICT_ID.concat(StringPool.DASH).concat(AuthUtil.getTenantId()).concat(StringPool.COLON);
		return CacheUtil.get(DICT_CACHE, keyPrefix, id, () -> dictService.getById(id));
	}

	/**
	 * Get dictionary value
	 *
	 * @param code    dictionary number enum
	 * @param dictKey Integertype dictionary key
	 * @return String
	 */
	public static String getValue(DictBizEnum code, Integer dictKey) {
		return getValue(code.getName(), dictKey);
	}

	/**
	 * Get dictionary value
	 *
	 * @param code    dictionary number
	 * @param dictKey Integertype dictionary key
	 * @return String
	 */
	public static String getValue(String code, Integer dictKey) {
		String keyPrefix = DICT_VALUE.concat(StringPool.DASH).concat(AuthUtil.getTenantId()).concat(StringPool.COLON);
		return CacheUtil.get(DICT_CACHE, keyPrefix + code + StringPool.COLON, String.valueOf(dictKey), () -> dictService.getValue(code, String.valueOf(dictKey)));
	}

	/**
	 * Get dictionary value
	 *
	 * @param code    dictionary number enum
	 * @param dictKey Stringtype dictionary key
	 * @return String
	 */
	public static String getValue(DictBizEnum code, String dictKey) {
		return getValue(code.getName(), dictKey);
	}

	/**
	 * Get dictionary value
	 *
	 * @param code    dictionary number
	 * @param dictKey Stringtype dictionary key
	 * @return String
	 */
	public static String getValue(String code, String dictKey) {
		String keyPrefix = DICT_VALUE.concat(StringPool.DASH).concat(AuthUtil.getTenantId()).concat(StringPool.COLON);
		return CacheUtil.get(DICT_CACHE, keyPrefix + code + StringPool.COLON, dictKey, () -> dictService.getValue(code, dictKey));
	}

	/**
	 * Get dictionary collection
	 *
	 * @param code dictionary number
	 * @return List<DictBiz>
	 */
	public static List<DictBiz> getList(String code) {
		String keyPrefix = DICT_LIST.concat(StringPool.DASH).concat(AuthUtil.getTenantId()).concat(StringPool.COLON);
		return CacheUtil.get(DICT_CACHE, keyPrefix, code, () -> dictService.getList(code));
	}

}
