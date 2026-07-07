package org.springblade.modules.system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.log.model.LogApi;

import java.io.Serial;

/**
 * LogApiVO
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogApiVO extends LogApi {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 操作提交的数据
	 */
	@JsonIgnore
	private String params;


}
