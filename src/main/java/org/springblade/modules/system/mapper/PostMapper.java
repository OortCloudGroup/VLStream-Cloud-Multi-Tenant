package org.springblade.modules.system.mapper;

import org.springblade.modules.system.pojo.entity.Post;
import org.springblade.modules.system.pojo.vo.PostVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 岗位表 Mapper 接口
 *
 * @author Chill
 */
public interface PostMapper extends BaseMapper<Post> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param post
	 * @return
	 */
	List<PostVO> selectPostPage(IPage page, PostVO post);

	/**
	 * 获取岗位名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getPostNames(Long[] ids);

}
