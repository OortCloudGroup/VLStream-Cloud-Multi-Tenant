package org.springblade.vlstream.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Camera application submission parameters
 */
@Data
public class CameraApplySubmitDTO {

	@Schema(description = "Device primary keyID", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "Device primary keyIDcannot be empty")
	private Long deviceInfoId;

	@Schema(description = "Reason for application", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "Application reason cannot be empty")
	private String applyReason;

	@Schema(description = "Application notes")
	private String applyRemark;

	@Schema(description = "applicant", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "Applicant cannot be empty")
	private String applyUserName;
}
