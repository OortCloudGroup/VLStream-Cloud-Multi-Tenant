package org.springblade.modules.system.service;


import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.system.pojo.entity.User;

import java.util.List;

/**
 * 用户查询服务类
 *
 * @author Chill
 */
public interface IUserSearchService extends BaseService<User> {

	/**
	 * 根据用户ID查询用户列表
	 *
	 * @param userId 用户ID
	 * @return 用户列表
	 */
	List<User> listByUser(List<Long> userId);

	/**
	 * 根据部门ID查询用户列表
	 *
	 * @param deptId 部门ID
	 * @return 用户列表
	 */
	List<User> listByDept(List<Long> deptId);

	/**
	 * 根据岗位ID查询用户列表
	 *
	 * @param postId 岗位ID
	 * @return 用户列表
	 */
	List<User> listByPost(List<Long> postId);

	/**
	 * 根据角色ID查询用户列表
	 *
	 * @param roleId 角色ID
	 * @return 用户列表
	 */
	List<User> listByRole(List<Long> roleId);

	/**
	 * 查询主管列表
	 *
	 * @param tenantId 租户ID
	 * @param realName 真实姓名
	 * @return 用户列表
	 */
	List<User> listLeader(String tenantId, String realName);

	/**
	 * 查询用户的主管列表
	 *
	 * @param userId 用户ID
	 * @return 用户信息
	 */
	List<User> getLeader(List<Long> userId);

}
