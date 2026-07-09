package org.springblade.vlstream.pojo.entity;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.tenant.mp.TenantEntity;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Remote server configuration table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_remote_servers")
@Schema(description = "VlsRemoteServersEntityobject")
@EqualsAndHashCode(callSuper = true)
public class RemoteServers extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Server name
	 */
	@Schema(description = "Server name")
	private String serverName;
	/**
	 * serverIPaddress
	 */
	@Schema(description = "serverIPaddress")
	private String serverIp;
	/**
	 * SSHport
	 */
	@Schema(description = "SSHport")
	private Integer serverPort;
	/**
	 * username
	 */
	@Schema(description = "username")
	private String username;
	/**
	 * password(encryption)
	 */
	@Schema(description = "password(encryption)")
	private String password;
	/**
	 * Condaenvironment name
	 */
	@Schema(description = "Condaenvironment name")
	private String condaEnv;
	/**
	 * working directory
	 */
	@Schema(description = "working directory")
	private String workDir;

}
