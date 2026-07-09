package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Tag management table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsTagManagementExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Tag name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Tag name")
	private String tagName;
	/**
	 * Tag categories: own-private label, public-public tags
	 */
	@ColumnWidth(20)
	@ExcelProperty("Tag categories: own-private label, public-public tags")
	private String categoryType;
	/**
	 * Hierarchy: 1-Tag type, 2-specific tags
	 */
	@ColumnWidth(20)
	@ExcelProperty("Hierarchy: 1-Tag type, 2-specific tags")
	private Byte level;
	/**
	 * parentID, level=1time isNULL, level=2is the label typeID
	 */
	@ColumnWidth(20)
	@ExcelProperty("parentID, level=1time isNULL, level=2is the label typeID")
	private Long parentId;
	/**
	 * sort order
	 */
	@ColumnWidth(20)
	@ExcelProperty("sort order")
	private Integer sortOrder;
	/**
	 * Label color
	 */
	@ColumnWidth(20)
	@ExcelProperty("Label color")
	private String tagColor;
	/**
	 * label icon
	 */
	@ColumnWidth(20)
	@ExcelProperty("label icon")
	private String tagIcon;
	/**
	 * Tag description
	 */
	@ColumnWidth(20)
	@ExcelProperty("Tag description")
	private String description;
	/**
	 * Whether to enable: 1-enable, 0-Disable
	 */
	@ColumnWidth(20)
	@ExcelProperty("Whether to enable: 1-enable, 0-Disable")
	private Byte isActive;
	/**
	 * Number of uses
	 */
	@ColumnWidth(20)
	@ExcelProperty("Number of uses")
	private Integer usageCount;

}
