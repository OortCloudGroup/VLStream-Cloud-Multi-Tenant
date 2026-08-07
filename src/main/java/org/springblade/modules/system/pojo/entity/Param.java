package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_param")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Param对象")
public class Param extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 参数名
	 */
	@Schema(description = "参数名")
	private String paramName;

	/**
	 * 参数键
	 */
	@Schema(description = "参数键")
	private String paramKey;

	/**
	 * 参数值
	 */
	@Schema(description = "参数值")
	private String paramValue;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;


}
