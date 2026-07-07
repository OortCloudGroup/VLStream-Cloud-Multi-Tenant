package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.AnnotationImage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 标注图片信息表 数据传输对象实体类
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
