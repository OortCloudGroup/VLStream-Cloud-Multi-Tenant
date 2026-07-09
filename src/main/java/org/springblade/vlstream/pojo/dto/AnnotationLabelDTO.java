package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.AnnotationLabel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Annotation label entity class Data transfer object entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnnotationLabelDTO extends AnnotationLabel {
	@Serial
	private static final long serialVersionUID = 1L;

}
