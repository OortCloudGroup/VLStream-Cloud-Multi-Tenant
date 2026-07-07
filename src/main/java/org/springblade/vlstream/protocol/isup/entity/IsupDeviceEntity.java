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
@Schema(description = "ISUP设备表")
@EqualsAndHashCode(callSuper = true)
public class IsupDeviceEntity extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "部门ID")
	private Long deptId;

	@Schema(description = "设备ID")
	private String deviceId;

	@Schema(description = "名称")
	private String name;

	@Schema(description = "通道")
	private String channel;

	@Schema(description = "播放路径")
	private String url;

	@Schema(description = "注册信息大小")
	private Integer dwSize;

	@Schema(description = "网络单元类型")
	private Integer dwNetUnitType;

	@Schema(description = "固件版本")
	private String firmwareVersion;

	@Schema(description = "设备IP地址")
	private String ipAddress;

	@Schema(description = "设备端口")
	private Integer port;

	@Schema(description = "设备保留字段")
	private String deviceRes;

	@Schema(description = "设备类型")
	private Integer devType;

	@Schema(description = "制造商标识")
	private Integer manufacture;

	@Schema(description = "用户名")
	private String userName;

	@Schema(description = "密码")
	private String password;

	@Schema(description = "设备序列号")
	private String deviceSerial;

	@Schema(description = "可靠传输标志")
	private Integer reliableTransmission;

	@Schema(description = "WebSocket传输标志")
	private Integer websocketTransmission;

	@Schema(description = "重定向支持")
	private Integer supportRedirect;

	@Schema(description = "设备协议版本")
	private String devProtocolVersion;

	@Schema(description = "SessionKey")
	private String sessionKey;

	@Schema(description = "资源类型")
	private String res;

	@Schema(description = "保留字段")
	private Integer marketType;

	@Schema(description = "用户ID")
	private Integer luserId;

	@Schema(description = "状态")
	@TableField("status")
	private String deviceStatus;

	@Schema(description = "纬度")
	private BigDecimal lat;

	@Schema(description = "经度")
	private BigDecimal lng;

	@Schema(description = "地图定位地址")
	private String addressMap;
}
