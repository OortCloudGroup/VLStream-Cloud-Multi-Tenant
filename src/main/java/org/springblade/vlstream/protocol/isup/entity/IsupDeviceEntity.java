package org.springblade.vlstream.protocol.isup.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;

@Data
@TableName("vls_isup_device")
@Schema(description = "ISUPEquipment table")
@EqualsAndHashCode(callSuper = true)
public class IsupDeviceEntity extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "departmentID")
	private Long deptId;

	@Schema(description = "equipmentID")
	private String deviceId;

	@Schema(description = "name")
	private String name;

	@Schema(description = "aisle")
	private String channel;

	@Schema(description = "Play path")
	private String url;

	@Schema(description = "Registration information size")
	private Integer dwSize;

	@Schema(description = "Network element type")
	private Integer dwNetUnitType;

	@Schema(description = "Firmware version")
	private String firmwareVersion;

	@Schema(description = "equipmentIPaddress")
	private String ipAddress;

	@Schema(description = "Device port")
	private Integer port;

	@Schema(description = "Device reserved fields")
	private String deviceRes;

	@Schema(description = "Device type")
	private Integer devType;

	@Schema(description = "Manufacturer's identification")
	private Integer manufacture;

	@Schema(description = "username")
	private String userName;

	@Schema(description = "password")
	private String password;

	@Schema(description = "Device serial number")
	private String deviceSerial;

	@Schema(description = "Reliable transmission flag")
	private Integer reliableTransmission;

	@Schema(description = "WebSockettransfer flag")
	private Integer websocketTransmission;

	@Schema(description = "Redirect support")
	private Integer supportRedirect;

	@Schema(description = "Device protocol version")
	private String devProtocolVersion;

	@Schema(description = "SessionKey")
	private String sessionKey;

	@Schema(description = "Resource type")
	private String res;

	@Schema(description = "reserved fields")
	private Integer marketType;

	@Schema(description = "userID")
	private Integer luserId;

	@Schema(description = "state")
	@TableField("status")
	private String deviceStatus;

	@Schema(description = "latitude")
	private BigDecimal lat;

	@Schema(description = "longitude")
	private BigDecimal lng;

	@Schema(description = "Map location address")
	private String addressMap;
}
