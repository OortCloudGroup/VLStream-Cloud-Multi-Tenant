package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.AlgorithmRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Algorithm warehouse table Data transfer object entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlgorithmRepositoryDTO extends AlgorithmRepository {
	@Serial
	private static final long serialVersionUID = 1L;

}
