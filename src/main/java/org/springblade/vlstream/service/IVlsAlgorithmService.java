package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.vlstream.pojo.entity.Algorithm;
import org.springblade.vlstream.pojo.vo.AlgorithmVO;
import org.springblade.vlstream.excel.VlsAlgorithmExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * Algorithm table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsAlgorithmService extends BaseService<Algorithm> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAlgorithm query parameters
	 * @return IPage<VlsAlgorithmVO>
	 */
	IPage<AlgorithmVO> selectVlsAlgorithmPage(IPage<AlgorithmVO> page, AlgorithmVO vlsAlgorithm);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAlgorithmExcel>
	 */
	List<VlsAlgorithmExcel> exportVlsAlgorithm(Wrapper<Algorithm> queryWrapper);

	/**
	 * Paging query algorithm list
	 *
	 * @param page Paging parameters
	 * @param repositoryId storehouseID
	 * @param name Algorithm name(fuzzy query)
	 * @param category Algorithm type
	 * @param deployStatus Deployment status
	 * @return Paginated results
	 */
	IPage<Algorithm> selectAlgorithmPage(Page<Algorithm> page,
										 Long repositoryId,
										 String name,
										 String category,
										 String deployStatus);

	/**
	 * According to warehouseIDQuery algorithm list
	 *
	 * @param repositoryId storehouseID
	 * @return Algorithm list
	 */
	List<Algorithm> getByRepositoryId(Long repositoryId);

	/**
	 * Query algorithm list according to classification
	 *
	 * @param category Algorithm classification
	 * @return Algorithm list
	 */
	List<Algorithm> getByCategory(String category);

	/**
	 * Create algorithm
	 *
	 * @param algorithm Algorithm information
	 * @return Is it successful?
	 */
	boolean createAlgorithm(Algorithm algorithm);

	/**
	 * Update algorithm
	 *
	 * @param algorithm Algorithm information
	 * @return Is it successful?
	 */
	boolean updateAlgorithm(Algorithm algorithm);

	/**
	 * Delete algorithm
	 *
	 * @param id algorithmID
	 * @return Is it successful?
	 */
	boolean deleteAlgorithm(Long id);

	/**
	 * Batch deletion algorithm
	 *
	 * @param ids algorithmIDlist
	 * @return Is it successful?
	 */
	boolean batchDeleteAlgorithms(List<Long> ids);

	/**
	 * Update deployment status
	 *
	 * @param id algorithmID
	 * @param deployStatus New deployment status
	 * @return Is it successful?
	 */
	boolean updateDeployStatus(Long id, String deployStatus);

	/**
	 * Update deployment status in batches
	 *
	 * @param ids algorithmIDlist
	 * @param deployStatus New deployment status
	 * @return Is it successful?
	 */
	boolean batchUpdateDeployStatus(List<Long> ids, String deployStatus);

	/**
	 * Deploy algorithm to device
	 *
	 * @param algorithmId algorithmID
	 * @param deviceIds equipmentIDlist
	 * @return Is it successful?
	 */
	boolean deployAlgorithmToDevices(Long algorithmId, List<Long> deviceIds);

	/**
	 * Count the number of algorithms under a certain warehouse
	 *
	 * @param repositoryId storehouseID
	 * @return Number of algorithms
	 */
	Long countByRepositoryId(Long repositoryId);

	/**
	 * Get algorithm classification statistics
	 *
	 * @return Classification statistics
	 */
	List<Map<String, Object>> getCategoryStatistics();

	/**
	 * Get algorithm type statistics
	 *
	 * @return Type statistics
	 */
	List<Map<String, Object>> getTypeStatistics();

	/**
	 * Get deployment status statistics
	 *
	 * @return Deployment status statistics
	 */
	List<Map<String, Object>> getDeployStatusStatistics();

	/**
	 * Algorithm evaluation
	 *
	 * @param algorithmId algorithmID
	 * @return Assessment results
	 */
	Map<String, Object> evaluateAlgorithm(Long algorithmId);

}
