package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.modules.system.pojo.entity.Region;
import org.springblade.modules.system.excel.RegionExcel;
import org.springblade.modules.system.pojo.vo.RegionVO;

import java.util.List;
import java.util.Map;

/**
 * Administrative division table Mapper interface
 *
 * @author Chill
 */
public interface RegionMapper extends BaseMapper<Region> {

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
	 * Export zoning data
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<RegionExcel> exportRegion(@Param("ew") Wrapper<Region> queryWrapper);

}
