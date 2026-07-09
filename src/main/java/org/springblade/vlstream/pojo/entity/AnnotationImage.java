package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;
import org.springblade.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.util.Date;

/**
 * Label image information table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_annotation_image")
@Schema(description = "VlsAnnotationImageEntityobject")
@EqualsAndHashCode(callSuper = true)
public class AnnotationImage extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Label itemsID
	 */
	@Schema(description = "Label itemsID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long annotationId;
	/**
	 * Picture name
	 */
	@Schema(description = "Picture name")
	private String imageName;
	/**
	 * original file name
	 */
	@Schema(description = "original file name")
	private String originalName;
	/**
	 * local storage path
	 */
	@Schema(description = "local storage path")
	private String localPath;
	/**
	 * file size(byte)
	 */
	@Schema(description = "file size(byte)")
	private Long fileSize;
	/**
	 * last modified time
	 */
	@Schema(description = "last modified time")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private Date lastModified;
	/**
	 * Whether it is an imported picture: 0-no, 1-yes
	 */
	@Schema(description = "Whether it is an imported picture: 0-no, 1-yes")
	private Integer isImported;
	/**
	 * Import time
	 */
	@Schema(description = "Import time")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private Date importTime;

}
