package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * time strategy table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsTimeStrategyExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * equipmentID
	 */
	@ColumnWidth(20)
	@ExcelProperty("equipmentID")
	private String deviceId;
	/**
	 * Strategy type: everyday-every day, weekly-weekly
	 */
	@ColumnWidth(20)
	@ExcelProperty("Strategy type: everyday-every day, weekly-weekly")
	private String strategyType;
	/**
	 * Daily mode time period, JSONarray format: [0,1,2,3]
	 */
	@ColumnWidth(20)
	@ExcelProperty("Daily mode time period, JSONarray format: [0,1,2,3]")
	private String dailyTimes;
	/**
	 * Time period for weekly mode, JSONobject format: {\"monday\":[0,1,2],\"tuesday\":[3,4,5]}
	 */
	@ColumnWidth(20)
	@ExcelProperty("Time period for weekly mode, JSONobject format: {\"monday\":[0,1,2],\"tuesday\":[3,4,5]}")
	private String weeklyTimes;

}
