package org.springblade.vlstream.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springblade.vlstream.pojo.entity.DeviceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Equipment information table Data transfer object entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceInfoDTO extends DeviceInfo {
	@Serial
	private static final long serialVersionUID = 1L;

	// non-database fields, Used to display related information when querying
	@Schema(description = "Algorithm name", hidden = true)
	@TableField(exist = false)
	private String algorithmName;

	@Schema(description = "Creator name", hidden = true)
	@TableField(exist = false)
	private String createdByName;

	@Schema(description = "Instance status description", hidden = true)
	@TableField(exist = false)
	private String instanceStatusDesc;

	@Schema(description = "Health status description", hidden = true)
	@TableField(exist = false)
	private String healthStatusDesc;

	@Schema(description = "Running time(minute)", hidden = true)
	@TableField(exist = false)
	private Long runtimeMinutes;

}
