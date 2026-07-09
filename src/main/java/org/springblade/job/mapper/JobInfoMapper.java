package org.springblade.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.job.pojo.entity.JobInfo;
import org.springblade.job.pojo.vo.JobInfoVO;

import java.util.List;

/**
 * Task information sheet Mapper interface
 *
 * @author Oort
 */
public interface JobInfoMapper extends BaseMapper<JobInfo> {

	/**
	 * Custom paging
	 *
	 * @param page
	 * @param jobInfo
	 * @return
	 */
	List<JobInfoVO> selectJobInfoPage(IPage page, JobInfoVO jobInfo);

}
