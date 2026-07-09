package org.springblade.modules.system.mapper;

import org.springblade.modules.system.pojo.entity.Post;
import org.springblade.modules.system.pojo.vo.PostVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * Job list Mapper interface
 *
 * @author Chill
 */
public interface PostMapper extends BaseMapper<Post> {

	/**
	 * Custom paging
	 *
	 * @param page
	 * @param post
	 * @return
	 */
	List<PostVO> selectPostPage(IPage page, PostVO post);

	/**
	 * Get job title
	 *
	 * @param ids
	 * @return
	 */
	List<String> getPostNames(Long[] ids);

}
