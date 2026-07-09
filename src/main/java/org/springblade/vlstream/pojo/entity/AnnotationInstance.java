package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;
import org.springblade.vlstream.enums.AlgorithmAnnotationTypeEnum;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * Label instance entity class Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_annotation_instance")
@Schema(description = "VlsAnnotationInstanceEntityobject")
@EqualsAndHashCode(callSuper = true)
public class AnnotationInstance extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Associated annotation itemsID
	 */
	@Schema(description = "Associated annotation itemsID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long annotationId;
	/**
	 * LabelID
	 */
	@Schema(description = "LabelID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long labelId;
	/**
	 * pictureid
	 */
	@Schema(description = "pictureid")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long imageId;
	/**
	 * Dimension type
	 */
	@Schema(description = "Dimension type")
	private AlgorithmAnnotationTypeEnum annotationType;
	/**
	 * Label coordinate data(JSONFormat)
	 */
	@Schema(description = "Label coordinate data(JSONFormat)")
	private String annotationData;
	/**
	 * Confidence
	 */
	@Schema(description = "Confidence")
	private BigDecimal confidence;
	/**
	 * Is it verified
	 */
	@Schema(description = "Is it verified")
	private Integer verified;

}
