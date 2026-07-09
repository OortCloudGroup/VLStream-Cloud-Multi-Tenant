package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.system.pojo.entity.RoleMenu;
import org.springblade.modules.system.pojo.vo.RoleMenuVO;

import java.util.List;

/**
 * Mapper interface
 *
 * @author Chill
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

	/**
	 * Custom paging
	 * @param page
	 * @param roleMenu
	 * @return
	 */
	List<RoleMenuVO> selectRoleMenuPage(IPage page, RoleMenuVO roleMenu);

}
