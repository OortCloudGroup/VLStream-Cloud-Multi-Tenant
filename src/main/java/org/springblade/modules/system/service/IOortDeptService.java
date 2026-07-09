package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.system.pojo.entity.OortDeptEntity;
import org.springblade.modules.system.pojo.vo.OortDeptVO;

import java.util.List;

/**
 *  Single tenant department query service class
 *
 * @author BladeX
 * @since 2025-09-04
 */
public interface IOortDeptService extends BaseService<OortDeptEntity> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param oortDept query parameters
	 * @return IPage<OortDeptVO>
	 */
	IPage<OortDeptVO> selectOortDeptPage(IPage<OortDeptVO> page, OortDeptVO oortDept);

	/**
	 * Get departmentID
	 *
	 * @param tenantId
	 * @param deptNames
	 * @return
	 */
	String getDeptIds(String tenantId, String deptNames);

	/**
	 * Get departmentID
	 *
	 * @param tenantId
	 * @param deptNames
	 * @return
	 */
	String getDeptIdsByFuzzy(String tenantId, String deptNames);

	/**
	 * Get department name
	 *
	 * @param deptIds
	 * @return
	 */
	List<String> getDeptNames(String deptIds);

	/**
	 * Get subdepartmentID
	 *
	 * @param deptId
	 * @return
	 */
	List<OortDeptEntity> getDeptChild(String deptId);

}
