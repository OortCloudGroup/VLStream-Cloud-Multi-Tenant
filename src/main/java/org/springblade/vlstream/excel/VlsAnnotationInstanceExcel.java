package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Label instance entity class ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsAnnotationInstanceExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Associated annotation itemsID
	 */
	@ColumnWidth(20)
	@ExcelProperty("Associated annotation itemsID")
	private Long annotationId;
	/**
	 * LabelID
	 */
	@ColumnWidth(20)
	@ExcelProperty("LabelID")
	private Long labelId;
	/**
	 * pictureid
	 */
	@ColumnWidth(20)
	@ExcelProperty("pictureid")
	private Long imageId;
	/**
	 * Dimension type: rect-rectangle,circle-round,polygon-polygon
	 */
	@ColumnWidth(20)
	@ExcelProperty("Dimension type: rect-rectangle,circle-round,polygon-polygon")
	private String annotationType;
	/**
	 * Label coordinate data(JSONFormat)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Label coordinate data(JSONFormat)")
	private String annotationData;
	/**
	 * Confidence
	 */
	@ColumnWidth(20)
	@ExcelProperty("Confidence")
	private BigDecimal confidence;
	/**
	 * Is it verified
	 */
	@ColumnWidth(20)
	@ExcelProperty("Is it verified")
	private Byte verified;

}
