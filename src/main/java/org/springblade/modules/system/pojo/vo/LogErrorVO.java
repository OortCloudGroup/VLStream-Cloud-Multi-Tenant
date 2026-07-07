package org.springblade.modules.system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.log.model.LogError;

import java.io.Serial;

/**
 * LogErrorVO
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogErrorVO extends LogError {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 操作提交的数据
	 */
	@JsonIgnore
	private String params;

	/**
	 * 堆栈信息
	 */
	@JsonIgnore
	private String stackTrace;

	/**
	 * 异常消息
	 */
	@JsonIgnore
	private String message;
}
