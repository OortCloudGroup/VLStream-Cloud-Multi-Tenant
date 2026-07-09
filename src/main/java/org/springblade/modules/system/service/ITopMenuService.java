package org.springblade.modules.system.service;

import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.system.pojo.entity.TopMenu;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * top menu table Service category
 *
 * @author Oort
 */
public interface ITopMenuService extends BaseService<TopMenu> {

	/**
	 * Top menu configuration
	 *
	 * @param topMenuIds top menuidgather
	 * @param menuIds    menuidgather
	 * @return Is it successful?
	 */
	boolean grant(@NotEmpty List<Long> topMenuIds, @NotEmpty List<Long> menuIds);

}
