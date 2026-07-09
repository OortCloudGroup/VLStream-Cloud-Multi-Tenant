package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.modules.system.excel.ApDeptExcel;
import org.springblade.modules.system.pojo.entity.ApDeptEntity;
import org.springblade.modules.system.pojo.vo.ApDeptVO;

import java.util.List;

/**
 * Organization chart Mapper interface
 *
 * @author BladeX
 * @since 2025-08-09
 */
public interface ApDeptMapper extends BaseMapper<ApDeptEntity> {

	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param apDept query parameters
	 * @return List<ApDeptVO>
	 */
	List<ApDeptVO> selectApDeptPage(IPage page, ApDeptVO apDept);

	/**
	 * Get export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<ApDeptExcel>
	 */
	List<ApDeptExcel> exportApDept(@Param("ew") Wrapper<ApDeptEntity> queryWrapper);

	/**
	 * Query this department and all sub-departments based on department code
	 *
	 * @param deptCode Department code
	 * @return List<ApDeptEntity>
	 */
	List<ApDeptEntity> selectDeptAndChildrenByDeptCode(@Param("deptCode") String deptCode);

	/**
	 * Get department name
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptNames(Long[] ids);

}
