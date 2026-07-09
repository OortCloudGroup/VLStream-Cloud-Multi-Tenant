package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 *  Entity class
 *
 * @author BladeX
 * @since 2025-09-04
 */
@Data
@TableName("oort_dept")
@Schema(description = "OortDeptEntityobject")
@EqualsAndHashCode(callSuper = true)
public class OortDeptEntity extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * UUID
	 */
	@Schema(description = "UUID")
	private String oortUdid;
	/**
	 * Department name
	 */
	@Schema(description = "Department name")
	private String oortDname;
	/**
	 * Department pinyin initials
	 */
	@Schema(description = "Department pinyin initials")
	private String oortDnamefl;
	/**
	 * Department code
	 */
	@Schema(description = "Department code")
	private String oortDcode;
	/**
	 * Parent department name
	 */
	@Schema(description = "Parent department name")
	private String oortPdname;
	/**
	 * Parent department code
	 */
	@Schema(description = "Parent department code")
	private String oortPdcode;
	/**
	 * Department type, 0Other units, 1Establishment unit, 2temporary unit...9test unit
	 */
	@Schema(description = "Department type, 0Other units, 1Establishment unit, 2temporary unit...9test unit")
	private Byte oortDtype;
	/**
	 * Department rank, 0other, 1Section level, 2Division level, 3Bureau level, 4Department level...
	 */
	@Schema(description = "Department rank, 0other, 1Section level, 2Division level, 3Bureau level, 4Department level...")
	private Byte oortDpost;
	/**
	 * Department status, 0Disable, 1normal, ...9delete
	 */
	@Schema(description = "Department status, 0Disable, 1normal, ...9delete")
	private Byte oortStatus;
	/**
	 * Department sorting The smaller it is, the closer it is to the front
	 */
	@Schema(description = "Department sorting The smaller it is, the closer it is to the front")
	private Integer oortDsort;
	/**
	 * Department contact number
	 */
	@Schema(description = "Department contact number")
	private String oortDtel;
	/**
	 * Department address
	 */
	@Schema(description = "Department address")
	private String oortDaddr;
	/**
	 * department level
	 */
	@Schema(description = "department level")
	private Integer oortDlevel;
	/**
	 * Department level path Hierarchical path from the top to the department
	 */
	@Schema(description = "Department level path Hierarchical path from the top to the department")
	private String oortDpath;
	/**
	 * Department coding hierarchical path Coding hierarchy path from the top to your department
	 */
	@Schema(description = "Department coding hierarchical path Coding hierarchy path from the top to your department")
	private String oortDcodepath;
	/**
	 * Last updated by department
	 */
	@Schema(description = "Last updated by department")
	private String oortDuupdate;
	/**
	 * Remark
	 */
	@Schema(description = "Remark")
	private String oortDremark;
	/**
	 * Department creation time
	 */
	@Schema(description = "Department creation time")
	private LocalDateTime oortDtcreate;
	/**
	 * Department update time
	 */
	@Schema(description = "Department update time")
	private LocalDateTime oortDupdate;
	/**
	 * Delete time
	 */
	@Schema(description = "Delete time")
	private Integer oortTdelete;
	/**
	 * Update tag
	 */
	@Schema(description = "Update tag")
	private Integer uptag;
	/**
	 * Logoaddress
	 */
	@Schema(description = "Logoaddress")
	private String oortDeptPhoto;
	/**
	 * Whether to hide in address book
	 */
	@Schema(description = "Whether to hide in address book")
	private Byte ishidden;
	/**
	 * reserved fields1
	 */
	@Schema(description = "reserved fields1")
	private String exfield1;
	/**
	 * reserved fields2
	 */
	@Schema(description = "reserved fields2")
	private String exfield2;
	/**
	 * reserved fields3
	 */
	@Schema(description = "reserved fields3")
	private String exfield3;
	/**
	 * reserved fields4
	 */
	@Schema(description = "reserved fields4")
	private String exfield4;
	/**
	 * reserved fields5
	 */
	@Schema(description = "reserved fields5")
	private String exfield5;
	/**
	 * reserved fields6
	 */
	@Schema(description = "reserved fields6")
	private String exfield6;
	/**
	 * reserved fields7
	 */
	@Schema(description = "reserved fields7")
	private String exfield7;
	/**
	 * reserved fields8
	 */
	@Schema(description = "reserved fields8")
	private String exfield8;
	/**
	 * reserved fields9
	 */
	@Schema(description = "reserved fields9")
	private String exfield9;
	/**
	 * reserved fields10
	 */
	@Schema(description = "reserved fields10")
	private String exfield10;

}
