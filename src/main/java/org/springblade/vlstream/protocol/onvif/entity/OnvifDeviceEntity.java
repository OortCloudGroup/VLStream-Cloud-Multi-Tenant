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
@Schema(description = "ONVIF设备表")
@EqualsAndHashCode(callSuper = true)
public class OnvifDeviceEntity extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "部门ID")
	private Long deptId;

	@Schema(description = "设备IP")
	private String ip;

	@Schema(description = "设备名称")
	private String name;

	@Schema(description = "用户名")
	private String userName;

	@Schema(description = "密码")
	private String password;

	@Schema(description = "默认播放地址")
	private String url;

	@Schema(description = "设备厂商")
	private String firm;

	@Schema(description = "设备型号")
	private String model;

	@Schema(description = "固件版本")
	private String firmwareVersion;

	@Schema(description = "播放地址集合(JSON)")
	private String streamUris;

	@Schema(description = "纬度")
	private BigDecimal lat;

	@Schema(description = "经度")
	private BigDecimal lng;

	@Schema(description = "地图定位地址")
	private String addressMap;
}
