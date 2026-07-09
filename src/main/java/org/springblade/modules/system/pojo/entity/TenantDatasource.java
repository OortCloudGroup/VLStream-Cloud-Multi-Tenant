package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * Multi-tenant data source table entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_tenant_datasource")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Multi-tenant data source table")
public class TenantDatasource extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Data source type
	 */
	@Schema(description = "Data source type")
	private Integer category;
	/**
	 * name
	 */
	@Schema(description = "name")
	private String name;
	/**
	 * Driver class
	 */
	@Schema(description = "Driver class")
	private String driverClass;
	/**
	 * connection address
	 */
	@Schema(description = "connection address")
	private String url;
	/**
	 * username
	 */
	@Schema(description = "username")
	private String username;
	/**
	 * password
	 */
	@Schema(description = "password")
	private String password;
	/**
	 * Sub-database and sub-table configuration
	 */
	@Schema(description = "Sub-database and sub-table configuration")
	private String shardingConfig;
	/**
	 * Remark
	 */
	@Schema(description = "Remark")
	private String remark;


}
