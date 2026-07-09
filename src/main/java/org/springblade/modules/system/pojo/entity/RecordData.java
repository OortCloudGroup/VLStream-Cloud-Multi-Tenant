package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Data audit table Entity class
 *
 * @author Oort
 */
@Data
@TableName("blade_record_data")
@Schema(description = "RecordDataobject")
public class RecordData implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * primary key
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "primary key")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * ServeID
	 */
	@Schema(description = "ServeID")
	private String serviceId;
	/**
	 * Server name
	 */
	@Schema(description = "Server name")
	private String serverHost;
	/**
	 * serverIPaddress
	 */
	@Schema(description = "serverIPaddress")
	private String serverIp;
	/**
	 * Server environment
	 */
	@Schema(description = "Server environment")
	private String env;
	/**
	 * Audit level
	 */
	@Schema(description = "Audit level")
	private String recordLevel;
	/**
	 * Operation mode
	 */
	@Schema(description = "Operation mode")
	private String method;
	/**
	 * askURI
	 */
	@Schema(description = "askURI")
	private String requestUri;
	/**
	 * user agent
	 */
	@Schema(description = "user agent")
	private String userAgent;
	/**
	 * operateIPaddress
	 */
	@Schema(description = "operateIPaddress")
	private String remoteIp;
	/**
	 * Operation type
	 */
	@Schema(description = "Operation type")
	private String operation;
	/**
	 * Data table name
	 */
	@Schema(description = "Data table name")
	private String tableName;
	/**
	 * Pre-operation parameters
	 */
	@Schema(description = "Pre-operation parameters")
	private String oldData;
	/**
	 * Parameters after operation
	 */
	@Schema(description = "Parameters after operation")
	private String newData;
	/**
	 * audit message
	 */
	@Schema(description = "audit message")
	private String recordMessage;
	/**
	 * Audit results
	 */
	@Schema(description = "Audit results")
	private String recordResult;
	/**
	 * Recording time
	 */
	@Schema(description = "Recording time")
	private String recordCost;
	/**
	 * Record time
	 */
	@Schema(description = "Record time")
	private LocalDateTime recordTime;
	/**
	 * Recorder
	 */
	@Schema(description = "Recorder")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long recordUser;

	/**
	 * business status
	 */
	@Schema(description = "business status")
	private Integer status;

	/**
	 * Has it been deleted?
	 */
	@TableLogic
	@Schema(description = "Has it been deleted?")
	private Integer isDeleted;

}
