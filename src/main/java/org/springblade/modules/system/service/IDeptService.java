package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.pojo.entity.Dept;
import org.springblade.modules.system.pojo.vo.DeptVO;
import org.springblade.modules.system.pojo.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * Service category
 *
 * @author Chill
 */
public interface IDeptService extends IService<Dept> {

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
	 * tree structure
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * Lazy loading tree structure
	 *
	 * @param tenantId
	 * @param parentId
	 * @return
	 */
	List<DeptVO> lazyTree(String tenantId, Long parentId);

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
	List<Dept> getDeptChild(Long deptId);

	/**
	 * Delete department
	 *
	 * @param ids
	 * @return
	 */
	boolean removeDept(String ids);

	/**
	 * submit
	 *
	 * @param dept
	 * @return
	 */
	boolean submit(Dept dept);

	/**
	 * Department information query
	 *
	 * @param deptName
	 * @param parentId
	 * @return
	 */
	List<DeptVO> search(String deptName, Long parentId);

	/**
	 * Get department manager information
	 *
	 * @param deptId departmentid
	 * @return
	 */
	List<UserVO> deptLeaderInfo(Long deptId);

}
