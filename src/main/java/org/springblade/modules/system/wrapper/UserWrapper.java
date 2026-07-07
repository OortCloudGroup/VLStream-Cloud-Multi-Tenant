package org.springblade.modules.system.wrapper;

import org.springblade.common.cache.DictCache;
import org.springblade.common.cache.SysCache;
import org.springblade.common.enums.DictEnum;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.pojo.entity.User;
import org.springblade.modules.system.pojo.vo.UserVO;

import java.util.List;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
		if (userVO == null) {
			return null;
		}
		Tenant tenant = SysCache.getTenant(user.getTenantId());
		if (tenant != null) {
			userVO.setTenantName(tenant.getTenantName());
		}
		if (StringUtil.isNotBlank(user.getRoleId()) && !StringUtil.equals(user.getRoleId(), StringPool.MINUS_ONE)) {
			List<String> roleName = SysCache.getRoleNames(user.getRoleId());
			userVO.setRoleName(Func.join(roleName));
		} else {
			userVO.setRoleId(StringPool.EMPTY);
			userVO.setRoleName("暂未分配");
		}
		if (StringUtil.isNotBlank(user.getDeptId()) && !StringUtil.equals(user.getDeptId(), StringPool.MINUS_ONE)) {
			List<String> deptName = SysCache.getDeptNames(user.getDeptId());
			userVO.setDeptName(Func.join(deptName));
		} else {
			userVO.setDeptId(StringPool.EMPTY);
			userVO.setDeptName("暂未分配");
		}
		if (StringUtil.isNotBlank(user.getPostId()) && !StringUtil.equals(user.getPostId(), StringPool.MINUS_ONE)) {
			List<String> postName = SysCache.getPostNames(user.getPostId());
			userVO.setPostName(Func.join(postName));
		} else {
			userVO.setPostId(StringPool.EMPTY);
			userVO.setPostName("暂未分配");
		}
		userVO.setSexName(DictCache.getValue(DictEnum.SEX, user.getSex()));
		userVO.setUserTypeName(DictCache.getValue(DictEnum.USER_TYPE, user.getUserType()));
		return userVO;
	}

}
