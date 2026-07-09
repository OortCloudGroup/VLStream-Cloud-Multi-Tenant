package org.springblade.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.job.pojo.entity.JobServer;
import org.springblade.job.pojo.vo.JobServerVO;

import java.util.List;

/**
 * task service table Mapper interface
 *
 * @author Oort
 */
public interface JobServerMapper extends BaseMapper<JobServer> {

	/**
	 * Custom paging
	 *
	 * @param page
	 * @param jobServer
	 * @return
	 */
	List<JobServerVO> selectJobServerPage(IPage page, JobServerVO jobServer);

}
