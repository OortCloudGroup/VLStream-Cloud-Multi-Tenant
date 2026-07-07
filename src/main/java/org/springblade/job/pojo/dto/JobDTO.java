package org.springblade.job.pojo.dto;

import lombok.Data;
import org.springblade.job.pojo.entity.JobInfo;
import org.springblade.job.pojo.entity.JobServer;
import tech.powerjob.client.PowerJobClient;

/**
 * 任务数据DTO
 *
 * @author Chill
 */
@Data
public class JobDTO {

	/**
	 * 任务信息类
	 */
	private JobInfo jobInfo;

	/**
	 * 任务服务类
	 */
	private JobServer jobServer;

	/**
	 * 任务客户端类
	 */
	private PowerJobClient powerJobClient;

}
