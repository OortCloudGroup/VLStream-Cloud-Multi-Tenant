package org.springblade.vlstream.pojo.entity;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springblade.core.tenant.mp.TenantEntity;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Algorithm layout table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_algorithm_orchestration")
@Schema(description = "VlsAlgorithmOrchestrationEntityobject")
@EqualsAndHashCode(callSuper = true)
public class AlgorithmOrchestration extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * arrangement name
	 */
	@Schema(description = "arrangement name")
	private String orchestrationName;
	/**
	 * 编排describe
	 */
	@Schema(description = "编排describe")
	private String orchestrationDesc;
	/**
	 * Trigger type: realtime-real time,scheduled-timing,manual-Manual
	 */
	@Schema(description = "Trigger type: realtime-real time,scheduled-timing,manual-Manual")
	private String triggerType;
	/**
	 * execution mode: serial-serial,parallel-parallel
	 */
	@Schema(description = "execution mode: serial-serial,parallel-parallel")
	private String executeMode;
	/**
	 * Algorithm step configuration
	 */
	@Schema(description = "Algorithm step configuration")
	private String algorithmSteps;
	/**
	 * Enter configuration
	 */
	@Schema(description = "Enter configuration")
	private String inputConfig;
	/**
	 * 输出Configuration
	 */
	@Schema(description = "输出Configuration")
	private String outputConfig;
	/**
	 * Number of associated devices
	 */
	@Schema(description = "Number of associated devices")
	private Integer deviceCount;
	/**
	 * Number of runs
	 */
	@Schema(description = "Number of runs")
	private Integer runCount;
	/**
	 * state: active-active,inactive-inactive,draft-draft
	 */
	@Schema(description = "state: active-active,inactive-inactive,draft-draft")
	private String orchestrationStatus;
	/**
	 * Last running time
	 */
	@Schema(description = "Last running time")
	private LocalDateTime lastRunTime;
	/**
	 * average running time(Second)
	 */
	@Schema(description = "average running time(Second)")
	private Integer avgRunTime;
	/**
	 * success rate
	 */
	@Schema(description = "success rate")
	private BigDecimal successRate;

}
