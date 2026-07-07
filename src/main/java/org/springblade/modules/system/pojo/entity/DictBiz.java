package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_dict_biz")
@Schema(description = "DictBiz对象")
public class DictBiz implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private String tenantId;

	/**
	 * 父主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "父主键")
	private Long parentId;

	/**
	 * 字典码
	 */
	@Schema(description = "字典码")
	private String code;

	/**
	 * 字典值
	 */
	@Schema(description = "字典值")
	private String dictKey;

	/**
	 * 字典名称
	 */
	@Schema(description = "字典名称")
	private String dictValue;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer sort;

	/**
	 * 字典备注
	 */
	@Schema(description = "字典备注")
	private String remark;

	/**
	 * 是否已封存
	 */
	@Schema(description = "是否已封存")
	private Integer isSealed;

	/**
	 * 业务状态
	 */
	@Schema(description = "业务状态")
	private Integer status;

	/**
	 * 是否已删除
	 */
	@TableLogic
	@Schema(description = "是否已删除")
	private Integer isDeleted;


}
