package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.mp.support.Query;
import org.springblade.modules.system.pojo.entity.Dict;
import org.springblade.modules.system.pojo.vo.DictVO;

import java.util.List;
import java.util.Map;

/**
 * Service category
 *
 * @author Chill
 */
public interface IDictService extends IService<Dict> {

	/**
	 * Custom paging
	 *
	 * @param page
	 * @param dict
	 * @return
	 */
	IPage<DictVO> selectDictPage(IPage<DictVO> page, DictVO dict);

	/**
	 * tree structure
	 *
	 * @return
	 */
	List<DictVO> tree();

	/**
	 * tree structure
	 *
	 * @return
	 */
	List<DictVO> parentTree();

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
	 * Add or modify
	 *
	 * @param dict
	 * @return
	 */
	boolean submit(Dict dict);

	/**
	 * delete dictionary
	 *
	 * @param ids
	 * @return
	 */
	boolean removeDict(String ids);

	/**
	 * top list
	 *
	 * @param dict
	 * @param query
	 * @return
	 */
	IPage<DictVO> parentList(Map<String, Object> dict, Query query);

	/**
	 * sublist
	 *
	 * @param dict
	 * @param parentId
	 * @return
	 */
	List<DictVO> childList(Map<String, Object> dict, Long parentId);

}
