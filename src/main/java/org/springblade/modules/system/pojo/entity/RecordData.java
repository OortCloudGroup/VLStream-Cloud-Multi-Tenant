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
 * 数据审计表 实体类
 *
 * @author Oort
 */
@Data
@TableName("blade_record_data")
@Schema(description = "RecordData对象")
public class RecordData implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(description = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 服务ID
	 */
	@Schema(description = "服务ID")
	private String serviceId;
	/**
	 * 服务器名
	 */
	@Schema(description = "服务器名")
	private String serverHost;
	/**
	 * 服务器IP地址
	 */
	@Schema(description = "服务器IP地址")
	private String serverIp;
	/**
	 * 服务器环境
	 */
	@Schema(description = "服务器环境")
	private String env;
	/**
	 * 审计级别
	 */
	@Schema(description = "审计级别")
	private String recordLevel;
	/**
	 * 操作方式
	 */
	@Schema(description = "操作方式")
	private String method;
	/**
	 * 请求URI
	 */
	@Schema(description = "请求URI")
	private String requestUri;
	/**
	 * 用户代理
	 */
	@Schema(description = "用户代理")
	private String userAgent;
	/**
	 * 操作IP地址
	 */
	@Schema(description = "操作IP地址")
	private String remoteIp;
	/**
	 * 操作类型
	 */
	@Schema(description = "操作类型")
	private String operation;
	/**
	 * 数据表名
	 */
	@Schema(description = "数据表名")
	private String tableName;
	/**
	 * 操作前参数
	 */
	@Schema(description = "操作前参数")
	private String oldData;
	/**
	 * 操作后参数
	 */
	@Schema(description = "操作后参数")
	private String newData;
	/**
	 * 审计消息
	 */
	@Schema(description = "审计消息")
	private String recordMessage;
	/**
	 * 审计结果
	 */
	@Schema(description = "审计结果")
	private String recordResult;
	/**
	 * 记录耗时
	 */
	@Schema(description = "记录耗时")
	private String recordCost;
	/**
	 * 记录时间
	 */
	@Schema(description = "记录时间")
	private LocalDateTime recordTime;
	/**
	 * 记录人
	 */
	@Schema(description = "记录人")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long recordUser;

	/**
	 * 业务状态
	 */
	@Schema(description = "业务状态")
	private Integer status;

	/**
	 * 是否已删除
	 */
	@TableLogic
	@Schema(description = "是否已删除")
	private Integer isDeleted;

}
