package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * TopMenuSetting
 *
 * @author Chill
 */
@Data
@TableName("blade_top_menu_setting")
public class TopMenuSetting {

	/**
	 * primary keyid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * top menuid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long topMenuId;

	/**
	 * menuid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long menuId;

}
