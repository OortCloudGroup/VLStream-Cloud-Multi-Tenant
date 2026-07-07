package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springblade.vlstream.pojo.entity.AnalysisRequest;
import org.springblade.vlstream.pojo.vo.AnalysisRequestVO;
import org.springblade.vlstream.excel.VlsAnalysisRequestExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;

/**
 * 智能分析请求表 服务类
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsAnalysisRequestService extends BaseService<AnalysisRequest> {
	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param vlsAnalysisRequest 查询参数
	 * @return IPage<VlsAnalysisRequestVO>
	 */
	IPage<AnalysisRequestVO> selectVlsAnalysisRequestPage(IPage<AnalysisRequestVO> page, AnalysisRequestVO vlsAnalysisRequest);

	/**
	 * 导出数据
	 *
	 * @param queryWrapper 查询条件
	 * @return List<VlsAnalysisRequestExcel>
	 */
	List<VlsAnalysisRequestExcel> exportVlsAnalysisRequest(Wrapper<AnalysisRequest> queryWrapper);

}
