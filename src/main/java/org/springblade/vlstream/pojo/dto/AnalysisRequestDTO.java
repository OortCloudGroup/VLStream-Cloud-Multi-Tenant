package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.AnalysisRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 智能分析请求表 数据传输对象实体类
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnalysisRequestDTO extends AnalysisRequest {
	@Serial
	private static final long serialVersionUID = 1L;

}
