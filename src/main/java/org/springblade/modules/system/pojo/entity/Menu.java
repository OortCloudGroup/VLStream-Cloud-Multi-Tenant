package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springblade.core.tool.utils.Func;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_menu")
@Schema(description = "Menuobject")
public class Menu implements Serializable {

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
	 * Menu parent primary key
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "Menu parent primary key")
	private Long parentId;

	/**
	 * menu number
	 */
	@Schema(description = "menu number")
	private String code;

	/**
	 * Menu name
	 */
	@Schema(description = "Menu name")
	private String name;

	/**
	 * Menu alias
	 */
	@Schema(description = "Menu alias")
	private String alias;

	/**
	 * Request address
	 */
	@Schema(description = "Request address")
	private String path;

	/**
	 * Menu resources
	 */
	@Schema(description = "Menu resources")
	private String source;

	/**
	 * Component resources
	 */
	@Schema(description = "Component resources")
	private String component;

	/**
	 * sort
	 */
	@Schema(description = "sort")
	private Integer sort;

	/**
	 * Menu type
	 */
	@Schema(description = "Menu type")
	private Integer category;

	/**
	 * Action button type
	 */
	@Schema(description = "Action button type")
	private Integer action;

	/**
	 * Whether to open a new page
	 */
	@Schema(description = "Whether to open a new page")
	private Integer isOpen;

	/**
	 * Remark
	 */
	@Schema(description = "Remark")
	private String remark;

	/**
	 * Has it been deleted?
	 */
	@TableLogic
	@Schema(description = "Has it been deleted?")
	private Integer isDeleted;


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		Menu other = (Menu) obj;
		return Func.equals(this.getId(), other.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, parentId, code);
	}


}
