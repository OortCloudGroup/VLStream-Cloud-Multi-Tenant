package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.core.tenant.annotation.TenantIgnore;
import org.springblade.modules.system.pojo.entity.DictBiz;
import org.springblade.modules.system.pojo.vo.DictBizVO;

import java.util.List;

/**
 * Mapper interface
 *
 * @author Chill
 */
public interface DictBizMapper extends BaseMapper<DictBiz> {

	/**
	 * Get the dictionary table corresponding to Chinese
	 *
	 * @param tenantId tenantID
	 * @param code     dictionary number
	 * @param dictKey  Dictionary number
	 * @return
	 */
	@TenantIgnore
	String getValue(String tenantId, String code, String dictKey);

	/**
	 * Get dictionary table
	 *
	 * @param tenantId tenantID
	 * @param code     dictionary number
	 * @return
	 */
	@TenantIgnore
	List<DictBiz> getList(String tenantId, String code);

	/**
	 * Get tree nodes
	 *
	 * @return
	 */
	List<DictBizVO> tree();

	/**
	 * Get tree nodes
	 *
	 * @return
	 */
	List<DictBizVO> parentTree();

}
