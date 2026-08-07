package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.AnnotationImage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 标注图片信息表 视图实体类
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnnotationImageVO extends AnnotationImage {
	@Serial
	private static final long serialVersionUID = 1L;

}
