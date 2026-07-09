package org.springblade.job.pojo.dto;

import lombok.Data;
import org.springblade.job.pojo.entity.JobInfo;
import org.springblade.job.pojo.entity.JobServer;
import tech.powerjob.client.PowerJobClient;

/**
 * mission dataDTO
 *
 * @author Chill
 */
@Data
public class JobDTO {

	/**
	 * Task information class
	 */
	private JobInfo jobInfo;

	/**
	 * Task service class
	 */
	private JobServer jobServer;

	/**
	 * Task client class
	 */
	private PowerJobClient powerJobClient;

}
