package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.AnnotationInstance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Label instance entity class Data transfer object entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnnotationInstanceDTO extends AnnotationInstance {
	@Serial
	private static final long serialVersionUID = 1L;

}
