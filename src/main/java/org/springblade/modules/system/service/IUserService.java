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
 * 服务类
 *
 * @author Chill
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增用户
	 *
	 * @param user
	 * @return
	 */
	boolean submit(User user);

	/**
	 * 修改用户
	 *
	 * @param user
	 * @return
	 */
	boolean updateUser(User user);

	/**
	 * 修改用户基本信息
	 *
	 * @param user
	 * @return
	 */
	boolean updateUserInfo(User user);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @param deptId
	 * @param tenantId
	 * @return
	 */
	IPage<User> selectUserPage(IPage<User> page, User user, Long deptId, String tenantId);

	/**
	 * 自定义分页
	 *
	 * @param user
	 * @param query
	 * @return
	 */
	IPage<UserVO> selectUserSearch(UserVO user, Query query);

	/**
	 * 根据账号获取用户
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	User userByAccount(String tenantId, String account);

	/**
	 * 用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfo userInfo(Long userId);

	/**
	 * 用户信息
	 *
	 * @param userId
	 * @param userType
	 * @return
	 */
	UserInfo userInfo(Long userId, UserType userType);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param account
	 * @param userType
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account, UserType userType);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param phone
	 * @return
	 */
	UserInfo userInfoByPhone(String tenantId, String phone);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param phone
	 * @param userType
	 * @return
	 */
	UserInfo userInfoByPhone(String tenantId, String phone, UserType userType);

	/**
	 * 用户信息
	 *
	 * @param userOauth
	 * @return
	 */
	UserInfo userInfo(UserOauth userOauth);

	/**
	 * 给用户设置角色
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 初始化密码
	 *
	 * @param userIds
	 * @return
	 */
	boolean resetPassword(String userIds);

	/**
	 * 修改密码
	 *
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1);

	/**
	 * 删除用户
	 *
	 * @param userIds
	 * @return
	 */
	boolean removeUser(String userIds);

	/**
	 * 导入用户数据
	 *
	 * @param data
	 * @param isCovered
	 * @return
	 */
	void importUser(List<UserExcel> data, Boolean isCovered);

	/**
	 * 导出用户数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(Wrapper<User> queryWrapper);

	/**
	 * 注册用户
	 *
	 * @param user
	 * @param oauthId
	 * @return
	 */
	boolean registerGuest(User user, Long oauthId);

	/**
	 * 注册用户
	 *
	 * @param user
	 * @return
	 */
	boolean registerUser(User user);

	/**
	 * 配置用户平台
	 *
	 * @param userId
	 * @param userType
	 * @param userExt
	 * @return
	 */
	boolean updatePlatform(Long userId, Integer userType, String userExt);

	/**
	 * 用户详细信息
	 *
	 * @param user
	 * @return
	 */
	UserVO platformDetail(User user);

	/**
	 * 解锁用户
	 *
	 * @param userIds
	 * @return
	 */
	boolean unlock(String userIds);

	/**
	 * 审核通过
	 *
	 * @param userIds
	 * @return
	 */
	boolean auditPass(String userIds);

	/**
	 * 审核拒绝
	 *
	 * @param userIds
	 * @return
	 */
	boolean auditRefuse(String userIds);

	/**
	 * 设置用户为主管
	 *
	 * @param userId 用户id
	 * @return
	 */
	boolean setLeader(Long userId);

	/**
	 * 获取用户的主管信息
	 *
	 * @param userId 用户id
	 * @return
	 */
	List<UserVO> leaderInfo(Long userId);

	/**
	 * 获取主管列表
	 *
	 * @param tenantId 租户编号
	 * @param realName 用户姓名
	 * @return
	 */
	List<UserVO> leaderList(String tenantId, String realName);
}
