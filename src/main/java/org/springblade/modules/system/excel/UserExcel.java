package org.springblade.modules.system.excel;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * UserExcel
 *
 * @author Chill
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class UserExcel implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@ColumnWidth(15)
	@ExcelProperty("Tenant number")
	private String tenantId;

	@ExcelIgnore
	@ExcelProperty("User platform")
	private String userType;

	@ColumnWidth(20)
	@ExcelProperty("User platform name")
	private String userTypeName;

	@ColumnWidth(15)
	@ExcelProperty("Account")
	private String account;

	@ColumnWidth(10)
	@ExcelProperty("Nick name")
	private String name;

	@ColumnWidth(10)
	@ExcelProperty("Name")
	private String realName;

	@ExcelProperty("Mail")
	private String email;

	@ColumnWidth(15)
	@ExcelProperty("cell phone")
	private String phone;

	@ExcelIgnore
	@ExcelProperty("RoleID")
	private String roleId;

	@ExcelIgnore
	@ExcelProperty("departmentID")
	private String deptId;

	@ExcelIgnore
	@ExcelProperty("postID")
	private String postId;

	@ExcelProperty("Character name")
	private String roleName;

	@ExcelProperty("Department name")
	private String deptName;

	@ExcelProperty("Job title")
	private String postName;

	@ColumnWidth(20)
	@ExcelProperty("Birthday")
	private Date birthday;

}
