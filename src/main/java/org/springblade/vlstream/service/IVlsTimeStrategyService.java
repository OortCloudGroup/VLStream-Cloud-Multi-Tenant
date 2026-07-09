package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springblade.vlstream.pojo.entity.TimeStrategy;
import org.springblade.vlstream.pojo.vo.TimeStrategyVO;
import org.springblade.vlstream.excel.VlsTimeStrategyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;

/**
 * time strategy table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsTimeStrategyService extends BaseService<TimeStrategy> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsTimeStrategy query parameters
	 * @return IPage<VlsTimeStrategyVO>
	 */
	IPage<TimeStrategyVO> selectVlsTimeStrategyPage(IPage<TimeStrategyVO> page, TimeStrategyVO vlsTimeStrategy);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsTimeStrategyExcel>
	 */
	List<VlsTimeStrategyExcel> exportVlsTimeStrategy(Wrapper<TimeStrategy> queryWrapper);

	/**
	 * According to deviceIDGet time strategy
	 * @param deviceId equipmentID
	 * @return time strategy
	 */
	TimeStrategy getByDeviceId(String deviceId);

	/**
	 * Save or update time policy
	 * @param timeStrategy time strategy
	 * @return Is it successful?
	 */
	boolean saveOrUpdateStrategy(TimeStrategy timeStrategy);

	/**
	 * According to deviceIDDelete time policy
	 * @param deviceId equipmentID
	 * @return Is it successful?
	 */
	boolean deleteByDeviceId(String deviceId);

}
