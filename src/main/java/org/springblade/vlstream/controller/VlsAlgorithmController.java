package org.springblade.vlstream.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import org.springblade.vlstream.excel.VlsAlgorithmExcel;
import org.springblade.vlstream.pojo.entity.Algorithm;
import org.springblade.vlstream.pojo.vo.AlgorithmVO;
import org.springblade.vlstream.service.IVlsAlgorithmService;
import org.springblade.vlstream.wrapper.VlsAlgorithmWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Algorithm table controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/vlsAlgorithm")
@Tag(name = "Algorithm table", description = "Algorithm table interface")
public class VlsAlgorithmController extends BladeController {

	private final IVlsAlgorithmService vlsAlgorithmService;

	/**
	 * Algorithm table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingvlsAlgorithm")
	public R<AlgorithmVO> detail(Algorithm vlsAlgorithm) {
		Algorithm detail = vlsAlgorithmService.getOne(Condition.getQueryWrapper(vlsAlgorithm));
		return R.data(VlsAlgorithmWrapper.build().entityVO(detail));
	}

	/**
	 * Algorithm table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingvlsAlgorithm")
	public R<IPage<AlgorithmVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAlgorithm, Query query) {
		IPage<Algorithm> pages = vlsAlgorithmService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsAlgorithm, Algorithm.class));
		return R.data(VlsAlgorithmWrapper.build().pageVO(pages));
	}


	/**
	 * Algorithm table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description = "incomingvlsAlgorithm")
	public R<IPage<AlgorithmVO>> page(AlgorithmVO vlsAlgorithm, Query query) {
		IPage<AlgorithmVO> pages = vlsAlgorithmService.selectVlsAlgorithmPage(Condition.getPage(query), vlsAlgorithm);
		for (AlgorithmVO algorithm : pages.getRecords()) {
			algorithm.setCategoryName(algorithm.getCategory().getDescription());
		}
		return R.data(pages);
	}

	/**
	 * Algorithm table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingvlsAlgorithm")
	public R save(@Valid @RequestBody Algorithm vlsAlgorithm) {
		return R.status(vlsAlgorithmService.save(vlsAlgorithm));
	}

	/**
	 * Algorithm table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingvlsAlgorithm")
	public R update(@Valid @RequestBody Algorithm vlsAlgorithm) {
		return R.status(vlsAlgorithmService.updateById(vlsAlgorithm));
	}

	/**
	 * Algorithm table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingvlsAlgorithm")
	public R submit(@Valid @RequestBody Algorithm vlsAlgorithm) {
		return R.status(vlsAlgorithmService.saveOrUpdate(vlsAlgorithm));
	}

	/**
	 * Algorithm table delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsAlgorithmService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsAlgorithm")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description = "incomingvlsAlgorithm")
	public void exportVlsAlgorithm(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAlgorithm, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<Algorithm> queryWrapper = Condition.getQueryWrapper(vlsAlgorithm, Algorithm.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsAlgorithmEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsAlgorithmEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsAlgorithmExcel> list = vlsAlgorithmService.exportVlsAlgorithm(queryWrapper);
		ExcelUtil.export(response, "Algorithm table data" + DateUtil.time(), "Algorithm table data table", list, VlsAlgorithmExcel.class);
	}

	/**
	 * According to warehouseIDQuery algorithm list
	 */
	@GetMapping("/repository/{repositoryId}")
	@Operation(summary = "According to warehouseIDQuery algorithm list", description = "Get all algorithms under the specified warehouse")
	public R<List<Algorithm>> getAlgorithmsByRepositoryId(
		@Parameter(description = "storehouseID", example = "1") @PathVariable @NotNull Long repositoryId) {

		log.info("According to warehouseIDQuery algorithm list: {}", repositoryId);

		List<Algorithm> algorithms = vlsAlgorithmService.getByRepositoryId(repositoryId);
		return R.data(algorithms);
	}

