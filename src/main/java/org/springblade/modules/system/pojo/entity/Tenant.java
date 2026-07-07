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
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_tenant")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Tenant对象")
public class Tenant extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private String tenantId;
	/**
	 * 租户名称
	 */
	@Schema(description = "租户名称")
	private String tenantName;
	/**
	 * 域名地址
	 */
	@Schema(description = "域名地址")
	private String domainUrl;
	/**
	 * 系统背景
	 */
	@Schema(description = "系统背景")
	private String backgroundUrl;
	/**
	 * 联系人
	 */
	@Schema(description = "联系人")
	private String linkman;
	/**
	 * 联系电话
	 */
	@Schema(description = "联系电话")
	private String contactNumber;
	/**
	 * 联系地址
	 */
	@Schema(description = "联系地址")
	private String address;
	/**
	 * 账号额度
	 */
	@Schema(description = "账号额度")
	private Integer accountNumber;
	/**
	 * 过期时间
	 */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	@Schema(description = "过期时间")
	private Date expireTime;
	/**
	 * 产品包ID
	 */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	@Schema(description = "产品包ID")
	private Long packageId;
	/**
	 * 数据源ID
	 */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	@Schema(description = "数据源ID")
	private Long datasourceId;
	/**
	 * 授权码
	 */
	@Schema(description = "授权码")
	private String licenseKey;


}
