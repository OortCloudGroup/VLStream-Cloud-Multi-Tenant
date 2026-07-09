package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * Equipment information table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_device_info")
@Schema(description = "VlsDeviceInfoEntityobject")
@EqualsAndHashCode(callSuper = true)
public class DeviceInfo extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Device name
	 */
	@Schema(description = "Device name")
	private String deviceName;
	/**
	 * Device number, unique identifier
	 */
	@Schema(description = "Device number, unique identifier")
	private String deviceId;
	/**
	 * Video stream address (RTSP/HTTPwait)
	 */
	@Schema(description = "Video stream address (RTSP/HTTPwait)")
	private String streamUrl;
	/**
	 * Device image path
	 */
	@Schema(description = "Device image path")
	private String imagePath;
	/**
	 * Device type (Dome machine monitoring、PTZ、Bolt, etc.)
	 */
	@Schema(description = "Device type (Dome machine monitoring、PTZ、Bolt, etc.)")
	private String deviceType;
	/**
	 * Remarks
	 */
	@Schema(description = "Remarks")
	private String remark;
	/**
	 * longitude
	 */
	@Schema(description = "longitude")
	private BigDecimal longitude;
	/**
	 * latitude
	 */
	@Schema(description = "latitude")
	private BigDecimal latitude;
	/**
	 * height position(high altitude/ground/underground/other)
	 */
	@Schema(description = "height position(high altitude/ground/underground/other)")
	private String heightPosition;
	/**
	 * Detailed address
	 */
	@Schema(description = "Detailed address")
	private String address;
	/**
	 * Zoning options
	 */
	@Schema(description = "Zoning options")
	private String region;
	/**
	 * device tag
	 */
	@Schema(description = "device tag")
	private String tag;
	/**
	 * algorithmid
	 */
	@Schema(description = "algorithmid")
	private String algorithmId;
	/**
	 * push address
	 */
	@Schema(description = "push address")
	private String pushUrl;
	/**
	 * Is it public?: 0-no, 1-yes
	 */
	@Schema(description = "Is it public?: 0-no, 1-yes")
	private Integer isPublic;
}
