package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;
import org.springblade.vlstream.enums.CameraApplyStatusEnum;

import java.io.Serial;
import java.util.Date;

/**
 * Camera application approval record
 */
@Data
@TableName("vls_camera_apply_record")
@Schema(description = "CameraApplyRecordobject")
@EqualsAndHashCode(callSuper = true)
public class CameraApplyRecord extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Device primary keyID")
	private Long deviceInfoId;

	@Schema(description = "Reason for application")
	private String applyReason;

	@Schema(description = "Application notes")
	private String applyRemark;

	@Schema(description = "applicant")
	private String applyUserName;

	@Schema(description = "Application time")
	private Date applyTime;

	@Schema(description = "Approval status")
	private CameraApplyStatusEnum applyStatus;

	@Schema(description = "Approval comments")
	private String approvalComment;

	@Schema(description = "approver")
	private String approveUserName;

	@Schema(description = "Approval time")
	private Date approveTime;

	@Schema(description = "Final remarks")
	private String completeRemark;

	@Schema(description = "finisher")
	private String completeUserName;

	@Schema(description = "end time")
	private Date completeTime;
}
