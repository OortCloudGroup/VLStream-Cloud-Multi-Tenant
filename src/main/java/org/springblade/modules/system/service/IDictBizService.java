package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.mp.support.Query;
import org.springblade.modules.system.pojo.entity.DictBiz;
import org.springblade.modules.system.pojo.vo.DictBizVO;

import java.util.List;
import java.util.Map;

/**
 * Service category
 *
 * @author Chill
 */
public interface IDictBizService extends IService<DictBiz> {

	/**
	 * tree structure
	 *
	 * @return
	 */
	List<DictBizVO> tree();

	/**
	 * tree structure
	 *
	 * @return
	 */
	List<DictBizVO> parentTree();

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
	List<DictBiz> getList(String code);

	/**
	 * Get the dictionary table corresponding to Chinese
	 *
	 * @param tenantId tenantID
	 * @param code     dictionary number
	 * @param dictKey  Dictionary number
	 * @return
	 */
	String getValue(String tenantId, String code, String dictKey);

	/**
	 * Get dictionary table
	 *
	 * @param tenantId tenantID
	 * @param code     dictionary number
	 * @return
	 */
	List<DictBiz> getList(String tenantId, String code);

	/**
	 * Add or modify
	 *
	 * @param dict
	 * @return
	 */
	boolean submit(DictBiz dict);

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
	IPage<DictBizVO> parentList(Map<String, Object> dict, Query query);

	/**
	 * sublist
	 *
	 * @param dict
	 * @param parentId
	 * @return
	 */
	List<DictBizVO> childList(Map<String, Object> dict, Long parentId);

}
