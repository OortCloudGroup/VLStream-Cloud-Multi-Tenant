package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.common.enums.YesNoEnum;
import org.springblade.core.tenant.mp.TenantEntity;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.vlstream.enums.AlgorithmCategoryEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Algorithm table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_algorithm")
@Schema(description = "VlsAlgorithmEntityobject")
@EqualsAndHashCode(callSuper = true)
public class Algorithm extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Algorithm warehouse to which it belongsID
	 */
	@Schema(description = "Algorithm warehouse to which it belongsID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long repositoryId;
	/**
	 * Algorithm name
	 */
	@Schema(description = "Algorithm name")
	private String name;
	/**
	 * Algorithm classification
	 */
	@Schema(description = "Algorithm classification")
	private AlgorithmCategoryEnum category;
	/**
	 * Algorithm description
	 */
	@Schema(description = "Algorithm description")
	private String description;
	/**
	 * Algorithm pictureURL
	 */
	@Schema(description = "Algorithm pictureURL")
	private String imageUrl;
	/**
	 * ptModel file path
	 */
	@Schema(description = "ptModel file path")
	private String ptModelFilePath;
	/**
	 * Model file path
	 */
	@Schema(description = "onnxModel file path")
	private String onnxModelFilePath;
	/**
	 * Algorithm configuration parameters(JSONFormat)
	 */
	@Schema(description = "Algorithm configuration parameters(JSONFormat)")
	private String configParams;
	/**
	 * Input format(image、videowait)
	 */
	@Schema(description = "Input format(image、videowait)")
	private String inputFormat;
	/**
	 * Output format(bbox、mask、keypointwait)
	 */
	@Schema(description = "Output format(bbox、mask、keypointwait)")
	private String outputFormat;
	/**
	 * Is it necessaryGPU: 0-no, 1-yes
	 */
	@Schema(description = "Is it necessaryGPU: 0-no, 1-yes")
	private Integer gpuRequired;
	/**
	 * Whether the algorithm is preset for the system
	 */
	@Schema(description = "Whether the algorithm is preset for the system")
	private YesNoEnum isSystem;

}
