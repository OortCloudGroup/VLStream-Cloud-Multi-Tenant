package org.springblade.vlstream.pojo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.tenant.mp.TenantEntity;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Annotation label entity class Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_annotation_label")
@Schema(description = "VlsAnnotationLabelEntityobject")
@EqualsAndHashCode(callSuper = true)
public class AnnotationLabel extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Associated annotation itemsID
	 */
	@Schema(description = "Associated annotation itemsID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long annotationId;
	/**
	 * Tag name
	 */
	@Schema(description = "Tag name")
	private String name;
	/**
	 * Label color(hexadecimal)
	 */
	@Schema(description = "Label color(hexadecimal)")
	private String color;
	/**
	 * Tag description
	 */
	@Schema(description = "Tag description")
	private String description;
	/**
	 * sort order
	 */
	@Schema(description = "sort order")
	private Integer sortOrder;
	/**
	 * Usage statistics
	 */
	@Schema(description = "Usage statistics")
	private Integer usageCount;

}
