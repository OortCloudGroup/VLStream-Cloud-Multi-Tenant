package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Organization chart Entity class
 *
 * @author BladeX
 * @since 2025-08-09
 */
@Data
@TableName("ap_dept")
@Schema(description = "ApDeptEntityobject")
public class ApDeptEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Organizational structureID
	 */
	@Schema(description = "Organizational structureID")
	@TableId(value = "dept_id", type = IdType.ASSIGN_ID)
	private String deptId;
	/**
	 * tenantID
	 */
	@Schema(description = "tenantID")
	private String tenantId;
	/**
	 * Organization code
	 */
	@Schema(description = "Organization code")
	private String deptCode;
	/**
	 * Parent OrganizationID
	 */
	@Schema(description = "Parent OrganizationID")
	private String parentDeptId;
	/**
	 * Organization type 1:group 2:company 3:department 4:project 0:unknown
	 */
	@Schema(description = "Organization type 1:group 2:company 3:department 4:project 0:unknown")
	private Integer deptType;
	/**
	 * Organization name
	 */
	@Schema(description = "Organization name")
	private String deptName;
	/**
	 * Organization code hierarchy path
	 */
	@Schema(description = "Organization code hierarchy path")
	private String deptCodePath;
	/**
	 * Organization name hierarchical path
	 */
	@Schema(description = "Organization name hierarchical path")
	private String deptNamePath;
	/**
	 * organizational level
	 */
	@Schema(description = "organizational level")
	private Long deptLevel;
	/**
	 * Department sorting The smaller it is, the closer it is to the front
	 */
	@Schema(description = "Department sorting The smaller it is, the closer it is to the front")
	private Integer sort;
	/**
	 * extra data
	 */
	@Schema(description = "extra data")
	private String data;
	/**
	 * creation time
	 */
	@Schema(description = "creation time")
	private LocalDateTime createdAt;
	/**
	 * Update time
	 */
	@Schema(description = "Update time")
	private LocalDateTime updatedAt;
	/**
	 * Remove timestamp
	 */
	@Schema(description = "Remove timestamp")
	private Long deletedAt;
	/**
	 * Creator's ID
	 */
	@Schema(description = "Creator's ID")
	private String createdBy;
	/**
	 * ID of the last updater
	 */
	@Schema(description = "ID of the last updater")
	private String updatedBy;
	/**
	 * Audit table associationid
	 */
	@Schema(description = "Audit table associationid")
	private Integer checkId;

}
