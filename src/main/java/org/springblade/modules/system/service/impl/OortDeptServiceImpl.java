package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.mapper.OortDeptMapper;
import org.springblade.modules.system.pojo.entity.OortDeptEntity;
import org.springblade.modules.system.pojo.vo.OortDeptVO;
import org.springblade.modules.system.service.IOortDeptService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  Service implementation class
 *
 * @author BladeX
 * @since 2025-09-04
 */
@Service
public class OortDeptServiceImpl extends BaseServiceImpl<OortDeptMapper, OortDeptEntity> implements IOortDeptService {

	@Override
	public IPage<OortDeptVO> selectOortDeptPage(IPage<OortDeptVO> page, OortDeptVO oortDept) {
		return page.setRecords(baseMapper.selectOortDeptPage(page, oortDept));
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		List<OortDeptEntity> deptList = baseMapper.selectList(Wrappers.<OortDeptEntity>query().lambda().eq(OortDeptEntity::getTenantId, tenantId).in(OortDeptEntity::getOortDname, Func.toStrList(deptNames)));
		if (deptList != null && !deptList.isEmpty()) {
			return deptList.stream().map(dept -> Func.toStr(dept.getOortUdid())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public String getDeptIdsByFuzzy(String tenantId, String deptNames) {
		LambdaQueryWrapper<OortDeptEntity> queryWrapper = Wrappers.<OortDeptEntity>query().lambda().eq(OortDeptEntity::getTenantId, tenantId);
		queryWrapper.and(wrapper -> {
			List<String> names = Func.toStrList(deptNames);
			names.forEach(name -> wrapper.like(OortDeptEntity::getOortDname, name).or());
		});
		List<OortDeptEntity> deptList = baseMapper.selectList(queryWrapper);
		if (deptList != null && !deptList.isEmpty()) {
			return deptList.stream().map(dept -> Func.toStr(dept.getOortUdid())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return baseMapper.getDeptNames(Func.toLongArray(deptIds));
	}

	@Override
	public List<OortDeptEntity> getDeptChild(String deptId) {
		return baseMapper.selectList(Wrappers.<OortDeptEntity>query().lambda().like(OortDeptEntity::getOortDcodepath, deptId));
	}

}
