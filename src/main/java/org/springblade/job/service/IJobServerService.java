package org.springblade.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.job.pojo.entity.JobServer;
import org.springblade.job.pojo.vo.JobServerVO;

/**
 * 任务服务表 服务类
 *
 * @author Oort
 */
public interface IJobServerService extends BaseService<JobServer> {
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param jobServer
	 * @return
	 */
	IPage<JobServerVO> selectJobServerPage(IPage<JobServerVO> page, JobServerVO jobServer);

	/**
	 * 保存并同步
	 *
	 * @param jobServer
	 * @return
	 */
	Boolean submitAndSync(JobServer jobServer);

	/**
	 * 同步数据
	 *
	 * @param jobServer
	 * @return
	 */
	Boolean sync(JobServer jobServer);

}
