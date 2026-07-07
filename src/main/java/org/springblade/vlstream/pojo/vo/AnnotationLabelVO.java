package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.AnnotationLabel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 标注标签实体类 视图实体类
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
