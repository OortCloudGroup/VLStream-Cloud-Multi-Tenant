package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * 多租户数据源表实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_tenant_datasource")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "多租户数据源表")
public class TenantDatasource extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 数据源类型
	 */
	@Schema(description = "数据源类型")
	private Integer category;
	/**
	 * 名称
	 */
	@Schema(description = "名称")
	private String name;
	/**
	 * 驱动类
	 */
	@Schema(description = "驱动类")
	private String driverClass;
	/**
	 * 连接地址
	 */
	@Schema(description = "连接地址")
	private String url;
	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;
	/**
	 * 密码
	 */
	@Schema(description = "密码")
	private String password;
	/**
	 * 分库分表配置
	 */
	@Schema(description = "分库分表配置")
	private String shardingConfig;
	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;


}
