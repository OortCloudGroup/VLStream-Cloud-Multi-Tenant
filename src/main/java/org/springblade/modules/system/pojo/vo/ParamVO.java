package org.springblade.modules.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.modules.system.pojo.entity.Param;

import java.io.Serial;

/**
 * View entity class
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "ParamVOobject")
public class ParamVO extends Param {
	@Serial
	private static final long serialVersionUID = 1L;

}
