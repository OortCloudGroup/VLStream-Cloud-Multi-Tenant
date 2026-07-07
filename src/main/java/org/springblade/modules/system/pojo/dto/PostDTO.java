package org.springblade.modules.system.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.Post;

import java.io.Serial;

/**
 * 岗位表数据传输对象实体类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostDTO extends Post {
	@Serial
	private static final long serialVersionUID = 1L;

}
