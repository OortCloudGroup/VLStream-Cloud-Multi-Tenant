package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Label image information table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsAnnotationImageExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Label itemsID
	 */
	@ColumnWidth(20)
	@ExcelProperty("Label itemsID")
	private Long annotationId;
	/**
	 * Picture name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Picture name")
	private String imageName;
	/**
	 * original file name
	 */
	@ColumnWidth(20)
	@ExcelProperty("original file name")
	private String originalName;
	/**
	 * local storage path
	 */
	@ColumnWidth(20)
	@ExcelProperty("local storage path")
	private String localPath;
	/**
	 * file size(byte)
	 */
	@ColumnWidth(20)
	@ExcelProperty("file size(byte)")
	private Long fileSize;
	/**
	 * last modified time
	 */
	@ColumnWidth(20)
	@ExcelProperty("last modified time")
	private LocalDateTime lastModified;
	/**
	 * Whether it is an imported picture: 0-no, 1-yes
	 */
	@ColumnWidth(20)
	@ExcelProperty("Whether it is an imported picture: 0-no, 1-yes")
	private Byte isImported;
	/**
	 * Import time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Import time")
	private LocalDateTime importTime;

}
