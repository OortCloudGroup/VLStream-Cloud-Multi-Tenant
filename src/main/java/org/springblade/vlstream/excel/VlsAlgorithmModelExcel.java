package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Algorithm model table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsAlgorithmModelExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Model name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Model name")
	private String modelName;
	/**
	 * algorithmID
	 */
	@ColumnWidth(20)
	@ExcelProperty("algorithmID")
	private Long algorithmId;
	/**
	 * training tasksID
	 */
	@ColumnWidth(20)
	@ExcelProperty("training tasksID")
	private Long trainingId;
	/**
	 * model version
	 */
	@ColumnWidth(20)
	@ExcelProperty("model version")
	private Integer version;
	/**
	 * Model format: ONNX,PyTorch,TensorFlow
	 */
	@ColumnWidth(20)
	@ExcelProperty("Model format: ONNX,PyTorch,TensorFlow")
	private String modelFormat;
	/**
	 * Model size
	 */
	@ColumnWidth(20)
	@ExcelProperty("Model size")
	private String modelSize;
	/**
	 * Model file path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Model file path")
	private String modelPath;
	/**
	 * Model accuracy
	 */
	@ColumnWidth(20)
	@ExcelProperty("Model accuracy")
	private BigDecimal accuracy;
	/**
	 * Model description
	 */
	@ColumnWidth(20)
	@ExcelProperty("Model description")
	private String description;
	/**
	 * Number of downloads
	 */
	@ColumnWidth(20)
	@ExcelProperty("Number of downloads")
	private Integer downloadCount;
	/**
	 * Deployment times
	 */
	@ColumnWidth(20)
	@ExcelProperty("Deployment times")
	private Integer deployCount;
	/**
	 * Release time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Release time")
	private LocalDateTime publishTime;

}
