package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Device tag association table ExcelEntity class
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
	 * equipmentID, associationdevice_info.id
	 */
	@ColumnWidth(20)
	@ExcelProperty("equipmentID, associationdevice_info.id")
	private Long deviceId;
	/**
	 * LabelID, associationtag_management.id
	 */
	@ColumnWidth(20)
	@ExcelProperty("LabelID, associationtag_management.id")
	private Long tagId;

}
