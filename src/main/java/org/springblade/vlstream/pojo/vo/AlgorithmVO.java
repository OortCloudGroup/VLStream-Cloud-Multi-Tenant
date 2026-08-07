package org.springblade.vlstream.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springblade.vlstream.pojo.entity.Algorithm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 算法表 视图实体类
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
	 * 算法类型名称
	 */
	@Schema(description = "分类名称")
	private String categoryName;
}
