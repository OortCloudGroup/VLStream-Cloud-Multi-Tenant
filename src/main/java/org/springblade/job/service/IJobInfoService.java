package org.springblade.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.job.pojo.entity.JobInfo;
import org.springblade.job.pojo.vo.JobInfoVO;

import java.util.List;

/**
 * 任务信息表 服务类
 *
 * @author Oort
 */
public interface IJobInfoService extends BaseService<JobInfo> {
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param jobInfo
	 * @return
	 */
	IPage<JobInfoVO> selectJobInfoPage(IPage<JobInfoVO> page, JobInfoVO jobInfo);

	/**
	 * 保存并同步
	 *
	 * @return
	 */
	Boolean submitAndSync(JobInfo jobInfo);

	/**
	 * 删除并同步
	 *
	 * @return
	 */
	Boolean removeAndSync(List<Long> ids);

	/**
	 * 启用禁用服务
	 *
	 * @param id     任务服务ID
	 * @param enable 是否启用
	 * @return
	 */
	Boolean changeServerJob(Long id, Integer enable);

	/**
	 * 运行服务
	 *
	 * @param id 任务服务ID
	 * @return
	 */
	Boolean runServerJob(Long id);

	/**
	 * 数据同步
	 *
	 * @return
	 */
	Boolean sync();

}
