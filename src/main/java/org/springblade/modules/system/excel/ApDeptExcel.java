package org.springblade.modules.system.excel;


import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Organization chart ExcelEntity class
 *
 * @author Oort
 * @since 2025-08-09
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class ApDeptExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Organizational structureID
	 */
	@ColumnWidth(20)
	@ExcelProperty("Organizational structureID")
	private String deptId;
	/**
	 * Organization code
	 */
	@ColumnWidth(20)
	@ExcelProperty("Organization code")
	private String deptCode;
	/**
	 * Parent OrganizationID
	 */
	@ColumnWidth(20)
	@ExcelProperty("Parent OrganizationID")
	private String parentDeptId;
	/**
	 * Organization type 1:group 2:company 3:department 4:project 0:unknown
	 */
	@ColumnWidth(20)
	@ExcelProperty("Organization type 1:group 2:company 3:department 4:project 0:unknown")
	private Integer deptType;
	/**
	 * Organization name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Organization name")
	private String deptName;
	/**
	 * Organization code hierarchy path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Organization code hierarchy path")
	private String deptCodePath;
	/**
	 * Organization name hierarchical path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Organization name hierarchical path")
	private String deptNamePath;
	/**
	 * organizational level
	 */
	@ColumnWidth(20)
	@ExcelProperty("organizational level")
	private Long deptLevel;
	/**
	 * Department sorting The smaller it is, the closer it is to the front
	 */
	@ColumnWidth(20)
	@ExcelProperty("Department sorting The smaller it is, the closer it is to the front")
	private Integer sort;
	/**
	 * extra data
	 */
	@ColumnWidth(20)
	@ExcelProperty("extra data")
	private String data;
	/**
	 * creation time
	 */
	@ColumnWidth(20)
	@ExcelProperty("creation time")
	private LocalDateTime createdAt;
	/**
	 * Update time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Update time")
	private LocalDateTime updatedAt;
	/**
	 * Remove timestamp
	 */
	@ColumnWidth(20)
	@ExcelProperty("Remove timestamp")
	private Long deletedAt;
	/**
	 * Creator's ID
	 */
	@ColumnWidth(20)
	@ExcelProperty("Creator's ID")
	private String createdBy;
	/**
	 * ID of the last updater
	 */
	@ColumnWidth(20)
	@ExcelProperty("ID of the last updater")
	private String updatedBy;
	/**
	 * Audit table associationid
	 */
	@ColumnWidth(20)
	@ExcelProperty("Audit table associationid")
	private Integer checkId;

}
