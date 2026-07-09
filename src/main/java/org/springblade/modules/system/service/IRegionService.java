package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.pojo.entity.Region;
import org.springblade.modules.system.excel.RegionExcel;
import org.springblade.modules.system.pojo.vo.RegionVO;

import java.util.List;
import java.util.Map;

/**
 * Administrative division table Service category
 *
 * @author Chill
 */
public interface IRegionService extends IService<Region> {

	/**
	 * submit
	 *
	 * @param region
	 * @return
	 */
	boolean submit(Region region);

	/**
	 * delete
	 *
	 * @param id
	 * @return
	 */
	boolean removeRegion(String id);

	/**
	 * Lazy loading list
	 *
	 * @param parentCode
	 * @param param
	 * @return
	 */
	List<RegionVO> lazyList(String parentCode, Map<String, Object> param);

	/**
	 * Lazy loading list
	 *
	 * @param parentCode
	 * @param param
	 * @return
	 */
	List<RegionVO> lazyTree(String parentCode, Map<String, Object> param);

	/**
	 * Import zoning data
	 *
	 * @param data
	 * @param isCovered
	 * @return
	 */
	void importRegion(List<RegionExcel> data, Boolean isCovered);

	/**
	 * Export zoning data
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<RegionExcel> exportRegion(Wrapper<Region> queryWrapper);

}
