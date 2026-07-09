package org.springblade.modules.system.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.core.mp.support.Query;
import org.springblade.modules.auth.provider.UserType;
import org.springblade.modules.system.pojo.entity.User;
import org.springblade.modules.system.pojo.entity.UserInfo;
import org.springblade.modules.system.pojo.entity.UserOauth;
import org.springblade.modules.system.excel.UserExcel;
import org.springblade.modules.system.pojo.vo.UserVO;

import java.util.List;

/**
 * Service category
 *
 * @author Chill
 */
public interface IUserService extends BaseService<User> {

	/**
	 * Add new user
	 *
	 * @param user
	 * @return
	 */
	boolean submit(User user);

	/**
	 * Modify user
	 *
	 * @param user
	 * @return
	 */
	boolean updateUser(User user);

	/**
	 * Modify basic user information
	 *
	 * @param user
	 * @return
	 */
	boolean updateUserInfo(User user);

	/**
	 * Custom paging
	 *
	 * @param page
	 * @param user
	 * @param deptId
	 * @param tenantId
	 * @return
	 */
	IPage<User> selectUserPage(IPage<User> page, User user, Long deptId, String tenantId);

	/**
	 * Custom paging
	 *
	 * @param user
	 * @param query
	 * @return
	 */
	IPage<UserVO> selectUserSearch(UserVO user, Query query);

	/**
	 * Get users based on account
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	User userByAccount(String tenantId, String account);

	/**
	 * User information
	 *
	 * @param userId
	 * @return
	 */
	UserInfo userInfo(Long userId);

	/**
	 * User information
	 *
	 * @param userId
	 * @param userType
	 * @return
	 */
	UserInfo userInfo(Long userId, UserType userType);

	/**
	 * User information
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account);

	/**
	 * User information
	 *
	 * @param tenantId
	 * @param account
	 * @param userType
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account, UserType userType);

	/**
	 * User information
	 *
	 * @param tenantId
	 * @param phone
	 * @return
	 */
	UserInfo userInfoByPhone(String tenantId, String phone);

	/**
	 * User information
	 *
	 * @param tenantId
	 * @param phone
	 * @param userType
	 * @return
	 */
	UserInfo userInfoByPhone(String tenantId, String phone, UserType userType);

	/**
	 * User information
	 *
	 * @param userOauth
	 * @return
	 */
	UserInfo userInfo(UserOauth userOauth);

	/**
	 * Set roles for users
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * Initialization password
	 *
	 * @param userIds
	 * @return
	 */
	boolean resetPassword(String userIds);

	/**
	 * Change password
	 *
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1);

	/**
	 * Delete user
	 *
	 * @param userIds
	 * @return
	 */
	boolean removeUser(String userIds);

	/**
	 * Import user data
	 *
	 * @param data
	 * @param isCovered
	 * @return
	 */
	void importUser(List<UserExcel> data, Boolean isCovered);

	/**
	 * Export user data
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(Wrapper<User> queryWrapper);

	/**
	 * Registered user
	 *
	 * @param user
	 * @param oauthId
	 * @return
	 */
	boolean registerGuest(User user, Long oauthId);

	/**
	 * Registered user
	 *
	 * @param user
	 * @return
	 */
	boolean registerUser(User user);

	/**
	 * Configure user platform
	 *
	 * @param userId
	 * @param userType
	 * @param userExt
	 * @return
	 */
	boolean updatePlatform(Long userId, Integer userType, String userExt);

	/**
	 * User details
	 *
	 * @param user
	 * @return
	 */
	UserVO platformDetail(User user);

	/**
	 * Unlock user
	 *
	 * @param userIds
	 * @return
	 */
	boolean unlock(String userIds);

	/**
	 * Approved
	 *
	 * @param userIds
	 * @return
	 */
	boolean auditPass(String userIds);

	/**
	 * Review rejection
	 *
	 * @param userIds
	 * @return
	 */
	boolean auditRefuse(String userIds);

	/**
	 * Set user as supervisor
	 *
	 * @param userId userid
	 * @return
	 */
	boolean setLeader(Long userId);

	/**
	 * Get user's supervisor information
	 *
	 * @param userId userid
	 * @return
	 */
	List<UserVO> leaderInfo(Long userId);

	/**
	 * Get list of supervisors
	 *
	 * @param tenantId Tenant number
	 * @param realName Username
	 * @return
	 */
	List<UserVO> leaderList(String tenantId, String realName);
}