	/**
	 * Query algorithm list according to classification
	 */
	@GetMapping("/category/{category}")
	@Operation(summary = "Query algorithm list according to classification", description = "Get all algorithms for a specified category")
	public R<List<Algorithm>> getAlgorithmsByCategory(
		@Parameter(description = "Algorithm type", example = "person-detection") @PathVariable String category) {

		log.info("Query algorithm list according to classification: {}", category);

		List<Algorithm> algorithms = vlsAlgorithmService.getByCategory(category);
		return R.data(algorithms);
	}

	/**
	 * according toIDQuery algorithm details
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Query algorithm details", description = "according toIDGet algorithm details")
	public R<Algorithm> getAlgorithmById(
		@Parameter(description = "algorithmID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Query algorithm details: ID={}", id);

		Algorithm algorithm = vlsAlgorithmService.getById(id);
		if (algorithm == null) {
			return R.fail("Algorithm does not exist");
		}

		return R.data(algorithm);
	}

	/**
	 * Create algorithm
	 */
	@PostMapping
	@Operation(summary = "Create algorithm", description = "New algorithm")
	public R<String> createAlgorithm(@Valid @RequestBody Algorithm algorithm) {
		log.info("Create algorithm: {}", algorithm.getName());

		boolean success = vlsAlgorithmService.createAlgorithm(algorithm);
		if (success) {
			return R.success("Algorithm created successfully");
		} else {
			return R.fail("Algorithm creation failed, The name may already exist in the same warehouse");
		}
	}

	/**
	 * Update algorithm
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Update algorithm", description = "according toIDUpdate algorithm information")
	public R<String> updateAlgorithm(
		@Parameter(description = "algorithmID", example = "1") @PathVariable @NotNull Long id,
		@Valid @RequestBody Algorithm algorithm) {

		log.info("Update algorithm: ID={}", id);

		algorithm.setId(id);
		boolean success = vlsAlgorithmService.updateAlgorithm(algorithm);

		if (success) {
			return R.success("Algorithm update successful");
		} else {
			return R.fail("Algorithm update failed");
		}
	}

	/**
	 * Delete algorithm
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete algorithm", description = "according toIDDelete algorithm(soft delete)")
	public R<String> deleteAlgorithm(
		@Parameter(description = "algorithmID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Delete algorithm: ID={}", id);

		boolean success = vlsAlgorithmService.deleteAlgorithm(id);
		if (success) {
			return R.success("Algorithm deletion successful");
		} else {
			return R.fail("Algorithm deletion failed");
		}
	}

	/**
	 * Batch deletion algorithm
	 */
	@DeleteMapping("/batch")
	@Operation(summary = "Batch deletion algorithm", description = "according toIDList batch deletion algorithm")
	public R<String> batchDeleteAlgorithms(@RequestBody List<Long> ids) {
		log.info("Batch deletion algorithm: IDs={}", ids);

		if (ids == null || ids.isEmpty()) {
			return R.fail("Please select the algorithm to delete");
		}

		boolean success = vlsAlgorithmService.batchDeleteAlgorithms(ids);
		if (success) {
			return R.success("Algorithm batch deletion successful");
		} else {
			return R.fail("Algorithm batch deletion failed");
		}
	}

	/**
	 * Update deployment status
	 */
	@PutMapping("/{id}/deploy-status")
	@Operation(summary = "Update deployment status", description = "Update the deployment status of the algorithm")
	public R<String> updateDeployStatus(
		@Parameter(description = "algorithmID", example = "1") @PathVariable @NotNull Long id,
		@Parameter(description = "Deployment status", example = "deployed") @RequestParam @NotNull String deployStatus) {

		log.info("Update algorithm deployment status: ID={}, Status={}", id, deployStatus);

		boolean success = vlsAlgorithmService.updateDeployStatus(id, deployStatus);
		if (success) {
			return R.success("Deployment status updated successfully");
		} else {
			return R.fail("Deployment status update failed");
		}
	}

