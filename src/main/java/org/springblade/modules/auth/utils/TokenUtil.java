package org.springblade.modules.auth.utils;

import org.springblade.common.cache.SysCache;
import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2User;
import org.springblade.core.oauth2.service.impl.OAuth2UserDetail;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.User;
import org.springblade.modules.system.pojo.entity.UserInfo;

/**
 * Certification tools
 *
 * @author Chill
 */
public class TokenUtil {

	/**
	 * system user converted toOAuth2Standard user
	 *
	 * @param userInfo User information
	 * @param request  request information
	 * @return OAuth2User
	 */
	public static OAuth2User convertUser(UserInfo userInfo, OAuth2Request request) {
		// Return if emptynull
		if (userInfo == null) {
			return null;
		}
		User user = userInfo.getUser();
		String userDept = request.getUserDept();
		String userRole = request.getUserRole();
		// Separately designated departments
		if (Func.isNotEmpty(userDept) && user.getDeptId().contains(userDept)) {
			user.setDeptId(userDept);
		}
		// Specify roles individually
		if (Func.isNotEmpty(userRole) && user.getRoleId().contains(userRole)) {
			user.setRoleId(userRole);
			userInfo.setRoles(SysCache.getRoleAliases(userRole));
		}
		// buildoauth2Required user information
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
