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
 * Entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_dict")
@Schema(description = "Dictobject")
public class Dict implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * primary key
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "primary key")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * Parent primary key
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "Parent primary key")
	private Long parentId;

	/**
	 * dictionary code
	 */
	@Schema(description = "dictionary code")
	private String code;

	/**
	 * Dictionary value
	 */
	@Schema(description = "Dictionary value")
	private String dictKey;

	/**
	 * Dictionary name
	 */
	@Schema(description = "Dictionary name")
	private String dictValue;

	/**
	 * sort
	 */
	@Schema(description = "sort")
	private Integer sort;

	/**
	 * Dictionary notes
	 */
	@Schema(description = "Dictionary notes")
	private String remark;

	/**
	 * Has it been archived?
	 */
	@Schema(description = "Has it been archived?")
	private Integer isSealed;

	/**
	 * business status
	 */
	@Schema(description = "business status")
	private Integer status;

	/**
	 * Has it been deleted?
	 */
	@TableLogic
	@Schema(description = "Has it been deleted?")
	private Integer isDeleted;


}
