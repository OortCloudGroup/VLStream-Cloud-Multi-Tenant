package org.springblade.modules.system.excel;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * RegionExcel
 *
 * @author Chill
 */
@Data
@ColumnWidth(16)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class RegionExcel implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@ExcelProperty("Zoning number")
	private String code;

	@ExcelProperty("Parent division number")
	private String parentCode;

	@ExcelProperty("ancestral zoning number")
	private String ancestors;

	@ExcelProperty("Zoning name")
	private String name;

	@ExcelProperty("Provincial division number")
	private String provinceCode;

	@ExcelProperty("Provincial name")
	private String provinceName;

	@ExcelProperty("Municipal division number")
	private String cityCode;

	@ExcelProperty("Municipal name")
	private String cityName;

	@ExcelProperty("District level division number")
	private String districtCode;

	@ExcelProperty("District level name")
	private String districtName;

	@ExcelProperty("Town-level division number")
	private String townCode;

	@ExcelProperty("Town name")
	private String townName;

	@ExcelProperty("Village level zoning number")
	private String villageCode;

	@ExcelProperty("Village name")
	private String villageName;

	@ExcelProperty("Hierarchy")
	private Integer regionLevel;

	@ExcelProperty("sort")
	private Integer sort;

	@ExcelProperty("Remark")
	private String remark;

}
