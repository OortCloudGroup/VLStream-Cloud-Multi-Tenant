package org.springblade.modules.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.Post;

import java.io.Serial;

/**
 * 岗位表视图实体类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "岗位表")
public class PostVO extends Post {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 岗位分类名
	 */
	private String categoryName;

}
