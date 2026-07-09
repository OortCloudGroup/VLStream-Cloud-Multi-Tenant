package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;
import org.springblade.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.util.Date;

/**
 * Entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_tenant")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Tenantobject")
public class Tenant extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * tenantID
	 */
	@Schema(description = "tenantID")
	private String tenantId;
	/**
	 * Tenant name
	 */
	@Schema(description = "Tenant name")
	private String tenantName;
	/**
	 * Domain name address
	 */
	@Schema(description = "Domain name address")
	private String domainUrl;
	/**
	 * System background
	 */
	@Schema(description = "System background")
	private String backgroundUrl;
	/**
	 * Contact person
	 */
	@Schema(description = "Contact person")
	private String linkman;
	/**
	 * Contact number
	 */
	@Schema(description = "Contact number")
	private String contactNumber;
	/**
	 * Contact address
	 */
	@Schema(description = "Contact address")
	private String address;
	/**
	 * Account limit
	 */
	@Schema(description = "Account limit")
	private Integer accountNumber;
	/**
	 * Expiration time
	 */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	@Schema(description = "Expiration time")
	private Date expireTime;
	/**
	 * product packageID
	 */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	@Schema(description = "product packageID")
	private Long packageId;
	/**
	 * data sourceID
	 */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	@Schema(description = "data sourceID")
	private Long datasourceId;
	/**
	 * Authorization code
	 */
	@Schema(description = "Authorization code")
	private String licenseKey;


}
