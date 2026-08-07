package org.springblade.modules.system.service;

import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.system.pojo.entity.TopMenu;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 顶部菜单表 服务类
 *
 * @author Oort
 */
public interface ITopMenuService extends BaseService<TopMenu> {

	/**
	 * 顶部菜单配置
	 *
	 * @param topMenuIds 顶部菜单id集合
	 * @param menuIds    菜单id集合
	 * @return 是否成功
	 */
	boolean grant(@NotEmpty List<Long> topMenuIds, @NotEmpty List<Long> menuIds);

}
