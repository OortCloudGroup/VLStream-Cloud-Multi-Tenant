package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.excel.ApDeptExcel;
import org.springblade.modules.system.mapper.ApDeptMapper;
import org.springblade.modules.system.pojo.entity.ApDeptEntity;
import org.springblade.modules.system.pojo.vo.ApDeptVO;
import org.springblade.modules.system.service.IApDeptService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 组织机构表 服务实现类
 *
 * @author Oort
 * @since 2025-08-09
 */
@Service
public class ApDeptServiceImpl extends ServiceImpl<ApDeptMapper, ApDeptEntity> implements IApDeptService {

	@Resource
	private ApDeptMapper baseMapper;

	@Override
	public IPage<ApDeptVO> selectApDeptPage(IPage<ApDeptVO> page, ApDeptVO apDept) {
		return page.setRecords(baseMapper.selectApDeptPage(page, apDept));
	}

	@Override
	public List<ApDeptExcel> exportApDept(Wrapper<ApDeptEntity> queryWrapper) {
		List<ApDeptExcel> apDeptList = baseMapper.exportApDept(queryWrapper);
		//apDeptList.forEach(apDept -> {
		//	apDept.setTypeName(DictCache.getValue(DictEnum.YES_NO, ApDeptEntity.getType()));
		//});
		return apDeptList;
	}

	@Override
	public String getDeptIds(String tenantId, String deptNames) {
		List<ApDeptEntity> deptList = baseMapper.selectList(Wrappers.<ApDeptEntity>query().lambda().eq(ApDeptEntity::getTenantId, tenantId).in(ApDeptEntity::getDeptName, Func.toStrList(deptNames)));
		if (deptList != null && !deptList.isEmpty()) {
			return deptList.stream().map(dept -> Func.toStr(dept.getDeptId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public String getDeptIdsByFuzzy(String tenantId, String deptNames) {
		LambdaQueryWrapper<ApDeptEntity> queryWrapper = Wrappers.<ApDeptEntity>query().lambda().eq(ApDeptEntity::getTenantId, tenantId);
		queryWrapper.and(wrapper -> {
			List<String> names = Func.toStrList(deptNames);
			names.forEach(name -> wrapper.like(ApDeptEntity::getDeptName, name).or());
		});
		List<ApDeptEntity> deptList = baseMapper.selectList(queryWrapper);
		if (deptList != null && !deptList.isEmpty()) {
			return deptList.stream().map(dept -> Func.toStr(dept.getDeptId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getDeptNames(String deptIds) {
		return baseMapper.getDeptNames(Func.toLongArray(deptIds));
	}

	@Override
	public List<ApDeptEntity> getDeptChild(String deptId) {
		return baseMapper.selectList(Wrappers.<ApDeptEntity>query().lambda().like(ApDeptEntity::getDeptCodePath, deptId));
	}

}
