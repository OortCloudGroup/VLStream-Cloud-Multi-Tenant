package org.springblade.common.cache;

import org.springblade.common.enums.DictEnum;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.system.pojo.entity.Dict;
import org.springblade.modules.system.service.IDictService;

import java.util.List;
import java.util.Optional;

import static org.springblade.core.cache.constant.CacheConstant.DICT_CACHE;

/**
 * Dictionary cache tool class
 *
 * @author Chill
 */
public class DictCache {

	private static final String DICT_ID = "dict:id:";
	private static final String DICT_KEY = "dict:key:";
	private static final String DICT_VALUE = "dict:value:";
	private static final String DICT_LIST = "dict:list:";

	private static final Boolean TENANT_MODE = Boolean.FALSE;

	private static final IDictService dictService;

	static {
		dictService = SpringUtil.getBean(IDictService.class);
	}

	/**
	 * Get dictionary entity
	 *
	 * @param id primary key
	 * @return Dict
	 */
	public static Dict getById(Long id) {
		return CacheUtil.get(DICT_CACHE, DICT_ID, id, () -> dictService.getById(id), TENANT_MODE);
	}

	/**
	 * Get dictionary value
	 *
	 * @param code      dictionary number enum
	 * @param dictValue Dictionary value
	 * @return String
	 */
	public static String getKey(DictEnum code, String dictValue) {
		return getKey(code.getName(), dictValue);
	}

	/**
	 * Get dictionary key
	 *
	 * @param code      dictionary number
	 * @param dictValue Dictionary value
	 * @return String
	 */
	public static String getKey(String code, String dictValue) {
		return CacheUtil.get(DICT_CACHE, DICT_KEY + code + StringPool.COLON, dictValue, () -> {
			List<Dict> list = getList(code);
			Optional<String> key = list.stream().filter(
				dict -> dict.getDictValue().equalsIgnoreCase(dictValue)
			).map(Dict::getDictKey).findFirst();
			return key.orElse(StringPool.EMPTY);
		}, TENANT_MODE);
	}

	/**
	 * Get dictionary value
	 *
	 * @param code    dictionary number enum
	 * @param dictKey Integertype dictionary key
	 * @return String
	 */
	public static String getValue(DictEnum code, Integer dictKey) {
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
		return CacheUtil.get(DICT_CACHE, DICT_VALUE + code + StringPool.COLON, String.valueOf(dictKey), () -> dictService.getValue(code, String.valueOf(dictKey)), TENANT_MODE);
	}

	/**
	 * Get dictionary value
	 *
	 * @param code    dictionary number enum
	 * @param dictKey Stringtype dictionary key
	 * @return String
	 */
	public static String getValue(DictEnum code, String dictKey) {
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
		return CacheUtil.get(DICT_CACHE, DICT_VALUE + code + StringPool.COLON, dictKey, () -> dictService.getValue(code, dictKey), TENANT_MODE);
	}

	/**
	 * Get dictionary collection
	 *
	 * @param code dictionary number
	 * @return List<Dict>
	 */
	public static List<Dict> getList(String code) {
		return CacheUtil.get(DICT_CACHE, DICT_LIST, code, () -> dictService.getList(code), TENANT_MODE);
	}

}
