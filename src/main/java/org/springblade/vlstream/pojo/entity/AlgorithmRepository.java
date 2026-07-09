package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;
import org.springblade.vlstream.enums.AlgorithmRepositoryTypeEnum;

import java.io.Serial;

/**
 * Algorithm warehouse table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_algorithm_repository")
@Schema(description = "VlsAlgorithmRepositoryEntityobject")
@EqualsAndHashCode(callSuper = true)
public class AlgorithmRepository extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Algorithm warehouse name
	 */
	@Schema(description = "Algorithm warehouse name")
	private String name;
	/**
	 * Number of algorithms owned
	 */
	@Schema(description = "Number of algorithms owned")
	private Integer algorithmCount;
	/**
	 * Warehouse type
	 */
	@Schema(description = "Warehouse type")
	private AlgorithmRepositoryTypeEnum repositoryType;
	/**
	 * Remark
	 */
	@Schema(description = "Remark")
	private String remark;

}
