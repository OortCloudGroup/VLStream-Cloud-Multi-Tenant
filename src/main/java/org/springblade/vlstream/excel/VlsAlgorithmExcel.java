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
 * Algorithm table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsAlgorithmExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Algorithm warehouse to which it belongsID
	 */
	@ColumnWidth(20)
	@ExcelProperty("Algorithm warehouse to which it belongsID")
	private Long repositoryId;
	/**
	 * Algorithm name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Algorithm name")
	private String name;
	/**
	 * Algorithm classification(Target detection algorithm、Instance segmentation algorithm、Image classification algorithm、Key point detection algorithm、Rotating target detection algorithm, etc.)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Algorithm classification(Target detection algorithm、Instance segmentation algorithm、Image classification algorithm、Key point detection algorithm、Rotating target detection algorithm, etc.)")
	private String category;
	/**
	 * Algorithm description
	 */
	@ColumnWidth(20)
	@ExcelProperty("Algorithm description")
	private String description;
	/**
	 * Algorithm pictureURL
	 */
	@ColumnWidth(20)
	@ExcelProperty("Algorithm pictureURL")
	private String imageUrl;
	/**
	 * algorithm version
	 */
	@ColumnWidth(20)
	@ExcelProperty("algorithm version")
	private String version;
	/**
	 * Model format
	 */
	@ColumnWidth(20)
	@ExcelProperty("Model format")
	private String modelFormat;
	/**
	 * Model file path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Model file path")
	private String modelFilePath;
	/**
	 * Algorithm configuration parameters(JSONFormat)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Algorithm configuration parameters(JSONFormat)")
	private String configParams;
	/**
	 * Input format(image、videowait)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Input format(image、videowait)")
	private String inputFormat;
	/**
	 * Output format(bbox、mask、keypointwait)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Output format(bbox、mask、keypointwait)")
	private String outputFormat;
	/**
	 * Accuracy
	 */
	@ColumnWidth(20)
	@ExcelProperty("Accuracy")
	private BigDecimal accuracy;
	/**
	 * processing speed(FPS)
	 */
	@ColumnWidth(20)
	@ExcelProperty("processing speed(FPS)")
	private Integer processingSpeed;
	/**
	 * Memory usage(MB)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Memory usage(MB)")
	private Integer memoryUsage;
	/**
	 * Is it necessaryGPU: 0-no, 1-yes
	 */
	@ColumnWidth(20)
	@ExcelProperty("Is it necessaryGPU: 0-no, 1-yes")
	private Byte gpuRequired;
	/**
	 * Deployment status: ready-ready, deploying-Deploying, deployed-Deployed, failed-fail
	 */
	@ColumnWidth(20)
	@ExcelProperty("Deployment status: ready-ready, deploying-Deploying, deployed-Deployed, failed-fail")
	private String deployStatus;
	/**
	 * Deployment times
	 */
	@ColumnWidth(20)
	@ExcelProperty("Deployment times")
	private Integer deployCount;
	/**
	 * Last deployment time
	 */
	@ColumnWidth(20)
	@ExcelProperty("Last deployment time")
	private LocalDateTime lastDeployTime;

}
