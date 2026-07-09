package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;
import org.springblade.vlstream.enums.AlgorithmAnnotationStatusEnum;

import java.io.Serial;

/**
 * Algorithm annotation data table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_algorithm_annotation")
@Schema(description = "VlsAlgorithmAnnotationEntityobject")
@EqualsAndHashCode(callSuper = true)
public class AlgorithmAnnotation extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Label name
	 */
	@Schema(description = "Label name")
	private String annotationName;
	/**
	 * Dimension type: object_detection-Object detection,image_classification-Image classification,instance_segmentation-Instance splitting,semantic_segmentation-Semantic segmentation
	 */
	@Schema(description = "Dimension type: object_detection-Object detection,image_classification-Image classification,instance_segmentation-Instance splitting,semantic_segmentation-Semantic segmentation")
	private String annotationType;
	/**
	 * Dataset path
	 */
	@Schema(description = "Dataset path")
	private String datasetPath;
	/**
	 * total quantity
	 */
	@Schema(description = "total quantity")
	private Integer totalCount;
	/**
	 * Quantity marked
	 */
	@Schema(description = "Quantity marked")
	private Integer annotatedCount;
	/**
	 * Annotation status
	 */
	@Schema(description = "Annotation status")
	private AlgorithmAnnotationStatusEnum annotationStatus;
	/**
	 * Mark progress percentage
	 */
	@Schema(description = "Mark progress percentage")
	private Integer progress;
	/**
	 * Labeling rules
	 */
	@Schema(description = "Labeling rules")
	private String annotationRules;
	/**
	 * Remark
	 */
	@Schema(description = "Remark")
	private String remark;

}
