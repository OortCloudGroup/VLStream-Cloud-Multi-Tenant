package org.springblade.vlstream.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.enums.YesNoEnum;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.vlstream.excel.VlsAlgorithmModelExcel;
import org.springblade.vlstream.mapper.VlsAlgorithmModelMapper;
import org.springblade.vlstream.mapper.VlsAlgorithmTrainingMapper;
import org.springblade.vlstream.pojo.dto.AlgorithmModelQueryDTO;
import org.springblade.vlstream.pojo.dto.AlgorithmModelStatisticsDTO;
import org.springblade.vlstream.pojo.dto.AlgorithmModelUpdateDTO;
import org.springblade.vlstream.pojo.entity.AlgorithmModel;
import org.springblade.vlstream.pojo.entity.AlgorithmTraining;
import org.springblade.vlstream.pojo.vo.AlgorithmModelVO;
import org.springblade.vlstream.service.IVlsAlgorithmModelService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Algorithm model table Service implementation class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Slf4j
@Service
public class VlsAlgorithmModelServiceImpl extends BaseServiceImpl<VlsAlgorithmModelMapper, AlgorithmModel> implements IVlsAlgorithmModelService {

	@Resource
	private VlsAlgorithmTrainingMapper trainingMapper;

	@Override
	public IPage<AlgorithmModelVO> selectVlsAlgorithmModelPage(IPage<AlgorithmModelVO> page, AlgorithmModelVO vlsAlgorithmModel) {
		return page.setRecords(baseMapper.selectVlsAlgorithmModelPage(page, vlsAlgorithmModel));
	}

	@Override
	public List<VlsAlgorithmModelExcel> exportVlsAlgorithmModel(Wrapper<AlgorithmModel> queryWrapper) {
		List<VlsAlgorithmModelExcel> vlsAlgorithmModelList = baseMapper.exportVlsAlgorithmModel(queryWrapper);
		//vlsAlgorithmModelList.forEach(vlsAlgorithmModel -> {
		//	vlsAlgorithmModel.setTypeName(DictCache.getValue(DictEnum.YES_NO, VlsAlgorithmModelEntity.getType()));
		//});
		return vlsAlgorithmModelList;
	}

	@Override
	public IPage<AlgorithmModel> getModelPage(AlgorithmModelQueryDTO queryDTO) {
		Page<AlgorithmModel> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
		return baseMapper.selectModelPage(page, queryDTO);
	}

