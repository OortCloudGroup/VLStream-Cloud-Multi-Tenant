package org.springblade.vlstream.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springblade.vlstream.pojo.entity.AlgorithmModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 算法模型表 视图实体类
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlgorithmModelVO extends AlgorithmModel {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "模型下载路径")
	private String modelDownloadPath;

}
