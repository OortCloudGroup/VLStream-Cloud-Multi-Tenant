package org.springblade.modules.system.rule.tenant;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.common.cache.ParamCache;
import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.Menu;
import org.springblade.modules.system.pojo.entity.RoleMenu;
import org.springblade.modules.system.rule.context.TenantContext;
import org.springblade.modules.system.service.IMenuService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springblade.common.constant.TenantConstant.ACCOUNT_MENU_CODE_KEY;
import static org.springblade.common.constant.TenantConstant.MENU_CODES;
import static org.springblade.modules.system.rule.constant.TenantRuleConstant.TENANT_ROLE_MENU_RULE;

/**
 * Tenant role menu construction
 *
 * @author Chill
 */
@LiteRuleComponent(id = TENANT_ROLE_MENU_RULE, name = "Tenant role menu construction")
public class TenantRoleMenuRule extends RuleComponent {
	@Override
	public void process() {
		// Get context
		TenantContext contextBean = this.getContextBean(TenantContext.class);
		IMenuService menuService = contextBean.getMenuService();
		// Role menu permissions corresponding to the new tenant
		LinkedList<Menu> userMenus = new LinkedList<>();
		// Get the default menu collection of parameter configuration, comma separated
		List<String> menuCodes = Func.toStrList(ParamCache.getValue(ACCOUNT_MENU_CODE_KEY));
		List<Menu> menus = getMenus(menuService, (!menuCodes.isEmpty() ? menuCodes : MENU_CODES), userMenus);
		List<RoleMenu> roleMenuList = new ArrayList<>();
		menus.forEach(menu -> {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setMenuId(menu.getId());
			roleMenuList.add(roleMenu);
		});
		// Set context
		contextBean.setRoleMenuList(roleMenuList);
	}

	private List<Menu> getMenus(IMenuService menuService, List<String> codes, LinkedList<Menu> menus) {
		codes.forEach(code -> {
			Menu menu = menuService.getOne(Wrappers.<Menu>query().lambda().eq(Menu::getCode, code).eq(Menu::getIsDeleted, BladeConstant.DB_NOT_DELETED));
			if (menu != null) {
				menus.add(menu);
				recursionMenu(menuService, menu.getId(), menus);
			}
		});
		return menus;
	}

	private void recursionMenu(IMenuService menuService, Long parentId, LinkedList<Menu> menus) {
		List<Menu> menuList = menuService.list(Wrappers.<Menu>query().lambda().eq(Menu::getParentId, parentId).eq(Menu::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		menus.addAll(menuList);
		menuList.forEach(menu -> recursionMenu(menuService, menu.getId(), menus));
	}
}
