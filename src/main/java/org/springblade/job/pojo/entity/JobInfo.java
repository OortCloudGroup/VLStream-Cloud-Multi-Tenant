package org.springblade.job.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 任务信息表 实体类
 *
 * @author Oort
 */
@Data
@TableName("blade_job_info")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "任务信息表")
public class JobInfo extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 业务ID
	 */
	@Schema(description = "业务ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long businessId;

	/**
	 * 任务服务ID
	 */
	@Schema(description = "任务服务ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long jobServerId;
	/**
	 * 任务 ID，可选，null 代表创建任务，否则填写需要修改的任务 ID
	 */
	@Schema(description = "任务 ID，可选，null 代表创建任务，否则填写需要修改的任务 ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long jobId;
	/**
	 * 任务名称
	 */
	@Schema(description = "任务名称")
	private String jobName;
	/**
	 * 任务描述
	 */
	@Schema(description = "任务描述")
	private String jobDescription;
	/**
	 * 任务参数，Processor#process 方法入参 TaskContext 对象的 jobParams 字段
	 */
	@Schema(description = "任务参数，Processor#process 方法入参 TaskContext 对象的 jobParams 字段")
	private String jobParams;
	/**
	 * 时间表达式类型，枚举值
	 */
	@Schema(description = "时间表达式类型，枚举值")
	private Integer timeExpressionType;
	/**
	 * 时间表达式，填写类型由 timeExpressionType 决定，比如 CRON 需要填写 CRON 表达式
	 */
	@Schema(description = "时间表达式，填写类型由 timeExpressionType 决定，比如 CRON 需要填写 CRON 表达式")
	private String timeExpression;
	/**
	 * 执行类型，枚举值
	 */
	@Schema(description = "执行类型，枚举值")
	private Integer executeType;
	/**
	 * 处理器类型，枚举值
	 */
	@Schema(description = "处理器类型，枚举值")
	private Integer processorType;
	/**
	 * 处理器参数，填写类型由 processorType 决定，如Java 处理器需要填写全限定类名，如：com.github.kfcfans.oms.processors.demo.MapReduceProcessorDemo
	 */
	@Schema(description = "处理器参数，填写类型由 processorType 决定，如Java 处理器需要填写全限定类名，如：com.github.kfcfans.oms.processors.demo.MapReduceProcessorDemo")
	private String processorInfo;
	/**
	 * 最大实例数，该任务同时执行的数量（任务和实例就像是类和对象的关系，任务被调度执行后被称为实例）
	 */
	@Schema(description = "最大实例数，该任务同时执行的数量（任务和实例就像是类和对象的关系，任务被调度执行后被称为实例）")
	private Integer maxInstanceNum;
	/**
	 * 单机线程并发数，表示该实例执行过程中每个Worker 使用的线程数量
	 */
	@Schema(description = "单机线程并发数，表示该实例执行过程中每个Worker 使用的线程数量")
	private Integer concurrency;
	/**
	 * 任务实例运行时间限制，0 代表无任何限制，超时会被打断并判定为执行失败
	 */
	@Schema(description = "任务实例运行时间限制，0 代表无任何限制，超时会被打断并判定为执行失败")
	private Long instanceTimeLimit;
	/**
	 * instanceRetryNum	任务实例重试次数，整个任务失败时重试，代价大，不推荐使用
	 */
	@Schema(description = "instanceRetryNum	任务实例重试次数，整个任务失败时重试，代价大，不推荐使用")
	private Integer instanceRetryNum;
	/**
	 * taskRetryNum	Task 重试次数，每个子 Task 失败后单独重试，代价小，推荐使用
	 */
	@Schema(description = "taskRetryNum	Task 重试次数，每个子 Task 失败后单独重试，代价小，推荐使用")
	private Integer taskRetryNum;
	/**
	 * minCpuCores	最小可用 CPU 核心数，CPU 可用核心数小于该值的 Worker 将不会执行该任务，0 代表无任何限制
	 */
	@Schema(description = "minCpuCores	最小可用 CPU 核心数，CPU 可用核心数小于该值的 Worker 将不会执行该任务，0 代表无任何限制")
	private BigDecimal minCpuCores;
	/**
	 * 最小内存大小（GB），可用内存小于该值的Worker 将不会执行该任务，0 代表无任何限制
	 */
	@Schema(description = "最小内存大小（GB），可用内存小于该值的Worker 将不会执行该任务，0 代表无任何限制")
	private BigDecimal minMemorySpace;
	/**
	 * 最小磁盘大小（GB），可用磁盘空间小于该值的Worker 将不会执行该任务，0 代表无任何限制
	 */
	@Schema(description = "最小磁盘大小（GB），可用磁盘空间小于该值的Worker 将不会执行该任务，0 代表无任何限制")
	private BigDecimal minDiskSpace;
	/**
	 * 指定机器执行，设置该参数后只有列表中的机器允许执行该任务，空代表不指定机器
	 */
	@Schema(description = "指定机器执行，设置该参数后只有列表中的机器允许执行该任务，空代表不指定机器")
	private String designatedWorkers;
	/**
	 * 最大执行机器数量，限定调动执行的机器数量，0代表无限制
	 */
	@Schema(description = "最大执行机器数量，限定调动执行的机器数量，0代表无限制")
	private Integer maxWorkerCount;
	/**
	 * 接收报警的用户 ID 列表
	 */
	@Schema(description = "接收报警的用户 ID 列表")
	private String notifyUserIds;
	/**
	 * 是否启用该任务，未启用的任务不会被调度
	 */
	@Schema(description = "是否启用该任务，未启用的任务不会被调度")
	private Integer enable;
	/**
	 * 调度策略，枚举，目前支持随机（RANDOM）和 健康度优先（HEALTH_FIRST）
	 */
	@Schema(description = "调度策略，枚举，目前支持随机（RANDOM）和 健康度优先（HEALTH_FIRST）")
	private Integer dispatchStrategy;
	/**
	 * lifecycle	生命周期（预留，用于指定定时调度任务的生效时间范围）
	 */
	@Schema(description = "lifecycle	生命周期（预留，用于指定定时调度任务的生效时间范围）")
	private String lifecycle;
	/**
	 * 错误阈值，0代表不限制
	 */
	@Schema(description = "错误阈值，0代表不限制")
	private Integer alertThreshold;
	/**
	 * 统计的窗口长度(s)，0代表不限制
	 */
	@Schema(description = "统计的窗口长度(s)，0代表不限制")
	private Integer statisticWindowLen;
	/**
	 * 沉默时间窗口(s)，0代表不限制
	 */
	@Schema(description = "沉默时间窗口(s)，0代表不限制")
	private Integer silenceWindowLen;
	/**
	 * 日志配置
	 */
	@Schema(description = "日志配置")
	private Integer logType;
	/**
	 * 日志配置
	 */
	@Schema(description = "日志级别")
	private Integer logLevel;
	/**
	 * 扩展字段（供开发者使用，用于功能扩展，powerjob 自身不会使用该字段）
	 */
	@Schema(description = "扩展字段（供开发者使用，用于功能扩展，powerjob 自身不会使用该字段）")
	private String extra;

}
