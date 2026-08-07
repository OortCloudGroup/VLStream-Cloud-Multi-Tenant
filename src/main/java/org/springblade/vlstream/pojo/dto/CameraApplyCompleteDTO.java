package org.springblade.vlstream.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 摄像头申请完结参数
 */
@Data
public class CameraApplyCompleteDTO {

	@Schema(description = "申请记录ID", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "申请记录ID不能为空")
	private Long id;

	@Schema(description = "完结人", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "完结人不能为空")
	private String completeUserName;

	@Schema(description = "完结备注")
	private String completeRemark;
}
