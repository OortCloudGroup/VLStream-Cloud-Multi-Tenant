package org.springblade.modules.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springblade.modules.system.pojo.entity.RecordData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * Data audit table View entity class
 *
 * @author Oort
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RecordDataVO extends RecordData {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Record account number
	 */
	@Schema(description = "Record account number")
	private String recordUserName;

}
