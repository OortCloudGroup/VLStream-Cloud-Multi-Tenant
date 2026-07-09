package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.modules.system.pojo.entity.Dept;
import org.springblade.modules.system.pojo.vo.DeptVO;

import java.util.List;
import java.util.Map;

/**
 * Mapper interface
 *
 * @author Chill
 */
public interface DeptMapper extends BaseMapper<Dept> {

	/**
	 * Lazy loading of department list
	 *
	 * @param tenantId
	 * @param parentId
	 * @param param
	 * @return
	 */
	List<DeptVO> lazyList(String tenantId, Long parentId, Map<String, Object> param);

	/**
	 * Get tree nodes
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * Lazy loading to obtain tree nodes
	 *
	 * @param tenantId
	 * @param parentId
	 * @return
	 */
	List<DeptVO> lazyTree(String tenantId, Long parentId);

	/**
	 * Get department name
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptNames(Long[] ids);

}
