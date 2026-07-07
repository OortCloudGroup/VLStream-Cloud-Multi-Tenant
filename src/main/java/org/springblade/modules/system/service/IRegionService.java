package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.pojo.entity.Region;
import org.springblade.modules.system.excel.RegionExcel;
import org.springblade.modules.system.pojo.vo.RegionVO;

import java.util.List;
import java.util.Map;

/**
 * 行政区划表 服务类
 *
 * @author Chill
 */
public interface IRegionService extends IService<Region> {

	/**
	 * 提交
	 *
	 * @param region
	 * @return
	 */
	boolean submit(Region region);

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	boolean removeRegion(String id);

	/**
	 * 懒加载列表
	 *
	 * @param parentCode
	 * @param param
	 * @return
	 */
	List<RegionVO> lazyList(String parentCode, Map<String, Object> param);

	/**
	 * 懒加载列表
	 *
	 * @param parentCode
	 * @param param
	 * @return
	 */
	List<RegionVO> lazyTree(String parentCode, Map<String, Object> param);

	/**
	 * 导入区划数据
	 *
	 * @param data
	 * @param isCovered
	 * @return
	 */
	void importRegion(List<RegionExcel> data, Boolean isCovered);

	/**
	 * 导出区划数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<RegionExcel> exportRegion(Wrapper<Region> queryWrapper);

}
