package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Algorithm annotation data table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsAlgorithmAnnotationExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Label name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Label name")
	private String annotationName;
	/**
	 * Dimension type: object_detection-Object detection,image_classification-Image classification,instance_segmentation-Instance splitting,semantic_segmentation-Semantic segmentation
	 */
	@ColumnWidth(20)
	@ExcelProperty("Dimension type: object_detection-Object detection,image_classification-Image classification,instance_segmentation-Instance splitting,semantic_segmentation-Semantic segmentation")
	private String annotationType;
	/**
	 * Dataset path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Dataset path")
	private String datasetPath;
	/**
	 * total quantity
	 */
	@ColumnWidth(20)
	@ExcelProperty("total quantity")
	private Integer totalCount;
	/**
	 * Quantity marked
	 */
	@ColumnWidth(20)
	@ExcelProperty("Quantity marked")
	private Integer annotatedCount;
	/**
	 * Annotation status: none-Not labeled,partial-Partial annotation,completed-Complete annotation
	 */
	@ColumnWidth(20)
	@ExcelProperty("Annotation status: none-Not labeled,partial-Partial annotation,completed-Complete annotation")
	private String annotationStatus;
	/**
	 * Mark progress percentage
	 */
	@ColumnWidth(20)
	@ExcelProperty("Mark progress percentage")
	private Integer progress;
	/**
	 * Labeling rules
	 */
	@ColumnWidth(20)
	@ExcelProperty("Labeling rules")
	private String annotationRules;
	/**
	 * Remark
	 */
	@ColumnWidth(20)
	@ExcelProperty("Remark")
	private String remark;

}
