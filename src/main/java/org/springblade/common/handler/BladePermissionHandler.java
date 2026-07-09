package org.springblade.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.handler.IPermissionHandler;
import org.springblade.core.secure.provider.PermissionMenu;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.WebUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;
import static org.springblade.core.secure.constant.PermissionConstant.*;

/**
 * Default authorization verification class
 *
 * @author Chill
 */
//If the dynamic data source function is enabled, then add@MasterAnnotate the specified permission database as the main database
//@Master
@AllArgsConstructor
public class BladePermissionHandler implements IPermissionHandler {

	private static final String SCOPE_CACHE_ROLE = "apiScope:role:";

	private static final String SCOPE_CACHE_CODE = "apiScope:code:";

	private static final String SCOPE_CACHE_MENU = "apiScope:menu:";

	private static final String SCOPE_CACHE_ALL_MENU = "apiScope:allMenu:permission";

	private final JdbcTemplate jdbcTemplate;

	@Override
	public boolean permissionAll() {
		HttpServletRequest request = WebUtil.getRequest();
		BladeUser user = AuthUtil.getUser();
		if (request == null || user == null) {
			return false;
		}
		String uri = request.getRequestURI();
		List<String> paths = permissionPath(user.getRoleId());
		if (paths.isEmpty()) {
			return false;
		}
		return paths.stream().anyMatch(uri::contains);
	}

	@Override
	public boolean hasPermission(String permission) {
		HttpServletRequest request = WebUtil.getRequest();
		BladeUser user = AuthUtil.getUser();
		if (request == null || user == null) {
			return false;
		}
		List<String> codes = permissionCode(permission, user.getRoleId());
		return !codes.isEmpty();
	}

	@Override
	public boolean hasMenu(String permission) {
		HttpServletRequest request = WebUtil.getRequest();
		BladeUser user = AuthUtil.getUser();
		if (request == null || user == null) {
			return false;
		}
		if (AuthUtil.isAdministrator()) {
			return true;
		}
		List<String> codes = permissionMenu(permission, user.getRoleId());
		return !codes.isEmpty();
	}

	/**
	 * Get the interface permission address
	 *
	 * @param roleId Roleid
	 * @return permissions
	 */
	private List<String> permissionPath(String roleId) {
		List<String> permissions = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_ROLE, roleId, List.class, Boolean.FALSE);
		if (permissions == null) {
			List<Long> roleIds = Func.toLongList(roleId);
			permissions = jdbcTemplate.queryForList(permissionAllStatement(roleIds.size()), String.class, roleIds.toArray());
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_ROLE, roleId, permissions, Boolean.FALSE);
		}
		return permissions;
	}

	/**
	 * Get interface permission information
	 *
	 * @param permission Permission number
	 * @param roleId     Roleid
	 * @return permissions
	 */
	private List<String> permissionCode(String permission, String roleId) {
		List<String> permissions = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CODE, permission + StringPool.COLON + roleId, List.class, Boolean.FALSE);
		if (permissions == null) {
			List<Object> args = new ArrayList<>(Collections.singletonList(permission));
			List<Long> roleIds = Func.toLongList(roleId);
			args.addAll(roleIds);
			permissions = jdbcTemplate.queryForList(permissionCodeStatement(roleIds.size()), String.class, args.toArray());
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CODE, permission + StringPool.COLON + roleId, permissions, Boolean.FALSE);
		}
		return permissions;
	}

	/**
	 * Get menu permission information
	 *
	 * @param permission menu number
	 * @param roleId     Roleid
	 * @return permissions
	 */
	private List<String> permissionMenu(String permission, String roleId) {
		List<String> permissions = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_MENU, permission + StringPool.COLON + roleId, List.class, Boolean.FALSE);
		if (permissions == null) {
			// Get all menus
			List<PermissionMenu> allMenus = permissionAllMenu();
			// Get character menu
			List<Long> roleIds = Func.toLongList(roleId);
			List<PermissionMenu> roleIdMenus = jdbcTemplate.query(permissionMenuStatement(roleIds.size()), new BeanPropertyRowMapper<>(PermissionMenu.class), roleIds.toArray());
			// Reverse recursive character menu all parents
			List<PermissionMenu> routes = new LinkedList<>(roleIdMenus);
			roleIdMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
			// Get the matching menu permission value
			permissions = routes.stream().map(PermissionMenu::getCode).filter(code -> Func.equals(code, permission)).collect(Collectors.toList());
			// Write cached value
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_MENU, permission + StringPool.COLON + roleId, permissions, Boolean.FALSE);
		}
		return permissions;
	}

	/**
	 * Get all menu permission information
	 */
	private List<PermissionMenu> permissionAllMenu() {
		List<PermissionMenu> permissions = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_ALL_MENU, StringPool.EMPTY, List.class, Boolean.FALSE);
		if (permissions == null) {
			permissions = jdbcTemplate.query(permissionAllMenuStatement(), new BeanPropertyRowMapper<>(PermissionMenu.class));
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_ALL_MENU, StringPool.EMPTY, permissions, Boolean.FALSE);
		}
		return permissions;
	}

	/**
	 * Get menu parent recursively
	 *
	 * @param allMenus All menu collection
	 * @param routes   Menu collection for role assignments
	 * @param roleMenu Current menu
	 */
	private void recursion(List<PermissionMenu> allMenus, List<PermissionMenu> routes, PermissionMenu roleMenu) {
		Optional<PermissionMenu> menu = allMenus.stream().filter(x -> Func.equals(x.getId(), roleMenu.getParentId())).findFirst();
		if (menu.isPresent() && !routes.contains(menu.get())) {
			routes.add(menu.get());
			recursion(allMenus, routes, menu.get());
		}
	}

}
