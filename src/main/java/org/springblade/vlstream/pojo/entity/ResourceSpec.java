package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;

/**
 * Resource specification configuration table Entity class
 *
 * @author Oort
 */
@Data
@TableName("vls_resource_spec")
@Schema(description = "ResourceSpecobject")
@EqualsAndHashCode(callSuper = true)
public class ResourceSpec extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "Resource typeID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long resourceTypeId;

	@Schema(description = "Specification name")
	private String specName;

	@Schema(description = "CPUmodel")
	private String cpuModel;

	@Schema(description = "vCPUNumber of cores")
	private Integer vcpu;

	@Schema(description = "Memory(GB)")
	private Integer memoryGb;

	@Schema(description = "GPUdescribe")
	private String gpuDesc;

	@Schema(description = "system disk(GB)")
	private Integer systemDiskGb;

	@Schema(description = "data disk(GB)")
	private Integer dataDiskGb;

	@Schema(description = "Whether to enable: 1-enable, 0-Disable")
	private Integer isActive;

	@Schema(description = "sort order")
	private Integer sortOrder;

	@Schema(description = "Remark")
	private String remark;
}
