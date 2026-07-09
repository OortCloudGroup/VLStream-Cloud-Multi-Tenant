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
 * Task information sheet Entity class
 *
 * @author Oort
 */
@Data
@TableName("blade_job_info")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Task information sheet")
public class JobInfo extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * businessID
	 */
	@Schema(description = "businessID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long businessId;

	/**
	 * Task serviceID
	 */
	@Schema(description = "Task serviceID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long jobServerId;
	/**
	 * Task ID, Optional, null Create tasks on behalf of, Otherwise, fill in the tasks that need to be modified ID
	 */
	@Schema(description = "Task ID, Optional, null Create tasks on behalf of, Otherwise, fill in the tasks that need to be modified ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long jobId;
	/**
	 * Task name
	 */
	@Schema(description = "Task name")
	private String jobName;
	/**
	 * Task description
	 */
	@Schema(description = "Task description")
	private String jobDescription;
	/**
	 * Task parameters, Processor#process Method input parameters TaskContext Object jobParams Field
	 */
	@Schema(description = "Task parameters, Processor#process Method input parameters TaskContext Object jobParams Field")
	private String jobParams;
	/**
	 * time expression type, enumeration value
	 */
	@Schema(description = "time expression type, enumeration value")
	private Integer timeExpressionType;
	/**
	 * time expression, Fill in the type by timeExpressionType Decide, for example CRON 需要填写 CRON expression
	 */
	@Schema(description = "time expression, Fill in the type by timeExpressionType Decide, for example CRON 需要填写 CRON expression")
	private String timeExpression;
	/**
	 * execution type, enumeration value
	 */
	@Schema(description = "execution type, enumeration value")
	private Integer executeType;
	/**
	 * Processor type, enumeration value
	 */
	@Schema(description = "Processor type, enumeration value")
	private Integer processorType;
	/**
	 * Processor parameters, Fill in the type by processorType Decide, likeJava The processor needs to fill in the fully qualified class name, like: com.github.kfcfans.oms.processors.demo.MapReduceProcessorDemo
	 */
	@Schema(description = "Processor parameters, Fill in the type by processorType Decide, likeJava The processor needs to fill in the fully qualified class name, like: com.github.kfcfans.oms.processors.demo.MapReduceProcessorDemo")
	private String processorInfo;
	/**
	 * Maximum number of instances, The number of simultaneous executions of this task(Tasks and instances are like the relationship between classes and objects, Tasks are called instances after they are scheduled for execution.)
	 */
	@Schema(description = "Maximum number of instances, The number of simultaneous executions of this task(Tasks and instances are like the relationship between classes and objects, Tasks are called instances after they are scheduled for execution.)")
	private Integer maxInstanceNum;
	/**
	 * Number of concurrent threads on a single machine, Indicates that during the execution of this instance, eachWorker Number of threads used
	 */
	@Schema(description = "Number of concurrent threads on a single machine, Indicates that during the execution of this instance, eachWorker Number of threads used")
	private Integer concurrency;
	/**
	 * Task instance running time limit, 0 Represents no restrictions, Timeout will be interrupted and judged as execution failure.
	 */
	@Schema(description = "Task instance running time limit, 0 Represents no restrictions, Timeout will be interrupted and judged as execution failure.")
	private Long instanceTimeLimit;
	/**
	 * instanceRetryNum	Number of task instance retries, Retry if entire task fails, costly, Not recommended
	 */
	@Schema(description = "instanceRetryNum	Number of task instance retries, Retry if entire task fails, costly, Not recommended")
	private Integer instanceRetryNum;
	/**
	 * taskRetryNum	Task Number of retries, each child Task Retry alone after failure, The price is small, Recommended
	 */
	@Schema(description = "taskRetryNum	Task Number of retries, each child Task Retry alone after failure, The price is small, Recommended")
	private Integer taskRetryNum;
	/**
	 * minCpuCores	Minimum available CPU Number of cores, CPU The number of available cores is less than this value Worker The task will not be executed, 0 Represents no restrictions
	 */
	@Schema(description = "minCpuCores	Minimum available CPU Number of cores, CPU The number of available cores is less than this value Worker The task will not be executed, 0 Represents no restrictions")
	private BigDecimal minCpuCores;
	/**
	 * Minimum memory size(GB), Available memory is less than this valueWorker The task will not be executed, 0 Represents no restrictions
	 */
	@Schema(description = "Minimum memory size(GB), Available memory is less than this valueWorker The task will not be executed, 0 Represents no restrictions")
	private BigDecimal minMemorySpace;
	/**
	 * Minimum disk size(GB), Available disk space is less than this valueWorker The task will not be executed, 0 Represents no restrictions
	 */
	@Schema(description = "Minimum disk size(GB), Available disk space is less than this valueWorker The task will not be executed, 0 Represents no restrictions")
	private BigDecimal minDiskSpace;
	/**
	 * Specify machine to execute, After setting this parameter, only the machines in the list are allowed to perform the task., Empty means no machine is specified.
	 */
	@Schema(description = "Specify machine to execute, After setting this parameter, only the machines in the list are allowed to perform the task., Empty means no machine is specified.")
	private String designatedWorkers;
	/**
	 * Maximum number of execution machines, Limit the number of machines to be mobilized for execution, 0Represents unlimited
	 */
	@Schema(description = "Maximum number of execution machines, Limit the number of machines to be mobilized for execution, 0Represents unlimited")
	private Integer maxWorkerCount;
	/**
	 * Users who receive alerts ID list
	 */
	@Schema(description = "Users who receive alerts ID list")
	private String notifyUserIds;
	/**
	 * Whether to enable this task, Unenabled tasks will not be scheduled
	 */
	@Schema(description = "Whether to enable this task, Unenabled tasks will not be scheduled")
	private Integer enable;
	/**
	 * Scheduling strategy, enumerate, Currently supports random(RANDOM)and Health first(HEALTH_FIRST)
	 */
	@Schema(description = "Scheduling strategy, enumerate, Currently supports random(RANDOM)and Health first(HEALTH_FIRST)")
	private Integer dispatchStrategy;
	/**
	 * lifecycle	life cycle(reserved, Used to specify the effective time range of scheduled tasks)
	 */
	@Schema(description = "lifecycle	life cycle(reserved, Used to specify the effective time range of scheduled tasks)")
	private String lifecycle;
	/**
	 * error threshold, 0Represents no limit
	 */
	@Schema(description = "error threshold, 0Represents no limit")
	private Integer alertThreshold;
	/**
	 * Statistics window length(s), 0Represents no limit
	 */
	@Schema(description = "Statistics window length(s), 0Represents no limit")
	private Integer statisticWindowLen;
	/**
	 * Silent time window(s), 0Represents no limit
	 */
	@Schema(description = "Silent time window(s), 0Represents no limit")
	private Integer silenceWindowLen;
	/**
	 * Log configuration
	 */
	@Schema(description = "Log configuration")
	private Integer logType;
	/**
	 * Log configuration
	 */
	@Schema(description = "Log level")
	private Integer logLevel;
	/**
	 * extension fields(for developers, for function expansion, powerjob This field will not be used by itself)
	 */
	@Schema(description = "extension fields(for developers, for function expansion, powerjob This field will not be used by itself)")
	private String extra;

}
