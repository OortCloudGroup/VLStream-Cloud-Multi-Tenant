package org.springblade.modules.system.service;


import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.system.pojo.entity.User;

import java.util.List;

/**
 * User query service class
 *
 * @author Chill
 */
public interface IUserSearchService extends BaseService<User> {

	/**
	 * According to userIDQuery user list
	 *
	 * @param userId userID
	 * @return User list
	 */
	List<User> listByUser(List<Long> userId);

	/**
	 * According to departmentIDQuery user list
	 *
	 * @param deptId departmentID
	 * @return User list
	 */
	List<User> listByDept(List<Long> deptId);

	/**
	 * According to positionIDQuery user list
	 *
	 * @param postId postID
	 * @return User list
	 */
	List<User> listByPost(List<Long> postId);

	/**
	 * According to roleIDQuery user list
	 *
	 * @param roleId RoleID
	 * @return User list
	 */
	List<User> listByRole(List<Long> roleId);

	/**
	 * Query supervisor list
	 *
	 * @param tenantId tenantID
	 * @param realName real name
	 * @return User list
	 */
	List<User> listLeader(String tenantId, String realName);

	/**
	 * Query the user's supervisor list
	 *
	 * @param userId userID
	 * @return User information
	 */
	List<User> getLeader(List<Long> userId);

}
