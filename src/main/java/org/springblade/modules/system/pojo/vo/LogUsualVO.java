package org.springblade.modules.system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.log.model.LogUsual;

import java.io.Serial;

/**
 * LogUsualVO
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogUsualVO extends LogUsual {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 操作提交的数据
	 */
	@JsonIgnore
	private String params;

	/**
	 * 日志数据
	 */
	@JsonIgnore
	private String logData;
}
