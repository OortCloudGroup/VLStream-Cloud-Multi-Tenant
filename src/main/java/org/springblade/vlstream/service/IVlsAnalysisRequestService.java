package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springblade.vlstream.pojo.entity.AnalysisRequest;
import org.springblade.vlstream.pojo.vo.AnalysisRequestVO;
import org.springblade.vlstream.excel.VlsAnalysisRequestExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;

/**
 * Intelligent analysis request form Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsAnalysisRequestService extends BaseService<AnalysisRequest> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAnalysisRequest query parameters
	 * @return IPage<VlsAnalysisRequestVO>
	 */
	IPage<AnalysisRequestVO> selectVlsAnalysisRequestPage(IPage<AnalysisRequestVO> page, AnalysisRequestVO vlsAnalysisRequest);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAnalysisRequestExcel>
	 */
	List<VlsAnalysisRequestExcel> exportVlsAnalysisRequest(Wrapper<AnalysisRequest> queryWrapper);

}
