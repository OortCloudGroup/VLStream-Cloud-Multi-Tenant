package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.AnnotationLabel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Annotation label entity class View entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnnotationLabelVO extends AnnotationLabel {
	@Serial
	private static final long serialVersionUID = 1L;

}
