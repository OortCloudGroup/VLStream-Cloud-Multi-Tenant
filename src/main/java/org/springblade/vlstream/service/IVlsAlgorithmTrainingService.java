package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springblade.vlstream.pojo.entity.AlgorithmTraining;
import org.springblade.vlstream.pojo.vo.AlgorithmTrainingVO;
import org.springblade.vlstream.excel.VlsAlgorithmTrainingExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;

/**
 * Algorithm training task list Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsAlgorithmTrainingService extends BaseService<AlgorithmTraining> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAlgorithmTraining query parameters
	 * @return IPage<VlsAlgorithmTrainingVO>
	 */
	IPage<AlgorithmTrainingVO> selectVlsAlgorithmTrainingPage(IPage<AlgorithmTrainingVO> page, AlgorithmTrainingVO vlsAlgorithmTraining);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAlgorithmTrainingExcel>
	 */
	List<VlsAlgorithmTrainingExcel> exportVlsAlgorithmTraining(Wrapper<AlgorithmTraining> queryWrapper);

	/**
	 * Query algorithm training tasks
	 *
	 * @param id Algorithm training task primary key
	 * @return Algorithm training tasks
	 */
	public AlgorithmTraining selectAlgorithmTrainingById(Long id);

	/**
	 * Query algorithm training task list
	 *
	 * @param algorithmTraining Algorithm training tasks
	 * @return Algorithm training task set
	 */
	public List<AlgorithmTraining> selectAlgorithmTrainingList(AlgorithmTraining algorithmTraining);

	/**
	 * Added algorithm training tasks
	 *
	 * @param algorithmTraining Algorithm training tasks
	 * @return result
	 */
	public int insertAlgorithmTraining(AlgorithmTraining algorithmTraining);

	/**
	 * Modify algorithm training tasks
	 *
	 * @param algorithmTraining Algorithm training tasks
	 * @return result
	 */
	public int updateAlgorithmTraining(AlgorithmTraining algorithmTraining);

	/**
	 * Batch deletion of algorithm training tasks
	 *
	 * @param ids Algorithm training task primary key set that needs to be deleted
	 * @return result
	 */
	public int deleteAlgorithmTrainingByIds(Long[] ids);

	/**
	 * Delete algorithm training task information
	 *
	 * @param id Algorithm training task primary key
	 * @return result
	 */
	public int deleteAlgorithmTrainingById(Long id);

}
