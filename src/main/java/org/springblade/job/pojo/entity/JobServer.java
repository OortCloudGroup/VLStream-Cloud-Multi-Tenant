package org.springblade.job.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * task service table Entity class
 *
 * @author Oort
 */
@Data
@TableName("blade_job_server")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "task service table")
public class JobServer extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Task service name
	 */
	@Schema(description = "Task service name")
	private String jobServerName;
	/**
	 * Task server address
	 */
	@Schema(description = "Task server address")
	private String jobServerUrl;
	/**
	 * Task application name
	 */
	@Schema(description = "Task application name")
	private String jobAppName;
	/**
	 * Task application password
	 */
	@Schema(description = "Task application password")
	private String jobAppPassword;
	/**
	 * Task notes
	 */
	@Schema(description = "Task notes")
	private String jobRemark;

}
