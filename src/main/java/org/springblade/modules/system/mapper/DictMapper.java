package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.system.pojo.entity.Dict;
import org.springblade.modules.system.pojo.vo.DictVO;

import java.util.List;

/**
 * Mapper interface
 *
 * @author Chill
 */
public interface DictMapper extends BaseMapper<Dict> {

	/**
	 * Custom paging
	 *
	 * @param page
	 * @param dict
	 * @return
	 */
	List<DictVO> selectDictPage(IPage page, DictVO dict);

	/**
	 * Get the dictionary table corresponding to Chinese
	 *
	 * @param code    dictionary number
	 * @param dictKey Dictionary number
	 * @return
	 */
	String getValue(String code, String dictKey);

	/**
	 * Get dictionary table
	 *
	 * @param code dictionary number
	 * @return
	 */
	List<Dict> getList(String code);

	/**
	 * Get tree nodes
	 *
	 * @return
	 */
	List<DictVO> tree();

	/**
	 * Get tree nodes
	 *
	 * @return
	 */
	List<DictVO> parentTree();

}
