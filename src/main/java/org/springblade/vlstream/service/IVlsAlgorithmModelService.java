package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.vlstream.excel.VlsAlgorithmModelExcel;
import org.springblade.vlstream.pojo.dto.AlgorithmModelCreateDTO;
import org.springblade.vlstream.pojo.dto.AlgorithmModelQueryDTO;
import org.springblade.vlstream.pojo.dto.AlgorithmModelStatisticsDTO;
import org.springblade.vlstream.pojo.dto.AlgorithmModelUpdateDTO;
import org.springblade.vlstream.pojo.entity.AlgorithmModel;
import org.springblade.vlstream.pojo.vo.AlgorithmModelVO;

import java.util.List;

/**
 * Algorithm model table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsAlgorithmModelService extends BaseService<AlgorithmModel> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAlgorithmModel query parameters
	 * @return IPage<VlsAlgorithmModelVO>
	 */
	IPage<AlgorithmModelVO> selectVlsAlgorithmModelPage(IPage<AlgorithmModelVO> page, AlgorithmModelVO vlsAlgorithmModel);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAlgorithmModelExcel>
	 */
	List<VlsAlgorithmModelExcel> exportVlsAlgorithmModel(Wrapper<AlgorithmModel> queryWrapper);

	/**
	 * Paging query algorithm model
	 *
	 * @param queryDTO query parameters
	 * @return Paginated results
	 */
	IPage<AlgorithmModel> getModelPage(AlgorithmModelQueryDTO queryDTO);

	/**
	 * according toIDQuery algorithm model details
	 *
	 * @param id ModelID
	 * @return algorithm model
	 */
	AlgorithmModel getModelById(Long id);

	/**
	 * Create an algorithm model
	 *
	 * @param createDTO Create parameters
	 * @return Create a successful model
	 */
	AlgorithmModel createModel(AlgorithmModelVO createDTO);

	/**
	 * Update algorithm model
	 *
	 * @param updateDTO Update parameters
	 * @return Successfully updated model
	 */
	AlgorithmModel updateModel(AlgorithmModelUpdateDTO updateDTO);

	/**
	 * Delete algorithm model
	 *
	 * @param id ModelID
	 * @return Is it successful?
	 */
	boolean deleteModel(Long id);

	/**
	 * Batch deletion algorithm model
	 *
	 * @param ids ModelIDlist
	 * @return Is it successful?
	 */
	boolean batchDeleteModel(List<Long> ids);

	/**
	 * According to algorithmIDQuery model list
	 *
	 * @param algorithmId algorithmID
	 * @return Model list
	 */
	List<AlgorithmModel> getModelsByAlgorithmId(Long algorithmId);

	/**
	 * According to training tasksIDQuery model list
	 *
	 * @param trainingId training tasksID
	 * @return Model list
	 */
	List<AlgorithmModel> getModelsByTrainingId(Long trainingId);

	/**
	 * Query model list based on status
	 *
	 * @param status state
	 * @return Model list
	 */
	List<AlgorithmModel> getModelsByStatus(String status);

	/**
	 * publish model
	 *
	 * @param id ModelID
	 * @return Is it successful?
	 */
	boolean publishModel(Long id);

	/**
	 * Undo release model
	 *
	 * @param id ModelID
	 * @return Is it successful?
	 */
	boolean unpublishModel(Long id);

	/**
	 * Release models in batches
	 *
	 * @param ids ModelIDlist
	 * @return Is it successful?
	 */
	boolean batchPublishModel(List<Long> ids);

	/**
	 * Download model
	 *
	 * @param id ModelID
	 * @return Model file path
	 */
	String downloadModel(Long id);

	/**
	 * Deployment model
	 *
	 * @param id ModelID
	 * @return Is it successful?
	 */
	boolean deployModel(Long id);

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
	 * @return exists
	 */
	boolean checkModelNameAndVersion(String modelName, Integer version, Long excludeId);

	/**
	 * According to algorithmIDand version query model
	 *
	 * @param algorithmId algorithmID
	 * @param version Version
	 * @return algorithm model
	 */
	AlgorithmModel getModelByAlgorithmIdAndVersion(Long algorithmId, Integer version);

	/**
	 * Get the latest version of the model under the algorithm
	 *
	 * @param algorithmId algorithmID
	 * @return algorithm model
	 */
	AlgorithmModel getLatestModelByAlgorithmId(Long algorithmId);

	/**
	 * Query popular models(Sort by download count)
	 *
	 * @param limit limited quantity
	 * @return Model list
	 */
	List<AlgorithmModel> getPopularModels(Integer limit);

	/**
	 * Query the number of models based on the creator
	 *
	 * @param createdBy CreatorID
	 * @return Number of models
	 */
	Long countModelsByCreatedBy(Long createdBy);

	/**
	 * Get the total size of the algorithm model
	 *
	 * @return total size(byte)
	 */
	Long getTotalModelSize();

}
