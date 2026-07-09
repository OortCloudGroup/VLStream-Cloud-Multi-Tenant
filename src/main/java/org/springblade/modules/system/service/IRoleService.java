package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.pojo.entity.Role;
import org.springblade.modules.system.pojo.vo.RoleVO;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Service category
 *
 * @author Chill
 */
public interface IRoleService extends IService<Role> {

	/**
	 * Custom paging
	 *
	 * @param page Pagination
	 * @param role Role
	 * @return Pagination
	 */
	IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role);

	/**
	 * tree structure
	 *
	 * @param tenantId tenantid
	 * @return role list
	 */
	List<RoleVO> tree(String tenantId);

	/**
	 * Permission configuration
	 *
	 * @param roleIds      Roleidgather
	 * @param menuIds      menuidgather
	 * @param dataScopeIds Data permissionsidgather
	 * @param apiScopeIds  Interface permissionsidgather
	 * @return Is it successful?
	 */
	boolean grant(@NotEmpty List<Long> roleIds, List<Long> menuIds, List<Long> dataScopeIds, List<Long> apiScopeIds);

	/**
	 * Get roleID
	 *
	 * @param tenantId  tenantid
	 * @param roleNames Character name
	 * @return Roleid
	 */
	String getRoleIds(String tenantId, String roleNames);

	/**
	 * Get character name
	 *
	 * @param roleIds Roleid
	 * @return Character name
	 */
	List<String> getRoleNames(String roleIds);

	/**
	 * Get character name
	 *
	 * @param roleIds Roleid
	 * @return role alias
	 */
	List<String> getRoleAliases(String roleIds);

	/**
	 * submit
	 *
	 * @param role Role
	 * @return boolean
	 */
	boolean submit(Role role);

	/**
	 * Role information query
	 *
	 * @param roleName Character name
	 * @param parentId parentid
	 * @return role list
	 */
	List<RoleVO> search(String roleName, Long parentId);

	/**
	 * Delete role
	 *
	 * @param ids primary key set
	 * @return boolean
	 */
	boolean removeRole(String ids);

	/**
	 * Get a list of role aliases
	 *
	 * @param tenantId tenantid
	 * @return Alias ​​list
	 */
	List<Role> alias(String tenantId);


}
