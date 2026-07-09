package org.springblade.vlstream.mapper;

import org.springblade.vlstream.pojo.entity.AnalysisRequest;
import org.springblade.vlstream.pojo.vo.AnalysisRequestVO;
import org.springblade.vlstream.excel.VlsAnalysisRequestExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Intelligent analysis request form Mapper interface
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsAnalysisRequestMapper extends BaseMapper<AnalysisRequest> {

	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAnalysisRequest query parameters
	 * @return List<VlsAnalysisRequestVO>
	 */
	List<AnalysisRequestVO> selectVlsAnalysisRequestPage(IPage page, AnalysisRequestVO vlsAnalysisRequest);

	/**
	 * Get export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAnalysisRequestExcel>
	 */
	List<VlsAnalysisRequestExcel> exportVlsAnalysisRequest(@Param("ew") Wrapper<AnalysisRequest> queryWrapper);

}
