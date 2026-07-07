package org.springblade.job.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * 任务服务表 实体类
 *
 * @author Oort
 */
@Data
@TableName("blade_job_server")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "任务服务表")
public class JobServer extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 任务服务名称
	 */
	@Schema(description = "任务服务名称")
	private String jobServerName;
	/**
	 * 任务服务器地址
	 */
	@Schema(description = "任务服务器地址")
	private String jobServerUrl;
	/**
	 * 任务应用名称
	 */
	@Schema(description = "任务应用名称")
	private String jobAppName;
	/**
	 * 任务应用密码
	 */
	@Schema(description = "任务应用密码")
	private String jobAppPassword;
	/**
	 * 任务备注
	 */
	@Schema(description = "任务备注")
	private String jobRemark;

}
