package org.springblade.vlstream.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Camera application rejection parameters
 */
@Data
public class CameraApplyRejectDTO {

	@Schema(description = "Application recordID", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "Application recordIDcannot be empty")
	private Long id;

	@Schema(description = "approver", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "Approver cannot be empty")
	private String approveUserName;

	@Schema(description = "Reason for rejection", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "Reason for rejection cannot be empty")
	private String approvalComment;
}
