package org.springblade.common.cache;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.system.pojo.entity.User;
import org.springblade.modules.system.service.IUserService;

import static org.springblade.core.cache.constant.CacheConstant.USER_CACHE;
import static org.springblade.core.launch.constant.FlowConstant.TASK_USR_PREFIX;

/**
 * System cache
 *
 * @author Chill
 */
public class UserCache {
	private static final String USER_CACHE_ID = "user:id:";
	private static final String USER_CACHE_ACCOUNT = "user:account:";

	private static final IUserService userService;

	static {
		userService = SpringUtil.getBean(IUserService.class);
	}

	/**
	 * According to task useridGet user information
	 *
	 * @param taskUserId task userid
	 * @return
	 */
	public static User getUserByTaskUser(String taskUserId) {
		Long userId = Func.toLong(StringUtil.removePrefix(taskUserId, TASK_USR_PREFIX));
		return getUser(userId);
	}

	/**
	 * Get user
	 *
	 * @param userId userid
	 * @return
	 */
	public static User getUser(Long userId) {
		return CacheUtil.get(USER_CACHE, USER_CACHE_ID, userId, () -> userService.getById(userId));
	}

	/**
	 * Get user
	 *
	 * @param tenantId tenantid
	 * @param account  Account name
	 * @return
	 */
	public static User getUser(String tenantId, String account) {
		return CacheUtil.get(USER_CACHE, USER_CACHE_ACCOUNT, tenantId + StringPool.DASH + account, () -> userService.userByAccount(tenantId, account));
	}

}
