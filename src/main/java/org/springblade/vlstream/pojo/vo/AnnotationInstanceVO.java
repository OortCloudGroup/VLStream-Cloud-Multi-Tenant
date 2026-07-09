package org.springblade.vlstream.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springblade.vlstream.pojo.entity.AnnotationInstance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Label instance entity class View entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnnotationInstanceVO extends AnnotationInstance {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Picture name")
	private String imageName;

}
