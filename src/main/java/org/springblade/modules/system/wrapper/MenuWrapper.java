package org.springblade.modules.system.wrapper;

import org.springblade.common.cache.DictCache;
import org.springblade.common.cache.SysCache;
import org.springblade.common.enums.DictEnum;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.Menu;
import org.springblade.modules.system.pojo.vo.MenuVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Packaging,Returns the fields required by the view layer
 *
 * @author Chill
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {

	public static MenuWrapper build() {
		return new MenuWrapper();
	}

	@Override
	public MenuVO entityVO(Menu menu) {
		MenuVO menuVO = Objects.requireNonNull(BeanUtil.copyProperties(menu, MenuVO.class));
		if (Func.equals(menu.getParentId(), BladeConstant.TOP_PARENT_ID)) {
			menuVO.setParentName(BladeConstant.TOP_PARENT_NAME);
		} else {
			Menu parent = SysCache.getMenu(menu.getParentId());
			menuVO.setParentName(parent.getName());
		}
		String category = DictCache.getValue(DictEnum.MENU_CATEGORY, Func.toInt(menuVO.getCategory()));
		String action = DictCache.getValue(DictEnum.BUTTON_FUNC, Func.toInt(menuVO.getAction()));
		String open = DictCache.getValue(DictEnum.YES_NO, Func.toInt(menuVO.getIsOpen()));
		menuVO.setCategoryName(category);
		menuVO.setActionName(action);
		menuVO.setIsOpenName(open);
		return menuVO;
	}

	public List<MenuVO> listNodeVO(List<Menu> list) {
		List<MenuVO> collect = list.stream().map(menu -> BeanUtil.copyProperties(menu, MenuVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

	public List<MenuVO> listNodeLazyVO(List<MenuVO> list) {
		return ForestNodeMerger.merge(list);
	}

}