	@Override
	public AlgorithmModel getModelById(Long id) {
		if (id == null) {
			log.warn("When getting model details, IDis empty");
			return null;
		}
		return baseMapper.selectById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AlgorithmModel createModel(AlgorithmModelVO createDTO) {
		log.info("Create an algorithm model: {}", createDTO.getModelName());

		// Verify that model name and version exist
		Integer version = createDTO.getVersion();
		if (version == null || version < 1) {
			version = 1;
		}
		while (checkModelNameAndVersion(createDTO.getModelName(), version, null)) {
			version++;
		}

		AlgorithmTraining training = trainingMapper.selectById(createDTO.getTrainingId());
		// Create model entities
		AlgorithmModel model = new AlgorithmModel();
		BeanUtils.copyProperties(createDTO, model);
		model.setVersion(version);
		// Set default value
		if (model.getDownloadCount() == null) {
			model.setDownloadCount(0);
		}
		if (model.getDeployCount() == null) {
			model.setDeployCount(0);
		}
		if (model.getStatus() == null) {
			model.setStatus(YesNoEnum.NO.getCode());
		}
		model.setModelPath(training.getModelOutputPath());
		model.setOnnxModelPath(training.getOnnxModelOutputPath());
		model.setRknnModelPath(training.getRknnModelOutputPath());
		model.setInt8RknnModelOutputPath(training.getInt8RknnModelOutputPath());
		// Save to database
		boolean success = save(model);
		if (!success) {
			log.error("Failed to create model: {}", createDTO.getModelName());
			throw new RuntimeException("Failed to create model");
		}

		log.info("Model created successfully, ID: {}", model.getId());
		return model;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AlgorithmModel updateModel(AlgorithmModelUpdateDTO updateDTO) {
		log.info("Update algorithm model: {}", updateDTO.getId());

		// Check if the model exists
		AlgorithmModel existingModel = getModelById(updateDTO.getId());
		if (existingModel == null) {
			throw new RuntimeException("Model does not exist");
		}

		// Verify that model name and version exist(Exclude current record)
		if (updateDTO.getModelName() != null && updateDTO.getVersion() != null) {
			if (checkModelNameAndVersion(updateDTO.getModelName(), updateDTO.getVersion(), updateDTO.getId())) {
				throw new RuntimeException("Model name and version already exist");
			}
		}

		// Check if the model can be updated
		if ("published".equals(existingModel.getStatus()) && updateDTO.getStatus() != null &&
			!updateDTO.getStatus().equals(existingModel.getStatus())) {
			throw new RuntimeException("Published models cannot modify status");
		}

		// Update field
		if (updateDTO.getModelName() != null) {
			existingModel.setModelName(updateDTO.getModelName());
		}
		if (updateDTO.getVersion() != null) {
			existingModel.setVersion(updateDTO.getVersion());
		}
		if (updateDTO.getModelFormat() != null) {
			existingModel.setModelFormat(updateDTO.getModelFormat());
		}
		if (updateDTO.getModelSize() != null) {
			existingModel.setModelSize(updateDTO.getModelSize());
		}
		if (updateDTO.getModelPath() != null) {
			existingModel.setModelPath(updateDTO.getModelPath());
		}
		if (updateDTO.getAccuracy() != null) {
			existingModel.setAccuracy(updateDTO.getAccuracy());
		}
		if (updateDTO.getDescription() != null) {
			existingModel.setDescription(updateDTO.getDescription());
		}
		if (updateDTO.getStatus() != null) {
			existingModel.setStatus(updateDTO.getStatus());
		}

		// Save updates
		boolean success = updateById(existingModel);
		if (!success) {
			log.error("Update model failed: {}", updateDTO.getId());
			throw new RuntimeException("Update model failed");
		}

		log.info("Update model successfully: {}", updateDTO.getId());
		return existingModel;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteModel(Long id) {
		log.info("Delete algorithm model: {}", id);

		// Check if the model exists
		AlgorithmModel existingModel = getModelById(id);
		if (existingModel == null) {
			throw new RuntimeException("Model does not exist");
		}

		// Check if the model can be deleted
		if (existingModel.getStatus().equals(2)) {
			throw new RuntimeException("Published models cannot be deleted");
		}

		// Delete model(tombstone)
		boolean success = removeById(id);
		if (!success) {
			log.error("Failed to delete model: {}", id);
			throw new RuntimeException("Failed to delete model");
		}

		log.info("Deleted model successfully: {}", id);
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean batchDeleteModel(List<Long> ids) {
		log.info("Batch deletion algorithm model: {}", ids);

		if (ids == null || ids.isEmpty()) {
			throw new RuntimeException("deleted modelIDList cannot be empty");
		}

		// Check if all models can be deleted
		List<AlgorithmModel> models = listByIds(ids);
		for (AlgorithmModel model : models) {
			if (model.getStatus().equals(2)) {
				throw new RuntimeException("Model " + model.getModelName() + " Published, cannot be deleted");
			}
		}

		// Batch delete
		boolean success = removeByIds(ids);
		if (!success) {
			log.error("Batch deletion of models failed: {}", ids);
			throw new RuntimeException("Batch deletion of models failed");
		}

		log.info("Batch deletion of models successful: {}", ids);
		return true;
	}

	@Override
	public List<AlgorithmModel> getModelsByAlgorithmId(Long algorithmId) {
		return baseMapper.selectByAlgorithmId(algorithmId);
	}

	@Override
	public List<AlgorithmModel> getModelsByTrainingId(Long trainingId) {
		return baseMapper.selectByTrainingId(trainingId);
	}

	@Override
	public List<AlgorithmModel> getModelsByStatus(String status) {
		return baseMapper.selectByStatus(status);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean publishModel(Long id) {
		log.info("Publish algorithm model: {}", id);

		// Check if the model exists
		AlgorithmModel existingModel = getModelById(id);
		if (existingModel == null) {
			throw new RuntimeException("Model does not exist");
		}

		// Check if the model can be published
		if (existingModel.getStatus().equals(2)) {
			throw new RuntimeException("Model status does not allow publishing");
		}

		// Verify that the model file exists
		if (!new File(existingModel.getModelPath()).exists()) {
			throw new RuntimeException("Model file does not exist, Unable to publish");
		}

		// Update status is published
		int result = baseMapper.updateStatus(id, "published");
		if (result <= 0) {
			log.error("Publishing model failed: {}", id);
			throw new RuntimeException("Publishing model failed");
		}

		// Update release time
		existingModel.setPublishTime(LocalDateTime.now());
		updateById(existingModel);

		log.info("Published model successfully: {}", id);
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean unpublishModel(Long id) {
		log.info("Unpublish algorithm model: {}", id);

		// Check if the model exists
		AlgorithmModel existingModel = getModelById(id);
		if (existingModel == null) {
			throw new RuntimeException("Model does not exist");
		}

		// Check if the model has been published
		if (!"published".equals(existingModel.getStatus())) {
			throw new RuntimeException("Model not published, Cannot be undone");
		}

		// Update status is draft
		int result = baseMapper.updateStatus(id, "draft");
		if (result <= 0) {
			log.error("Failed to unpublish model: {}", id);
			throw new RuntimeException("Failed to unpublish model");
		}

		// Clear publishing time
		existingModel.setPublishTime(null);
		updateById(existingModel);

		log.info("Unpublishing model successful: {}", id);
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean batchPublishModel(List<Long> ids) {
		log.info("Release algorithm models in batches: {}", ids);

		if (ids == null || ids.isEmpty()) {
			throw new RuntimeException("Published modelIDList cannot be empty");
		}

		// Check if all models can be published
		List<AlgorithmModel> models = listByIds(ids);
		for (AlgorithmModel model : models) {
			if (model.getStatus().equals(2)) {
				throw new RuntimeException("Model " + model.getModelName() + " Status does not allow publishing");
			}
		}

		// Batch update status
		int result = baseMapper.batchUpdateStatus(ids, "published");
		if (result <= 0) {
			log.error("Failed to publish models in batches: {}", ids);
			throw new RuntimeException("Failed to publish models in batches");
		}

		// Update release time
		for (AlgorithmModel model : models) {
			model.setPublishTime(LocalDateTime.now());
			updateById(model);
		}

		log.info("Successfully released models in batches: {}", ids);
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String downloadModel(Long id) {
		log.info("Download algorithm model: {}", id);

		// Check if the model exists
		AlgorithmModel existingModel = getModelById(id);
		if (existingModel == null) {
			throw new RuntimeException("Model does not exist");
		}

		// Check if the model is available for download
		if (existingModel.getStatus().equals(2)) {
			throw new RuntimeException("Model not published, Unable to download");
		}

		// Verify that the model file exists
		String filePath = existingModel.getModelPath();
		if (!new File(filePath).exists()) {
			throw new RuntimeException("Model file does not exist");
		}

		// Increase downloads
		int result = baseMapper.updateDownloadCount(id);
		if (result <= 0) {
			log.error("Update download count failed: {}", id);
		}

		log.info("Download model successfully: {}", id);
		return filePath;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deployModel(Long id) {
		log.info("Deploy algorithm model: {}", id);

		// Check if the model exists
		AlgorithmModel existingModel = getModelById(id);
		if (existingModel == null) {
			throw new RuntimeException("Model does not exist");
		}

		// Check if the model can be deployed
		if (!existingModel.getStatus().equals(2)) {
			throw new RuntimeException("Model not published, Unable to deploy");
		}

		// Verify that the model file exists
		if (!new File(existingModel.getModelPath()).exists()) {
			throw new RuntimeException("Model file does not exist");
		}

		// The actual deployment logic should be called here
		// For example: callDocker API、Kubernetes APIwait
		log.info("Execute model deployment logic...");

		// Increase the number of deployments
		int result = baseMapper.updateDeployCount(id);
		if (result <= 0) {
			log.error("Failed to update deployment times: {}", id);
		}

		log.info("Deployment model successful: {}", id);
		return true;
	}

	@Override
	public AlgorithmModelStatisticsDTO getStatistics() {
		return baseMapper.getStatistics();
	}

	@Override
	public boolean checkModelNameAndVersion(String modelName, Integer version, Long excludeId) {
		int count = baseMapper.checkModelNameAndVersion(modelName, version, excludeId);
		return count > 0;
	}

	@Override
	public AlgorithmModel getModelByAlgorithmIdAndVersion(Long algorithmId, Integer version) {
		return baseMapper.selectByAlgorithmIdAndVersion(algorithmId, version);
	}

	@Override
	public AlgorithmModel getLatestModelByAlgorithmId(Long algorithmId) {
		return baseMapper.selectLatestByAlgorithmId(algorithmId);
	}

	@Override
	public List<AlgorithmModel> getPopularModels(Integer limit) {
		return baseMapper.selectPopularModels(limit);
	}

	@Override
	public Long countModelsByCreatedBy(Long createdBy) {
		return baseMapper.countByCreatedBy(createdBy);
	}

	@Override
	public Long getTotalModelSize() {
		return baseMapper.getTotalModelSize();
	}

}
