package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.AnnotationInstance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 标注实例实体类 数据传输对象实体类
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
