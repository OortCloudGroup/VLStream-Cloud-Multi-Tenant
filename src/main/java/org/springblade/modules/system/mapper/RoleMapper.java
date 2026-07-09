package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.system.pojo.entity.Role;
import org.springblade.modules.system.pojo.vo.RoleVO;

import java.util.List;

/**
 * Mapper interface
 *
 * @author Chill
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * Custom paging
	 *
	 * @param page
	 * @param role
	 * @return
	 */
	List<RoleVO> selectRolePage(IPage page, RoleVO role);

	/**
	 * Get tree nodes
	 *
	 * @param tenantId
	 * @param excludeRole
	 * @return
	 */
	List<RoleVO> tree(String tenantId, String excludeRole);

	/**
	 * Get character name
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleNames(Long[] ids);

	/**
	 * Get character name
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleAliases(Long[] ids);

}
