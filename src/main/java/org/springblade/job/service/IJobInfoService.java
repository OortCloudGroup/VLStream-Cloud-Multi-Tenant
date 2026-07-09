package org.springblade.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.job.pojo.entity.JobInfo;
import org.springblade.job.pojo.vo.JobInfoVO;

import java.util.List;

/**
 * Task information sheet Service category
 *
 * @author Oort
 */
public interface IJobInfoService extends BaseService<JobInfo> {
	/**
	 * Custom paging
	 *
	 * @param page
	 * @param jobInfo
	 * @return
	 */
	IPage<JobInfoVO> selectJobInfoPage(IPage<JobInfoVO> page, JobInfoVO jobInfo);

	/**
	 * Save and sync
	 *
	 * @return
	 */
	Boolean submitAndSync(JobInfo jobInfo);

	/**
	 * Delete and sync
	 *
	 * @return
	 */
	Boolean removeAndSync(List<Long> ids);

	/**
	 * Enable or disable services
	 *
	 * @param id     Task serviceID
	 * @param enable Whether to enable
	 * @return
	 */
	Boolean changeServerJob(Long id, Integer enable);

	/**
	 * Run service
	 *
	 * @param id Task serviceID
	 * @return
	 */
	Boolean runServerJob(Long id);

	/**
	 * Data synchronization
	 *
	 * @return
	 */
	Boolean sync();

}
