package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.core.tenant.annotation.TenantIgnore;
import org.springblade.modules.system.pojo.entity.DictBiz;
import org.springblade.modules.system.pojo.vo.DictBizVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface DictBizMapper extends BaseMapper<DictBiz> {

	/**
	 * 获取字典表对应中文
	 *
	 * @param tenantId 租户ID
	 * @param code     字典编号
	 * @param dictKey  字典序号
	 * @return
	 */
	@TenantIgnore
	String getValue(String tenantId, String code, String dictKey);

	/**
	 * 获取字典表
	 *
	 * @param tenantId 租户ID
	 * @param code     字典编号
	 * @return
	 */
	@TenantIgnore
	List<DictBiz> getList(String tenantId, String code);

	/**
	 * 获取树形节点
	 *
	 * @return
	 */
	List<DictBizVO> tree();

	/**
	 * 获取树形节点
	 *
	 * @return
	 */
	List<DictBizVO> parentTree();

}
