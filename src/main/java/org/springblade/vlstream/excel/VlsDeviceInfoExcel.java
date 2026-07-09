package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Equipment information table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsDeviceInfoExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Device name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Device name")
	private String deviceName;
	/**
	 * Device number, unique identifier
	 */
	@ColumnWidth(20)
	@ExcelProperty("Device number, unique identifier")
	private String deviceId;
	/**
	 * Video stream address (RTSP/HTTPwait)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Video stream address (RTSP/HTTPwait)")
	private String streamUrl;
	/**
	 * Device image path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Device image path")
	private String imagePath;
	/**
	 * Device location/Installation location
	 */
	@ColumnWidth(20)
	@ExcelProperty("Device location/Installation location")
	private String position;
	/**
	 * Device type (Dome machine monitoring、PTZ、Bolt, etc.)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Device type (Dome machine monitoring、PTZ、Bolt, etc.)")
	private String deviceType;
	/**
	 * Equipment brand (Hikvision、Dahua、Uniview etc.)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Equipment brand (Hikvision、Dahua、Uniview etc.)")
	private String brand;
	/**
	 * Device model
	 */
	@ColumnWidth(20)
	@ExcelProperty("Device model")
	private String model;
	/**
	 * IPaddress (supportIPv4andIPv6)
	 */
	@ColumnWidth(20)
	@ExcelProperty("IPaddress (supportIPv4andIPv6)")
	private String ipAddress;
	/**
	 * port number
	 */
	@ColumnWidth(20)
	@ExcelProperty("port number")
	private Integer port;
	/**
	 * Login username
	 */
	@ColumnWidth(20)
	@ExcelProperty("Login username")
	private String username;
	/**
	 * Login password
	 */
	@ColumnWidth(20)
	@ExcelProperty("Login password")
	private String password;
	/**
	 * Device description information
	 */
	@ColumnWidth(20)
	@ExcelProperty("Device description information")
	private String description;
	/**
	 * Remarks
	 */
	@ColumnWidth(20)
	@ExcelProperty("Remarks")
	private String remark;
	/**
	 * Location description
	 */
	@ColumnWidth(20)
	@ExcelProperty("Location description")
	private String location;
	/**
	 * longitude
	 */
	@ColumnWidth(20)
	@ExcelProperty("longitude")
	private BigDecimal longitude;
	/**
	 * latitude
	 */
	@ColumnWidth(20)
	@ExcelProperty("latitude")
	private BigDecimal latitude;
	/**
	 * Manufacturer
	 */
	@ColumnWidth(20)
	@ExcelProperty("Manufacturer")
	private String manufacturer;
	/**
	 * Video stream path
	 */
	@ColumnWidth(20)
	@ExcelProperty("Video stream path")
	private String streamPath;
	/**
	 * height position(high altitude/ground/underground/other)
	 */
	@ColumnWidth(20)
	@ExcelProperty("height position(high altitude/ground/underground/other)")
	private String heightPosition;
	/**
	 * Detailed address
	 */
	@ColumnWidth(20)
	@ExcelProperty("Detailed address")
	private String address;
	/**
	 * Zoning options(JSONFormat)
	 */
	@ColumnWidth(20)
	@ExcelProperty("Zoning options(JSONFormat)")
	private String region;
	/**
	 * Creator
	 */
	@ColumnWidth(20)
	@ExcelProperty("Creator")
	private String creator;
	/**
	 * RTSPaddress
	 */
	@ColumnWidth(20)
	@ExcelProperty("RTSPaddress")
	private String rtspUrl;
	/**
	 * device tag
	 */
	@ColumnWidth(20)
	@ExcelProperty("device tag")
	private String tag;
	/**
	 * algorithmid
	 */
	@ColumnWidth(20)
	@ExcelProperty("algorithmid")
	private String algorithmId;

}
