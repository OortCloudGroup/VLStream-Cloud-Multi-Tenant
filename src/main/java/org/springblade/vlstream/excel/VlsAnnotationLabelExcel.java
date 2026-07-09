package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Annotation label entity class ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsAnnotationLabelExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Associated annotation itemsID
	 */
	@ColumnWidth(20)
	@ExcelProperty("Associated annotation itemsID")
	private Long annotationId;
	/**
	 * Tag name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Tag name")
	private String name;
	/**
	 * Label color(hexadecimal)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Label color(hexadecimal)")
	private String color;
	/**
	 * Tag description
	 */
	@ColumnWidth(20)
	@ExcelProperty("Tag description")
	private String description;
	/**
	 * sort order
	 */
	@ColumnWidth(20)
	@ExcelProperty("sort order")
	private Integer sortOrder;
	/**
	 * Usage statistics
	 */
	@ColumnWidth(20)
	@ExcelProperty("Usage statistics")
	private Integer usageCount;

}
