package org.springblade.vlstream.excel;


import lombok.Data;

import java.io.Serializable;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import java.io.Serial;


/**
 * Remote server configuration table ExcelEntity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@ColumnWidth(25)
@HeadRowHeight(20)
@ContentRowHeight(18)
public class VlsRemoteServersExcel implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Server name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Server name")
	private String serverName;
	/**
	 * serverIPaddress
	 */
	@ColumnWidth(20)
	@ExcelProperty("serverIPaddress")
	private String serverIp;
	/**
	 * SSHport
	 */
	@ColumnWidth(20)
	@ExcelProperty("SSHport")
	private Integer serverPort;
	/**
	 * username
	 */
	@ColumnWidth(20)
	@ExcelProperty("username")
	private String username;
	/**
	 * password(encryption)
	 */
	@ColumnWidth(20)
	@ExcelProperty("password(encryption)")
	private String password;
	/**
	 * Condaenvironment name
	 */
	@ColumnWidth(20)
	@ExcelProperty("Condaenvironment name")
	private String condaEnv;
	/**
	 * working directory
	 */
	@ColumnWidth(20)
	@ExcelProperty("working directory")
	private String workDir;

}
