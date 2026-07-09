package org.springblade.vlstream.protocol.onvif.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;

@Data
@TableName("vls_onvif_device")
@Schema(description = "ONVIFEquipment table")
@EqualsAndHashCode(callSuper = true)
public class OnvifDeviceEntity extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "departmentID")
	private Long deptId;

	@Schema(description = "equipmentIP")
	private String ip;

	@Schema(description = "Device name")
	private String name;

	@Schema(description = "username")
	private String userName;

	@Schema(description = "password")
	private String password;

	@Schema(description = "Default playback address")
	private String url;

	@Schema(description = "Equipment manufacturer")
	private String firm;

	@Schema(description = "Device model")
	private String model;

	@Schema(description = "Firmware version")
	private String firmwareVersion;

	@Schema(description = "Play address collection(JSON)")
	private String streamUris;

	@Schema(description = "latitude")
	private BigDecimal lat;

	@Schema(description = "longitude")
	private BigDecimal lng;

	@Schema(description = "Map location address")
	private String addressMap;
}
