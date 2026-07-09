package org.springblade.vlstream.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springblade.vlstream.pojo.entity.Algorithm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Algorithm table View entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlgorithmVO extends Algorithm {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Algorithm type name
	 */
	@Schema(description = "Category name")
	private String categoryName;
}
