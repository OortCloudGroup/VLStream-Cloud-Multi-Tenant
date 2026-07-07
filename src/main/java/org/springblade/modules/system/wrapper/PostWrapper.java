package org.springblade.modules.system.wrapper;

import org.springblade.common.cache.DictCache;
import org.springblade.common.enums.DictEnum;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.pojo.entity.Post;
import org.springblade.modules.system.pojo.vo.PostVO;

import java.util.Objects;

/**
 * 岗位表包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class PostWrapper extends BaseEntityWrapper<Post, PostVO> {

	public static PostWrapper build() {
		return new PostWrapper();
	}

	@Override
	public PostVO entityVO(Post post) {
		PostVO postVO = Objects.requireNonNull(BeanUtil.copyProperties(post, PostVO.class));
		String categoryName = DictCache.getValue(DictEnum.POST_CATEGORY, post.getCategory());
		postVO.setCategoryName(categoryName);
		return postVO;
	}

}
