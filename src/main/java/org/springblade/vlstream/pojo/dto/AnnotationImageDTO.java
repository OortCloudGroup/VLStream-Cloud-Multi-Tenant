package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.AnnotationImage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Label image information table Data transfer object entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnnotationImageDTO extends AnnotationImage {
	@Serial
	private static final long serialVersionUID = 1L;

}
