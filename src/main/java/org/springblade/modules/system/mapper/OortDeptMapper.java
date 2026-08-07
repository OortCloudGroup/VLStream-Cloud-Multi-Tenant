package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.modules.system.pojo.entity.OortDeptEntity;
import org.springblade.modules.system.pojo.vo.OortDeptVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author BladeX
 * @since 2025-09-04
 */
public interface OortDeptMapper extends BaseMapper<OortDeptEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param oortDept 查询参数
	 * @return List<OortDeptVO>
	 */
	List<OortDeptVO> selectOortDeptPage(IPage page, OortDeptVO oortDept);

	/**
	 * 根据部门编码查询本部门及所有子部门
	 *
	 * @param deptCode 部门编码
	 * @return List<ApDeptEntity>
	 */
	List<OortDeptEntity> selectDeptAndChildrenByDeptCode(@Param("deptCode") String deptCode);

	/**
	 * 获取部门名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptNames(Long[] ids);

}
