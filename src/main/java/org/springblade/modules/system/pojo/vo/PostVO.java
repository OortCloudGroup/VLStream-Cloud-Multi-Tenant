package org.springblade.modules.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.Post;

import java.io.Serial;

/**
 * Position table view entity class
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Job list")
public class PostVO extends Post {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Job classification name
	 */
	private String categoryName;

}