	/**
	 * Update deployment status in batches
	 */
	@PutMapping("/batch/deploy-status")
	@Operation(summary = "Update deployment status in batches", description = "Batch update algorithm deployment status")
	public R<String> batchUpdateDeployStatus(
		@RequestBody List<Long> ids,
		@Parameter(description = "Deployment status", example = "deployed") @RequestParam @NotNull String deployStatus) {

		log.info("Batch update algorithm deployment status: IDs={}, Status={}", ids, deployStatus);

		if (ids == null || ids.isEmpty()) {
			return R.fail("Please select the algorithm to update");
		}

		boolean success = vlsAlgorithmService.batchUpdateDeployStatus(ids, deployStatus);
		if (success) {
			return R.success("Deployment status batch update successful");
		} else {
			return R.fail("Deployment status batch update failed");
		}
	}

	/**
	 * Deploy algorithm to device
	 */
	@PostMapping("/{id}/deploy")
	@Operation(summary = "Deploy algorithm to device", description = "Deploy the algorithm to the specified device")
	public R<String> deployAlgorithmToDevices(
		@Parameter(description = "algorithmID", example = "1") @PathVariable @NotNull Long algorithmId,
		@RequestBody List<Long> deviceIds) {

		log.info("Deploy algorithm to device: AlgorithmId={}, DeviceIds={}", algorithmId, deviceIds);

		if (deviceIds == null || deviceIds.isEmpty()) {
			return R.fail("Please select a device to deploy");
		}

		boolean success = vlsAlgorithmService.deployAlgorithmToDevices(algorithmId, deviceIds);
		if (success) {
			return R.success("Algorithm deployment successful");
		} else {
			return R.fail("Algorithm deployment failed");
		}
	}

	/**
	 * Algorithm evaluation
	 */
	@PostMapping("/{algorithmId}/evaluate")
	@Operation(summary = "Algorithm evaluation", description = "Performance evaluation of algorithms")
	public R<Map<String, Object>> evaluateAlgorithm(@Parameter(description = "algorithmID", example = "1") @PathVariable @NotNull Long algorithmId) {

		log.info("Algorithm evaluation: AlgorithmId={}", algorithmId);

		Map<String, Object> result = vlsAlgorithmService.evaluateAlgorithm(algorithmId);
		if (result != null) {
			return R.data(result);
		} else {
			return R.fail("Algorithm evaluation failed, Algorithm does not exist");
		}
	}

	/**
	 * Get algorithm classification statistics
	 */
	@GetMapping("/statistics/category")
	@Operation(summary = "Get algorithm classification statistics", description = "Get statistics on the number of algorithms in each category")
	public R<List<Map<String, Object>>> getCategoryStatistics() {
		log.info("Get algorithm classification statistics");

		List<Map<String, Object>> statistics = vlsAlgorithmService.getCategoryStatistics();
		return R.data(statistics);
	}

	/**
	 * Get algorithm type statistics
	 */
	@GetMapping("/statistics/type")
	@Operation(summary = "Get algorithm type statistics", description = "Get statistics on the number of algorithms of various types")
	public R<List<Map<String, Object>>> getTypeStatistics() {
		log.info("Get algorithm type statistics");

		List<Map<String, Object>> statistics = vlsAlgorithmService.getTypeStatistics();
		return R.data(statistics);
	}

	/**
	 * Get deployment status statistics
	 */
	@GetMapping("/statistics/deploy-status")
	@Operation(summary = "Get deployment status statistics", description = "Obtain statistics on the number of algorithms in each deployment status")
	public R<List<Map<String, Object>>> getDeployStatusStatistics() {
		log.info("Get deployment status statistics");

		List<Map<String, Object>> statistics = vlsAlgorithmService.getDeployStatusStatistics();
		return R.data(statistics);
	}

	/**
	 * Count the number of algorithms under a certain warehouse
	 */
	@GetMapping("/count/repository/{repositoryId}")
	@Operation(summary = "Count the number of algorithms under a certain warehouse", description = "Get the number of algorithms in the specified warehouse")
	public R<Long> countByRepositoryId(
		@Parameter(description = "storehouseID", example = "1") @PathVariable @NotNull Long repositoryId) {

		log.info("Count the number of algorithms under a certain warehouse: RepositoryId={}", repositoryId);

		Long count = vlsAlgorithmService.countByRepositoryId(repositoryId);
		return R.data(count);
	}

}
