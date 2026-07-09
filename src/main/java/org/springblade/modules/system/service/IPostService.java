package org.springblade.modules.system.service;

import org.springblade.modules.system.pojo.entity.Post;
import org.springblade.modules.system.pojo.vo.PostVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * Job list Service category
 *
 * @author Chill
 */
public interface IPostService extends BaseService<Post> {

	/**
	 * Custom paging
	 *
	 * @param page
	 * @param post
	 * @return
	 */
	IPage<PostVO> selectPostPage(IPage<PostVO> page, PostVO post);

	/**
	 * Get a jobID
	 *
	 * @param tenantId
	 * @param postNames
	 * @return
	 */
	String getPostIds(String tenantId, String postNames);

	/**
	 * Get a jobID
	 *
	 * @param tenantId
	 * @param postNames
	 * @return
	 */
	String getPostIdsByFuzzy(String tenantId, String postNames);

	/**
	 * Get job title
	 *
	 * @param postIds
	 * @return
	 */
	List<String> getPostNames(String postIds);

}
