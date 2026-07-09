package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Administrative division table entity class
 *
 * @author Chill
 */
@Data
@TableName("blade_region")
@Schema(description = "Administrative division table")
public class Region implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Zoning number
	 */
	@TableId(value = "code", type = IdType.INPUT)
	@Schema(description = "Zoning number")
	private String code;
	/**
	 * Parent division number
	 */
	@Schema(description = "Parent division number")
	private String parentCode;
	/**
	 * ancestral zoning number
	 */
	@Schema(description = "ancestral zoning number")
	private String ancestors;
	/**
	 * Zoning name
	 */
	@Schema(description = "Zoning name")
	private String name;
	/**
	 * Provincial division number
	 */
	@Schema(description = "Provincial division number")
	private String provinceCode;
	/**
	 * Provincial name
	 */
	@Schema(description = "Provincial name")
	private String provinceName;
	/**
	 * Municipal division number
	 */
	@Schema(description = "Municipal division number")
	private String cityCode;
	/**
	 * Municipal name
	 */
	@Schema(description = "Municipal name")
	private String cityName;
	/**
	 * District level division number
	 */
	@Schema(description = "District level division number")
	private String districtCode;
	/**
	 * District level name
	 */
	@Schema(description = "District level name")
	private String districtName;
	/**
	 * Town-level division number
	 */
	@Schema(description = "Town-level division number")
	private String townCode;
	/**
	 * Town name
	 */
	@Schema(description = "Town name")
	private String townName;
	/**
	 * Village level zoning number
	 */
	@Schema(description = "Village level zoning number")
	private String villageCode;
	/**
	 * Village name
	 */
	@Schema(description = "Village name")
	private String villageName;
	/**
	 * Hierarchy
	 */
	@Schema(description = "Hierarchy")
	private Integer regionLevel;
	/**
	 * sort
	 */
	@Schema(description = "sort")
	private Integer sort;
	/**
	 * Remark
	 */
	@Schema(description = "Remark")
	private String remark;


}
