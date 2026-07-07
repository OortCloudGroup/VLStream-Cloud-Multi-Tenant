package org.springblade.vlstream.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.AlgorithmTraining;

import java.io.Serial;

/**
 * 算法训练任务表 数据传输对象实体类
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlgorithmTrainingDTO extends AlgorithmTraining {
	@Serial
	private static final long serialVersionUID = 1L;

}
