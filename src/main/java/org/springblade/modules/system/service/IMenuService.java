package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.node.TreeNode;
import org.springblade.core.tool.support.Kv;
import org.springblade.modules.system.pojo.entity.Menu;
import org.springblade.modules.system.pojo.vo.MenuVO;

import java.util.List;
import java.util.Map;

/**
 * Service category
 *
 * @author Chill
 */
public interface IMenuService extends IService<Menu> {

	/**
	 * Lazy loading list
	 *
	 * @param parentId
	 * @param param
	 * @return
	 */
	List<MenuVO> lazyList(Long parentId, Map<String, Object> param);

	/**
	 * Lazy loading menu list
	 *
	 * @param parentId
	 * @param param
	 * @return
	 */
	List<MenuVO> lazyMenuList(Long parentId, Map<String, Object> param);

	/**
	 * Menu tree structure
	 *
	 * @param roleId
	 * @param topMenuId
	 * @return
	 */
	List<MenuVO> routes(String roleId, Long topMenuId);

	/**
	 * Menu tree structure
	 *
	 * @param roleId
	 * @param topMenuId
	 * @return
	 */
	List<MenuVO> routesExt(String roleId, Long topMenuId);

	/**
	 * Button tree structure
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> buttons(String roleId);

	/**
	 * tree structure
	 *
	 * @return
	 */
	List<TreeNode> tree();

	/**
	 * Authorization tree structure
	 *
	 * @param user
	 * @return
	 */
	List<TreeNode> grantTree(BladeUser user);

	/**
	 * Top menu tree structure
	 *
	 * @param user
	 * @return
	 */
	List<TreeNode> grantTopTree(BladeUser user);

	/**
	 * Data permission authorization tree structure
	 *
	 * @param user
	 * @return
	 */
	List<TreeNode> grantDataScopeTree(BladeUser user);

	/**
	 * Interface permission authorization tree structure
	 *
	 * @param user
	 * @return
	 */
	List<TreeNode> grantApiScopeTree(BladeUser user);

	/**
	 * Node selected by default
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> roleTreeKeys(String roleIds);

	/**
	 * Node selected by default
	 *
	 * @param topMenuIds
	 * @return
	 */
	List<String> topTreeKeys(String topMenuIds);

	/**
	 * Node selected by default
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> dataScopeTreeKeys(String roleIds);

	/**
	 * Node selected by default
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> apiScopeTreeKeys(String roleIds);

	/**
	 * Get configured role permissions
	 *
	 * @param user
	 * @return
	 */
	List<Kv> authRoutes(BladeUser user);

	/**
	 * delete menu
	 *
	 * @param ids
	 * @return
	 */
	boolean removeMenu(String ids);

	/**
	 * submit
	 *
	 * @param menu
	 * @return
	 */
	boolean submit(Menu menu);

}
