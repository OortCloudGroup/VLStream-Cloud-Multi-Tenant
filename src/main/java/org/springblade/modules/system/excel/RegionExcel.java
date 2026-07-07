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

	@ExcelProperty("区划编号")
	private String code;

	@ExcelProperty("父区划编号")
	private String parentCode;

	@ExcelProperty("祖区划编号")
	private String ancestors;

	@ExcelProperty("区划名称")
	private String name;

	@ExcelProperty("省级区划编号")
	private String provinceCode;

	@ExcelProperty("省级名称")
	private String provinceName;

	@ExcelProperty("市级区划编号")
	private String cityCode;

	@ExcelProperty("市级名称")
	private String cityName;

	@ExcelProperty("区级区划编号")
	private String districtCode;

	@ExcelProperty("区级名称")
	private String districtName;

	@ExcelProperty("镇级区划编号")
	private String townCode;

	@ExcelProperty("镇级名称")
	private String townName;

	@ExcelProperty("村级区划编号")
	private String villageCode;

	@ExcelProperty("村级名称")
	private String villageName;

	@ExcelProperty("层级")
	private Integer regionLevel;

	@ExcelProperty("排序")
	private Integer sort;

	@ExcelProperty("备注")
	private String remark;

}
