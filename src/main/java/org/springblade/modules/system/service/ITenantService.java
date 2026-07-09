package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.system.pojo.entity.Tenant;

import java.util.Date;
import java.util.List;

/**
 * Service category
 *
 * @author Chill
 */
public interface ITenantService extends BaseService<Tenant> {

	/**
	 * Custom paging
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant);

	/**
	 * Get entity based on tenant number
	 *
	 * @param tenantId
	 * @return
	 */
	Tenant getByTenantId(String tenantId);

	/**
	 * New
	 *
	 * @param tenant
	 * @return
	 */
	boolean submitTenant(Tenant tenant);

	/**
	 * Delete to recycle bin
	 *
	 * @param ids
	 * @return
	 */
	boolean recycleTenant(List<Long> ids);

	/**
	 * Restore from Recycle Bin
	 *
	 * @param ids
	 * @return
	 */
	boolean passTenant(List<Long> ids);

	/**
	 * Delete from recycle bin
	 *
	 * @param ids
	 * @return
	 */
	boolean removeTenant(List<Long> ids);

	/**
	 * Configure tenant authorization
	 *
	 * @param accountNumber
	 * @param expireTime
	 * @param ids
	 * @return
	 */
	boolean setting(Integer accountNumber, Date expireTime, String ids);

}
