package org.springblade.common.cache;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.modules.system.pojo.entity.Region;
import org.springblade.modules.system.service.IRegionService;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * Administrative division cache tool class
 *
 * @author Chill
 */
public class RegionCache {
	public static final String MAIN_CODE = "00";
	public static final int PROVINCE_LEVEL = 1;
	public static final int CITY_LEVEL = 2;
	public static final int DISTRICT_LEVEL = 3;
	public static final int TOWN_LEVEL = 4;
	public static final int VILLAGE_LEVEL = 5;

	private static final String REGION_CODE = "region:code:";

	private static final IRegionService regionService;

	static {
		regionService = SpringUtil.getBean(IRegionService.class);
	}

	/**
	 * Get administrative division entities
	 *
	 * @param code Zoning number
	 * @return Param
	 */
	public static Region getByCode(String code) {
		return CacheUtil.get(SYS_CACHE, REGION_CODE, code, () -> regionService.getById(code));
	}

}
