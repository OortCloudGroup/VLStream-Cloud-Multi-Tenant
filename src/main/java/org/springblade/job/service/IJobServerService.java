package org.springblade.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.job.pojo.entity.JobServer;
import org.springblade.job.pojo.vo.JobServerVO;

/**
 * task service table Service category
 *
 * @author Oort
 */
public interface IJobServerService extends BaseService<JobServer> {
	/**
	 * Custom paging
	 *
	 * @param page
	 * @param jobServer
	 * @return
	 */
	IPage<JobServerVO> selectJobServerPage(IPage<JobServerVO> page, JobServerVO jobServer);

	/**
	 * Save and sync
	 *
	 * @param jobServer
	 * @return
	 */
	Boolean submitAndSync(JobServer jobServer);

	/**
	 * Sync data
	 *
	 * @param jobServer
	 * @return
	 */
	Boolean sync(JobServer jobServer);

}
