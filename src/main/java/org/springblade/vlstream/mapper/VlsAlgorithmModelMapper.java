package org.springblade.vlstream.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springblade.vlstream.excel.VlsAlgorithmModelExcel;
import org.springblade.vlstream.pojo.dto.AlgorithmModelQueryDTO;
import org.springblade.vlstream.pojo.dto.AlgorithmModelStatisticsDTO;
import org.springblade.vlstream.pojo.entity.AlgorithmModel;
import org.springblade.vlstream.pojo.vo.AlgorithmModelVO;

import java.util.List;

/**
 * Algorithm model table Mapper interface
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsAlgorithmModelMapper extends BaseMapper<AlgorithmModel> {

	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAlgorithmModel query parameters
	 * @return List<VlsAlgorithmModelVO>
	 */
	List<AlgorithmModelVO> selectVlsAlgorithmModelPage(IPage page, AlgorithmModelVO vlsAlgorithmModel);

	/**
	 * Get export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAlgorithmModelExcel>
	 */
	List<VlsAlgorithmModelExcel> exportVlsAlgorithmModel(@Param("ew") Wrapper<AlgorithmModel> queryWrapper);

	/**
	 * Paging query algorithm model list
	 *
	 * @param page Pagination object
	 * @param queryDTO query parameters
	 * @return Algorithm model list
	 */
	IPage<AlgorithmModel> selectModelPage(Page<AlgorithmModel> page, @Param("query") AlgorithmModelQueryDTO queryDTO);

	/**
	 * According to algorithmIDQuery model list
	 *
	 * @param algorithmId algorithmID
	 * @return Algorithm model list
	 */
	List<AlgorithmModel> selectByAlgorithmId(@Param("algorithmId") Long algorithmId);

	/**
	 * According to training tasksIDQuery model list
	 *
	 * @param trainingId training tasksID
	 * @return Algorithm model list
	 */
	List<AlgorithmModel> selectByTrainingId(@Param("trainingId") Long trainingId);

	/**
	 * Query model list based on status
	 *
	 * @param status state
	 * @return Algorithm model list
	 */
	List<AlgorithmModel> selectByStatus(@Param("status") String status);

	/**
	 * Update model status
	 *
	 * @param id ModelID
	 * @param status new status
	 * @return Number of rows affected
	 */
	int updateStatus(@Param("id") Long id, @Param("status") String status);

	/**
	 * Update model download times
	 *
	 * @param id ModelID
	 * @return Number of rows affected
	 */
	int updateDownloadCount(@Param("id") Long id);

	/**
	 * Update model deployment times
	 *
	 * @param id ModelID
	 * @return Number of rows affected
	 */
	int updateDeployCount(@Param("id") Long id);

	/**
	 * Update model status in batches
	 *
	 * @param ids ModelIDlist
	 * @param status new status
	 * @return Number of rows affected
	 */
	int batchUpdateStatus(@Param("ids") List<Long> ids, @Param("status") String status);

	/**
	 * Get model statistics
	 *
	 * @return Statistics
	 */
	AlgorithmModelStatisticsDTO getStatistics();

	/**
	 * Check if model name and version exist
	 *
	 * @param modelName Model name
	 * @param version Version
	 * @param excludeId excludedID(Used when updating)
	 * @return quantity of existence
	 */
	int checkModelNameAndVersion(@Param("modelName") String modelName,
								 @Param("version") Integer version,
								 @Param("excludeId") Long excludeId);

	/**
	 * According to algorithmIDand version query model
	 *
	 * @param algorithmId algorithmID
	 * @param version Version
	 * @return algorithm model
	 */
	AlgorithmModel selectByAlgorithmIdAndVersion(@Param("algorithmId") Long algorithmId,
												 @Param("version") Integer version);

	/**
	 * Get the latest version of the model under the algorithm
	 *
	 * @param algorithmId algorithmID
	 * @return algorithm model
	 */
	AlgorithmModel selectLatestByAlgorithmId(@Param("algorithmId") Long algorithmId);

	/**
	 * Query popular models(Sort by download count)
	 *
	 * @param limit limited quantity
	 * @return Algorithm model list
	 */
	List<AlgorithmModel> selectPopularModels(@Param("limit") Integer limit);

	/**
	 * Query the number of models based on the creator
	 *
	 * @param createdBy CreatorID
	 * @return Number of models
	 */
	Long countByCreatedBy(@Param("createdBy") Long createdBy);

	/**
	 * Get the total size of the algorithm model(Sum of file sizes of all published models)
	 *
	 * @return total size(byte)
	 */
	Long getTotalModelSize();

}
