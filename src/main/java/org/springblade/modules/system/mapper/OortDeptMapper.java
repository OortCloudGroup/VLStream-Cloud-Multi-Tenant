package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.modules.system.pojo.entity.OortDeptEntity;
import org.springblade.modules.system.pojo.vo.OortDeptVO;

import java.util.List;

/**
 *  Mapper interface
 *
 * @author BladeX
 * @since 2025-09-04
 */
public interface OortDeptMapper extends BaseMapper<OortDeptEntity> {

	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param oortDept query parameters
	 * @return List<OortDeptVO>
	 */
	List<OortDeptVO> selectOortDeptPage(IPage page, OortDeptVO oortDept);

	/**
	 * Query this department and all sub-departments based on department code
	 *
	 * @param deptCode Department code
	 * @return List<ApDeptEntity>
	 */
	List<OortDeptEntity> selectDeptAndChildrenByDeptCode(@Param("deptCode") String deptCode);

	/**
	 * Get department name
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptNames(Long[] ids);

}
