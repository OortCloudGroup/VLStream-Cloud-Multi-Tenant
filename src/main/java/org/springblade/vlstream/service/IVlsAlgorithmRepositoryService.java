package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.vlstream.pojo.entity.AlgorithmRepository;
import org.springblade.vlstream.pojo.vo.AlgorithmRepositoryVO;
import org.springblade.vlstream.excel.VlsAlgorithmRepositoryExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;

/**
 * Algorithm warehouse table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsAlgorithmRepositoryService extends BaseService<AlgorithmRepository> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAlgorithmRepository query parameters
	 * @return IPage<VlsAlgorithmRepositoryVO>
	 */
	IPage<AlgorithmRepositoryVO> selectVlsAlgorithmRepositoryPage(IPage<AlgorithmRepositoryVO> page, AlgorithmRepositoryVO vlsAlgorithmRepository);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAlgorithmRepositoryExcel>
	 */
	List<VlsAlgorithmRepositoryExcel> exportVlsAlgorithmRepository(Wrapper<AlgorithmRepository> queryWrapper);

	/**
	 * Paging query algorithm warehouse list
	 *
	 * @param page Paging parameters
	 * @param name Warehouse name(fuzzy query)
	 * @param repositoryType Warehouse type
	 * @param status state
	 * @return Paginated results
	 */
	IPage<AlgorithmRepository> selectRepositoryPage(Page<AlgorithmRepository> page,
													String name,
													String repositoryType,
													String status);

	/**
	 * Query所有enablealgorithmstorehouse
	 *
	 * @return List of enabled algorithm repositories
	 */
	List<AlgorithmRepository> getEnabledRepositories();

	/**
	 * Query algorithm warehouse based on type
	 *
	 * @param repositoryType Warehouse type
	 * @return Algorithm warehouse list
	 */
	List<AlgorithmRepository> getByRepositoryType(String repositoryType);

	/**
	 * Create algorithm warehouse
	 *
	 * @param repository Algorithm warehouse information
	 * @return Is it successful?
	 */
	boolean createRepository(AlgorithmRepository repository);

	/**
	 * Update algorithm repository
	 *
	 * @param repository Algorithm warehouse information
	 * @return Is it successful?
	 */
	boolean updateRepository(AlgorithmRepository repository);

	/**
	 * Delete algorithm repository
	 *
	 * @param id storehouseID
	 * @return Is it successful?
	 */
	boolean deleteRepository(Long id);

	/**
	 * Batch deletion of algorithm warehouse
	 *
	 * @param ids storehouseIDlist
	 * @return Is it successful?
	 */
	boolean batchDeleteRepositories(List<Long> ids);

	/**
	 * Update warehouse status
	 *
	 * @param id storehouseID
	 * @param status new status
	 * @return Is it successful?
	 */
	boolean updateRepositoryStatus(Long id, String status);

	/**
	 * Update warehouse status in batches
	 *
	 * @param ids storehouseIDlist
	 * @param status new status
	 * @return Is it successful?
	 */
	boolean batchUpdateRepositoryStatus(List<Long> ids, String status);

	/**
	 * Statistical algorithm warehouse quantity
	 *
	 * @return Total number of warehouses
	 */
	Long countRepositories();

	/**
	 * Update the number of algorithms in the warehouse
	 *
	 * @param repositoryId storehouseID
	 */
	void updateAlgorithmCount(Long repositoryId);

}
