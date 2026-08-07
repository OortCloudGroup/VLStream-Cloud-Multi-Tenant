package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.system.pojo.entity.OortDeptEntity;
import org.springblade.modules.system.pojo.vo.OortDeptVO;

import java.util.List;

/**
 *  单租户部门查询服务类
 *
 * @author BladeX
 * @since 2025-09-04
 */
public interface IOortDeptService extends BaseService<OortDeptEntity> {
	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param oortDept 查询参数
	 * @return IPage<OortDeptVO>
	 */
	IPage<OortDeptVO> selectOortDeptPage(IPage<OortDeptVO> page, OortDeptVO oortDept);

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
	List<OortDeptEntity> getDeptChild(String deptId);

}
