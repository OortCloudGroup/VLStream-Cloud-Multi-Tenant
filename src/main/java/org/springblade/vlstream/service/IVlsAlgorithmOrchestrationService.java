package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springblade.vlstream.pojo.entity.AlgorithmOrchestration;
import org.springblade.vlstream.pojo.vo.AlgorithmOrchestrationVO;
import org.springblade.vlstream.excel.VlsAlgorithmOrchestrationExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;

/**
 * Algorithm layout table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsAlgorithmOrchestrationService extends BaseService<AlgorithmOrchestration> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAlgorithmOrchestration query parameters
	 * @return IPage<VlsAlgorithmOrchestrationVO>
	 */
	IPage<AlgorithmOrchestrationVO> selectVlsAlgorithmOrchestrationPage(IPage<AlgorithmOrchestrationVO> page, AlgorithmOrchestrationVO vlsAlgorithmOrchestration);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAlgorithmOrchestrationExcel>
	 */
	List<VlsAlgorithmOrchestrationExcel> exportVlsAlgorithmOrchestration(Wrapper<AlgorithmOrchestration> queryWrapper);

}
