package org.springblade.vlstream.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.vlstream.excel.VlsAlgorithmModelExcel;
import org.springblade.vlstream.pojo.dto.AlgorithmModelCreateDTO;
import org.springblade.vlstream.pojo.dto.AlgorithmModelStatisticsDTO;
import org.springblade.vlstream.pojo.entity.AlgorithmModel;
import org.springblade.vlstream.pojo.vo.AlgorithmModelVO;
import org.springblade.vlstream.service.IVlsAlgorithmModelService;
import org.springblade.vlstream.wrapper.VlsAlgorithmModelWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Algorithm model table controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/vlsAlgorithmModel")
@Tag(name = "Algorithm model table", description = "Algorithm model table interface")
public class VlsAlgorithmModelController extends BladeController {

	private final IVlsAlgorithmModelService vlsAlgorithmModelService;

	/**
	 * Algorithm model table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingvlsAlgorithmModel")
	public R<AlgorithmModelVO> detail(AlgorithmModel vlsAlgorithmModel) {
		AlgorithmModel detail = vlsAlgorithmModelService.getOne(Condition.getQueryWrapper(vlsAlgorithmModel));
		return R.data(VlsAlgorithmModelWrapper.build().entityVO(detail));
	}

	/**
	 * Algorithm model table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingvlsAlgorithmModel")
	public R<IPage<AlgorithmModelVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAlgorithmModel, Query query) {
		IPage<AlgorithmModel> pages = vlsAlgorithmModelService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsAlgorithmModel, AlgorithmModel.class));
		return R.data(VlsAlgorithmModelWrapper.build().pageVO(pages));
	}


	/**
	 * Algorithm model table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description = "incomingvlsAlgorithmModel")
	public R<IPage<AlgorithmModelVO>> page(AlgorithmModelVO vlsAlgorithmModel, Query query) {
		IPage<AlgorithmModelVO> pages = vlsAlgorithmModelService.selectVlsAlgorithmModelPage(Condition.getPage(query), vlsAlgorithmModel);
		return R.data(pages);
	}

	/**
	 * Algorithm model table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingvlsAlgorithmModel")
	public R save(@Valid @RequestBody AlgorithmModel vlsAlgorithmModel) {
		return R.status(vlsAlgorithmModelService.save(vlsAlgorithmModel));
	}

	/**
	 * Algorithm model table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingvlsAlgorithmModel")
	public R update(@Valid @RequestBody AlgorithmModel vlsAlgorithmModel) {
		return R.status(vlsAlgorithmModelService.updateById(vlsAlgorithmModel));
	}

	/**
	 * Algorithm model table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingvlsAlgorithmModel")
	public R submit(@Valid @RequestBody AlgorithmModel vlsAlgorithmModel) {
		return R.status(vlsAlgorithmModelService.saveOrUpdate(vlsAlgorithmModel));
	}

	/**
	 * Algorithm model table delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsAlgorithmModelService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsAlgorithmModel")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description = "incomingvlsAlgorithmModel")
	public void exportVlsAlgorithmModel(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAlgorithmModel, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<AlgorithmModel> queryWrapper = Condition.getQueryWrapper(vlsAlgorithmModel, AlgorithmModel.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsAlgorithmModelEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsAlgorithmModelEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsAlgorithmModelExcel> list = vlsAlgorithmModelService.exportVlsAlgorithmModel(queryWrapper);
		ExcelUtil.export(response, "Algorithm model table data" + DateUtil.time(), "Algorithm model table data table", list, VlsAlgorithmModelExcel.class);
	}

	@ApiOperation(value = "according toIDQuery algorithm model details")
	@GetMapping("/{id}")
	public R<AlgorithmModel> getModelById(@ApiParam("ModelID") @PathVariable Long id) {
		try {
			AlgorithmModel model = vlsAlgorithmModelService.getModelById(id);
			if (model == null) {
				return R.fail("Model does not exist");
			}
			return R.data(model);
		} catch (Exception e) {
			log.error("Failed to query algorithm model details", e);
			return R.fail("Failed to query algorithm model details: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Create an algorithm model")
	@PostMapping("/create")
	public R<AlgorithmModel> createModel(@Valid @RequestBody AlgorithmModelVO createDTO) {
		try {
			AlgorithmModel model = vlsAlgorithmModelService.createModel(createDTO);
			return R.data(model);
		} catch (Exception e) {
			log.error("Failed to create algorithm model", e);
			return R.fail("Failed to create algorithm model: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Delete algorithm model")
	@DeleteMapping("/{id}")
	public R<Boolean> deleteModel(@ApiParam("ModelID") @PathVariable Long id) {
		try {
			boolean success = vlsAlgorithmModelService.deleteModel(id);
			return R.data(success);
		} catch (Exception e) {
			log.error("Deletion of algorithm model failed", e);
			return R.fail("Deletion of algorithm model failed: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Batch deletion algorithm model")
	@DeleteMapping("/batch")
	public R<Boolean> batchDeleteModel(@RequestBody List<Long> ids) {
		try {
			boolean success = vlsAlgorithmModelService.batchDeleteModel(ids);
			return R.data(success);
		} catch (Exception e) {
			log.error("Batch deletion algorithm model failed", e);
			return R.fail("Batch deletion algorithm model failed: " + e.getMessage());
		}
	}

	@ApiOperation(value = "According to algorithmIDQuery model list")
	@GetMapping("/algorithm/{algorithmId}")
	public R<List<AlgorithmModel>> getModelsByAlgorithmId(@ApiParam("algorithmID") @PathVariable Long algorithmId) {
		try {
			List<AlgorithmModel> models = vlsAlgorithmModelService.getModelsByAlgorithmId(algorithmId);
			return R.data(models);
		} catch (Exception e) {
			log.error("According to algorithmIDQuerying model list failed", e);
			return R.fail("According to algorithmIDQuerying model list failed: " + e.getMessage());
		}
	}

	@ApiOperation(value = "According to training tasksIDQuery model list")
	@GetMapping("/training/{trainingId}")
	public R<List<AlgorithmModel>> getModelsByTrainingId(@ApiParam("training tasksID") @PathVariable Long trainingId) {
		try {
			List<AlgorithmModel> models = vlsAlgorithmModelService.getModelsByTrainingId(trainingId);
			return R.data(models);
		} catch (Exception e) {
			log.error("According to training tasksIDQuerying model list failed", e);
			return R.fail("According to training tasksIDQuerying model list failed: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Query model list based on status")
	@GetMapping("/status/{status}")
	public R<List<AlgorithmModel>> getModelsByStatus(@ApiParam("state") @PathVariable String status) {
		try {
			List<AlgorithmModel> models = vlsAlgorithmModelService.getModelsByStatus(status);
			return R.data(models);
		} catch (Exception e) {
			log.error("Querying model list based on status failed", e);
			return R.fail("Querying model list based on status failed: " + e.getMessage());
		}
	}

	@ApiOperation(value = "publish model")
	@PostMapping("/publish/{id}")
	public R<Boolean> publishModel(@ApiParam("ModelID") @PathVariable Long id) {
		try {
			boolean success = vlsAlgorithmModelService.publishModel(id);
			return R.data(success);
		} catch (Exception e) {
			log.error("Publishing model failed", e);
			return R.fail("Publishing model failed: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Undo release model")
	@PostMapping("/unpublish/{id}")
	public R<Boolean> unpublishModel(@ApiParam("ModelID") @PathVariable Long id) {
		try {
			boolean success = vlsAlgorithmModelService.unpublishModel(id);
			return R.data(success);
		} catch (Exception e) {
			log.error("Failed to unpublish model", e);
			return R.fail("Failed to unpublish model: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Release models in batches")
	@PostMapping("/batch-publish")
	public R<Boolean> batchPublishModel(@RequestBody List<Long> ids) {
		try {
			boolean success = vlsAlgorithmModelService.batchPublishModel(ids);
			return R.data(success);
		} catch (Exception e) {
			log.error("Failed to publish models in batches", e);
			return R.fail("Failed to publish models in batches: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Download model")
	@GetMapping("/download/{id}")
	public R<String> downloadModel(@ApiParam("ModelID") @PathVariable Long id) {
		try {
			String filePath = vlsAlgorithmModelService.downloadModel(id);
			return R.success(filePath);
		} catch (Exception e) {
			log.error("Failed to download model", e);
			return R.fail("Failed to download model: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Deployment model")
	@PostMapping("/deploy/{id}")
	public R<Boolean> deployModel(@ApiParam("ModelID") @PathVariable Long id) {
		try {
			boolean success = vlsAlgorithmModelService.deployModel(id);
			return R.data(success);
		} catch (Exception e) {
			log.error("Deployment model failed", e);
			return R.fail("Deployment model failed: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Get model statistics")
	@GetMapping("/statistics")
	public R<AlgorithmModelStatisticsDTO> getStatistics() {
		try {
			AlgorithmModelStatisticsDTO statistics = vlsAlgorithmModelService.getStatistics();
			return R.data(statistics);
		} catch (Exception e) {
			log.error("Failed to obtain model statistics", e);
			return R.fail("Failed to obtain model statistics: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Check if model name and version exist")
	@GetMapping("/check-name-version")
	public R<Boolean> checkModelNameAndVersion(
		@ApiParam("Model name") @RequestParam String modelName,
		@ApiParam("model version") @RequestParam Integer version,
		@ApiParam("excludedID") @RequestParam(required = false) Long excludeId) {
		try {
			boolean exists = vlsAlgorithmModelService.checkModelNameAndVersion(modelName, version, excludeId);
			return R.data(exists);
		} catch (Exception e) {
			log.error("Checking model name and version failed", e);
			return R.fail("Checking model name and version failed: " + e.getMessage());
		}
	}

	@ApiOperation(value = "According to algorithmIDand version query model")
	@GetMapping("/algorithm/{algorithmId}/version/{version}")
	public R<AlgorithmModel> getModelByAlgorithmIdAndVersion(
		@ApiParam("algorithmID") @PathVariable Long algorithmId,
		@ApiParam("Version") @PathVariable Integer version) {
		try {
			AlgorithmModel model = vlsAlgorithmModelService.getModelByAlgorithmIdAndVersion(algorithmId, version);
			return R.data(model);
		} catch (Exception e) {
			log.error("According to algorithmIDand version query model failed", e);
			return R.fail("According to algorithmIDand version query model failed: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Get the latest version of the model under the algorithm")
	@GetMapping("/algorithm/{algorithmId}/latest")
	public R<AlgorithmModel> getLatestModelByAlgorithmId(@ApiParam("algorithmID") @PathVariable Long algorithmId) {
		try {
			AlgorithmModel model = vlsAlgorithmModelService.getLatestModelByAlgorithmId(algorithmId);
			return R.data(model);
		} catch (Exception e) {
			log.error("Failed to obtain the latest version of the model under the algorithm", e);
			return R.fail("Failed to obtain the latest version of the model under the algorithm: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Query popular models")
	@GetMapping("/popular")
	public R<List<AlgorithmModel>> getPopularModels(@ApiParam("limited quantity") @RequestParam(defaultValue = "10") Integer limit) {
		try {
			List<AlgorithmModel> models = vlsAlgorithmModelService.getPopularModels(limit);
			return R.data(models);
		} catch (Exception e) {
			log.error("Failed to query popular models", e);
			return R.fail("Failed to query popular models: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Query the number of models based on the creator")
	@GetMapping("/count/creator/{createdBy}")
	public R<Long> countModelsByCreatedBy(@ApiParam("CreatorID") @PathVariable Long createdBy) {
		try {
			Long count = vlsAlgorithmModelService.countModelsByCreatedBy(createdBy);
			return R.data(count);
		} catch (Exception e) {
			log.error("Querying the number of models based on the creator failed", e);
			return R.fail("Querying the number of models based on the creator failed: " + e.getMessage());
		}
	}

	@ApiOperation(value = "Get the total size of the algorithm model")
	@GetMapping("/total-size")
	public R<Long> getTotalModelSize() {
		try {
			Long totalSize = vlsAlgorithmModelService.getTotalModelSize();
			return R.data(totalSize);
		} catch (Exception e) {
			log.error("Failed to get total size of algorithm model", e);
			return R.fail("Failed to get total size of algorithm model: " + e.getMessage());
		}
	}

}
