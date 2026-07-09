package org.springblade.vlstream.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Camera application paging query parameters
 */
@Data
public class CameraApplyQueryDTO {

	@Schema(description = "Device primary keyID")
	private Long deviceInfoId;

	@Schema(description = "Application status")
	private String applyStatus;

	@Schema(description = "applicant")
	private String applyUserName;
}
