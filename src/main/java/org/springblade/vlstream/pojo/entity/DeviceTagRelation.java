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
 * Device tag association table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_device_tag_relation")
@Schema(description = "VlsDeviceTagRelationEntityobject")
@EqualsAndHashCode(callSuper = true)
public class DeviceTagRelation extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * equipmentID, associationdevice_info.id
	 */
	@Schema(description = "equipmentID, associationdevice_info.id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long deviceId;
	/**
	 * LabelID, associationtag_management.id
	 */
	@Schema(description = "LabelID, associationtag_management.id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long tagId;

}
