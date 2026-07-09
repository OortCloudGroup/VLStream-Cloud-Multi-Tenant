package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.core.tool.node.TreeNode;
import org.springblade.modules.system.pojo.dto.MenuDTO;
import org.springblade.modules.system.pojo.entity.Menu;
import org.springblade.modules.system.pojo.vo.MenuVO;

import java.util.List;
import java.util.Map;

/**
 * Mapper interface
 *
 * @author Chill
 */
public interface MenuMapper extends BaseMapper<Menu> {

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
	 * tree structure
	 *
	 * @return
	 */
	List<TreeNode> tree();

	/**
	 * Authorization tree structure
	 *
	 * @return
	 */
	List<TreeNode> grantTree();

	/**
	 * Authorization tree structure
	 *
	 * @param roleId
	 * @return
	 */
	List<TreeNode> grantTreeByRole(List<Long> roleId);

	/**
	 * Top menu tree structure
	 *
	 * @return
	 */
	List<TreeNode> grantTopTree();

	/**
	 * Top menu tree structure
	 *
	 * @param roleId
	 * @return
	 */
	List<TreeNode> grantTopTreeByRole(List<Long> roleId);

	/**
	 * Data permission authorization tree structure
	 *
	 * @return
	 */
	List<TreeNode> grantDataScopeTree();

	/**
	 * Interface permission authorization tree structure
	 *
	 * @return
	 */
	List<TreeNode> grantApiScopeTree();

	/**
	 * Data permission authorization tree structure
	 *
	 * @param roleId
	 * @return
	 */
	List<TreeNode> grantDataScopeTreeByRole(List<Long> roleId);

	/**
	 * Interface permission authorization tree structure
	 *
	 * @param roleId
	 * @return
	 */
	List<TreeNode> grantApiScopeTreeByRole(List<Long> roleId);

	/**
	 * All menus
	 *
	 * @return
	 */
	List<Menu> allMenu();

	/**
	 * Permission configuration menu
	 *
	 * @param roleId
	 * @param topMenuId
	 * @return
	 */
	List<Menu> roleMenu(List<Long> roleId, Long topMenuId);

	/**
	 * Permission configuration menu
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> roleMenuByRoleId(List<Long> roleId);

	/**
	 * Permission configuration menu
	 *
	 * @param topMenuId
	 * @return
	 */
	List<Menu> roleMenuByTopMenuId(Long topMenuId);

	/**
	 * All menus
	 *
	 * @return
	 */
	List<Menu> allMenuExt();

	/**
	 * Permission configuration menu
	 *
	 * @param roleId
	 * @param topMenuId
	 * @return
	 */
	List<Menu> roleMenuExt(List<Long> roleId, Long topMenuId);

	/**
	 * Menu tree structure
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> routes(List<Long> roleId);

	/**
	 * Button tree structure
	 *
	 * @return
	 */
	List<Menu> allButtons();

	/**
	 * Button tree structure
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> buttons(List<Long> roleId);

	/**
	 * Get configured role permissions
	 *
	 * @param roleIds
	 * @return
	 */
	List<MenuDTO> authRoutes(List<Long> roleIds);
}
