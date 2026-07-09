package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Algorithm warehouse table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsAlgorithmRepositoryExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Algorithm warehouse name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Algorithm warehouse name")
	private String name;
	/**
	 * Number of algorithms owned
	 */
	@ColumnWidth(20)
	@ExcelProperty("Number of algorithms owned")
	private Integer algorithmCount;
	/**
	 * Warehouse type: basic-Basic presets, extended-Expand
	 */
	@ColumnWidth(20)
	@ExcelProperty("Warehouse type: basic-Basic presets, extended-Expand")
	private String repositoryType;
	/**
	 * Remark
	 */
	@ColumnWidth(20)
	@ExcelProperty("Remark")
	private String remark;

}
