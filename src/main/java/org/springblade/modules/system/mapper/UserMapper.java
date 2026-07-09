package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.modules.system.excel.UserExcel;
import org.springblade.modules.system.pojo.entity.User;

import java.util.List;

/**
 * Mapper interface
 *
 * @author Chill
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * Custom paging
	 *
	 * @param page
	 * @param user
	 * @param deptIdList
	 * @param tenantId
	 * @return
	 */
	List<User> selectUserPage(IPage<User> page, @Param("user") User user, @Param("deptIdList") List<Long> deptIdList, @Param("tenantId") String tenantId);

	/**
	 * Get user
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	User getUser(String tenantId, String account);

	/**
	 * Get user
	 *
	 * @param tenantId
	 * @param phone
	 * @return
	 */
	User getUserByPhone(String tenantId, String phone);

	/**
	 * Get exported user data
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(@Param("ew") Wrapper<User> queryWrapper);

}
