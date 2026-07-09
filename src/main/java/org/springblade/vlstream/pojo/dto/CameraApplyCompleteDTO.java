package org.springblade.vlstream.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Camera application completion parameters
 */
@Data
public class CameraApplyCompleteDTO {

	@Schema(description = "Application recordID", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "Application recordIDcannot be empty")
	private Long id;

	@Schema(description = "finisher", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "The finisher cannot be empty")
	private String completeUserName;

	@Schema(description = "Final remarks")
	private String completeRemark;
}
