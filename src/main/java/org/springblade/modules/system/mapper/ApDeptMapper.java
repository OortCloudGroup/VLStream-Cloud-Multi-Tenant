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
 * 组织机构表 Mapper 接口
 *
 * @author BladeX
 * @since 2025-08-09
 */
public interface ApDeptMapper extends BaseMapper<ApDeptEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param apDept 查询参数
	 * @return List<ApDeptVO>
	 */
	List<ApDeptVO> selectApDeptPage(IPage page, ApDeptVO apDept);

	/**
	 * 获取导出数据
	 *
	 * @param queryWrapper 查询条件
	 * @return List<ApDeptExcel>
	 */
	List<ApDeptExcel> exportApDept(@Param("ew") Wrapper<ApDeptEntity> queryWrapper);

	/**
	 * 根据部门编码查询本部门及所有子部门
	 *
	 * @param deptCode 部门编码
	 * @return List<ApDeptEntity>
	 */
	List<ApDeptEntity> selectDeptAndChildrenByDeptCode(@Param("deptCode") String deptCode);

	/**
	 * 获取部门名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptNames(Long[] ids);

}
