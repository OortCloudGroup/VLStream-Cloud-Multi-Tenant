package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.excel.ApDeptExcel;
import org.springblade.modules.system.pojo.entity.ApDeptEntity;
import org.springblade.modules.system.pojo.vo.ApDeptVO;

import java.util.List;

/**
 * Organization chart Service category
 *
 * @author Oort
 * @since 2025-08-09
 */
public interface IApDeptService extends IService<ApDeptEntity> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param apDept query parameters
	 * @return IPage<ApDeptVO>
	 */
	IPage<ApDeptVO> selectApDeptPage(IPage<ApDeptVO> page, ApDeptVO apDept);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<ApDeptExcel>
	 */
	List<ApDeptExcel> exportApDept(Wrapper<ApDeptEntity> queryWrapper);

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
	List<ApDeptEntity> getDeptChild(String deptId);

}
