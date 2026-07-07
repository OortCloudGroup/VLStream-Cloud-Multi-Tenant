package org.springblade.modules.auth.utils;

import org.springblade.common.cache.SysCache;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.impl.OAuth2UserDetail;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.User;
import org.springblade.modules.system.pojo.entity.UserInfo;

/**
 * 认证工具类
 *
 * @author Chill
 */
public class TokenUtil {

	/**
	 * 系统用户转换为OAuth2标准用户
	 *
	 * @param userInfo 用户信息
	 * @param request  请求信息
	 * @return OAuth2User
	 */
	public static OAuth2User convertUser(UserInfo userInfo, OAuth2Request request) {
		// 为空则返回null
		if (userInfo == null) {
			return null;
		}
		User user = userInfo.getUser();
		String userDept = request.getUserDept();
		String userRole = request.getUserRole();
		// 单独指定部门
		if (Func.isNotEmpty(userDept) && user.getDeptId().contains(userDept)) {
			user.setDeptId(userDept);
		}
		// 单独指定角色
		if (Func.isNotEmpty(userRole) && user.getRoleId().contains(userRole)) {
			user.setRoleId(userRole);
			userInfo.setRoles(SysCache.getRoleAliases(userRole));
		}
		// 构建oauth2所需用户信息
		OAuth2UserDetail userDetail = new OAuth2UserDetail();
		userDetail.setUserId(String.valueOf(user.getId()));
		userDetail.setOauthId(userInfo.getOauthId());
		userDetail.setTenantId(user.getTenantId());
		userDetail.setName(user.getName());
		userDetail.setRealName(user.getRealName());
		userDetail.setAccount(user.getAccount());
		userDetail.setPassword(user.getPassword());
		userDetail.setDeptId(user.getDeptId());
		userDetail.setPostId(user.getPostId());
		userDetail.setRoleId(user.getRoleId());
		userDetail.setRoleName(Func.join(userInfo.getRoles()));
		userDetail.setAvatar(user.getAvatar());
		userDetail.setAuthorities(userInfo.getRoles());
		userDetail.setDetail(userInfo.getDetail());
		return userDetail;
	}

}
