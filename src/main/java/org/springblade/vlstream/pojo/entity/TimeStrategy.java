package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.util.Map;

/**
 * time strategy table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName(value = "vls_time_strategy", autoResultMap = true)
@Schema(description = "VlsTimeStrategyEntityobject")
@EqualsAndHashCode(callSuper = true)
public class TimeStrategy extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * equipmentID
	 */
	@Schema(description = "equipmentID")
	private String deviceId;
	/**
	 *Time policy configuration
	 */
	@Schema(description = "Time policy configuration")
	@TableField(typeHandler = JacksonTypeHandler.class)
	private Map<String, Object> protectionTime;

}
