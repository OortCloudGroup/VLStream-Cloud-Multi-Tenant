package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.excel.ApDeptExcel;
import org.springblade.modules.system.pojo.entity.ApDeptEntity;
import org.springblade.modules.system.pojo.vo.ApDeptVO;

import java.util.List;

/**
 * 组织机构表 服务类
 *
 * @author Oort
 * @since 2025-08-09
 */
public interface IApDeptService extends IService<ApDeptEntity> {
	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param apDept 查询参数
	 * @return IPage<ApDeptVO>
	 */
	IPage<ApDeptVO> selectApDeptPage(IPage<ApDeptVO> page, ApDeptVO apDept);

	/**
	 * 导出数据
	 *
	 * @param queryWrapper 查询条件
	 * @return List<ApDeptExcel>
	 */
	List<ApDeptExcel> exportApDept(Wrapper<ApDeptEntity> queryWrapper);

	/**
	 * 获取部门ID
	 *
	 * @param tenantId
	 * @param deptNames
	 * @return
	 */
	String getDeptIds(String tenantId, String deptNames);

	/**
	 * 获取部门ID
	 *
	 * @param tenantId
	 * @param deptNames
	 * @return
	 */
	String getDeptIdsByFuzzy(String tenantId, String deptNames);

	/**
	 * 获取部门名
	 *
	 * @param deptIds
	 * @return
	 */
	List<String> getDeptNames(String deptIds);

	/**
	 * 获取子部门ID
	 *
	 * @param deptId
	 * @return
	 */
	List<ApDeptEntity> getDeptChild(String deptId);

}
