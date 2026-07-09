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
	 * Operate submitted data
	 */
	@JsonIgnore
	private String params;

	/**
	 * stack information
	 */
	@JsonIgnore
	private String stackTrace;

	/**
	 * Exception message
	 */
	@JsonIgnore
	private String message;
}
