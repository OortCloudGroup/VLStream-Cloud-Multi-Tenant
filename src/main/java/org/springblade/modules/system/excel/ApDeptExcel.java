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
 * 组织机构表 Excel实体类
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
	 * 组织机构ID
	 */
	@ColumnWidth(20)
	@ExcelProperty("组织机构ID")
	private String deptId;
	/**
	 * 组织机构编码
	 */
	@ColumnWidth(20)
	@ExcelProperty("组织机构编码")
	private String deptCode;
	/**
	 * 父组织机构ID
	 */
	@ColumnWidth(20)
	@ExcelProperty("父组织机构ID")
	private String parentDeptId;
	/**
	 * 组织机构类型 1:集团 2:公司 3:部门 4:项目 0:未知
	 */
	@ColumnWidth(20)
	@ExcelProperty("组织机构类型 1:集团 2:公司 3:部门 4:项目 0:未知")
	private Integer deptType;
	/**
	 * 组织机构名称
	 */
	@ColumnWidth(20)
	@ExcelProperty("组织机构名称")
	private String deptName;
	/**
	 * 组织机构编码层级路径
	 */
	@ColumnWidth(20)
	@ExcelProperty("组织机构编码层级路径")
	private String deptCodePath;
	/**
	 * 组织机构名称层级路径
	 */
	@ColumnWidth(20)
	@ExcelProperty("组织机构名称层级路径")
	private String deptNamePath;
	/**
	 * 组织机构层级
	 */
	@ColumnWidth(20)
	@ExcelProperty("组织机构层级")
	private Long deptLevel;
	/**
	 * 部门排序 越小越靠前
	 */
	@ColumnWidth(20)
	@ExcelProperty("部门排序 越小越靠前")
	private Integer sort;
	/**
	 * 额外数据
	 */
	@ColumnWidth(20)
	@ExcelProperty("额外数据")
	private String data;
	/**
	 * 创建时间
	 */
	@ColumnWidth(20)
	@ExcelProperty("创建时间")
	private LocalDateTime createdAt;
	/**
	 * 更新时间
	 */
	@ColumnWidth(20)
	@ExcelProperty("更新时间")
	private LocalDateTime updatedAt;
	/**
	 * 删除时间戳
	 */
	@ColumnWidth(20)
	@ExcelProperty("删除时间戳")
	private Long deletedAt;
	/**
	 * 创建者的标识
	 */
	@ColumnWidth(20)
	@ExcelProperty("创建者的标识")
	private String createdBy;
	/**
	 * 最后更新者的标识
	 */
	@ColumnWidth(20)
	@ExcelProperty("最后更新者的标识")
	private String updatedBy;
	/**
	 * 审核表关联id
	 */
	@ColumnWidth(20)
	@ExcelProperty("审核表关联id")
	private Integer checkId;

}
