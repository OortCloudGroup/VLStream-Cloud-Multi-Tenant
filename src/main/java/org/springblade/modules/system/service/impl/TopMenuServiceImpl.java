package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.modules.system.pojo.entity.TopMenu;
import org.springblade.modules.system.pojo.entity.TopMenuSetting;
import org.springblade.modules.system.mapper.TopMenuMapper;
import org.springblade.modules.system.service.ITopMenuService;
import org.springblade.modules.system.service.ITopMenuSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * top menu table Service implementation class
 *
 * @author Oort
 */
//@Master
@Service
@AllArgsConstructor
public class TopMenuServiceImpl extends BaseServiceImpl<TopMenuMapper, TopMenu> implements ITopMenuService {

	private final ITopMenuSettingService topMenuSettingService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean grant(@NotEmpty List<Long> topMenuIds, @NotEmpty List<Long> menuIds) {
		// Delete the menu collection of the top menu configuration
		topMenuSettingService.remove(Wrappers.<TopMenuSetting>update().lambda().in(TopMenuSetting::getTopMenuId, topMenuIds));
		// Assembly configuration
		List<TopMenuSetting> menuSettings = new ArrayList<>();
		topMenuIds.forEach(topMenuId -> menuIds.forEach(menuId -> {
			TopMenuSetting menuSetting = new TopMenuSetting();
			menuSetting.setTopMenuId(topMenuId);
			menuSetting.setMenuId(menuId);
			menuSettings.add(menuSetting);
		}));
		// New configuration
		topMenuSettingService.saveBatch(menuSettings);
		return true;
	}
}
