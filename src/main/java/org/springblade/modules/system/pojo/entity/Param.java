package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * Entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_param")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Paramobject")
public class Param extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Parameter name
	 */
	@Schema(description = "Parameter name")
	private String paramName;

	/**
	 * parameter key
	 */
	@Schema(description = "parameter key")
	private String paramKey;

	/**
	 * Parameter value
	 */
	@Schema(description = "Parameter value")
	private String paramValue;

	/**
	 * Remark
	 */
	@Schema(description = "Remark")
	private String remark;


}
