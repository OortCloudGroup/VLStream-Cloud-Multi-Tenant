package org.springblade.modules.system.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.Post;

import java.io.Serial;

/**
 * Position table data transfer object entity class
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostDTO extends Post {
	@Serial
	private static final long serialVersionUID = 1L;

}
