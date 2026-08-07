package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * 设备标签关联表 Excel实体类
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsDeviceTagRelationExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 设备ID，关联device_info.id
	 */
	@ColumnWidth(20)
	@ExcelProperty("设备ID，关联device_info.id")
	private Long deviceId;
	/**
	 * 标签ID，关联tag_management.id
	 */
	@ColumnWidth(20)
	@ExcelProperty("标签ID，关联tag_management.id")
	private Long tagId;

}
