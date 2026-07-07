package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.AlgorithmRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 算法仓库表 视图实体类
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlgorithmRepositoryVO extends AlgorithmRepository {
	@Serial
	private static final long serialVersionUID = 1L;

}
