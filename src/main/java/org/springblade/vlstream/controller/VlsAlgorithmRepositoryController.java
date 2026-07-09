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
import org.springblade.vlstream.excel.VlsAlgorithmRepositoryExcel;
import org.springblade.vlstream.pojo.entity.AlgorithmRepository;
import org.springblade.vlstream.pojo.vo.AlgorithmRepositoryVO;
import org.springblade.vlstream.service.IVlsAlgorithmRepositoryService;
import org.springblade.vlstream.wrapper.VlsAlgorithmRepositoryWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Algorithm warehouse table controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/vlsAlgorithmRepository")
@Tag(name = "Algorithm warehouse table", description = "Algorithm warehouse table interface")
public class VlsAlgorithmRepositoryController extends BladeController {

	private final IVlsAlgorithmRepositoryService vlsAlgorithmRepositoryService;

	/**
	 * Algorithm warehouse table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingvlsAlgorithmRepository")
	public R<AlgorithmRepositoryVO> detail(AlgorithmRepository vlsAlgorithmRepository) {
		AlgorithmRepository detail = vlsAlgorithmRepositoryService.getOne(Condition.getQueryWrapper(vlsAlgorithmRepository));
		return R.data(VlsAlgorithmRepositoryWrapper.build().entityVO(detail));
	}

	/**
	 * Algorithm warehouse table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingvlsAlgorithmRepository")
	public R<IPage<AlgorithmRepositoryVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAlgorithmRepository, Query query) {
		IPage<AlgorithmRepository> pages = vlsAlgorithmRepositoryService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsAlgorithmRepository, AlgorithmRepository.class));
		return R.data(VlsAlgorithmRepositoryWrapper.build().pageVO(pages));
	}


	/**
	 * Algorithm warehouse table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description = "incomingvlsAlgorithmRepository")
	public R<IPage<AlgorithmRepositoryVO>> page(AlgorithmRepositoryVO vlsAlgorithmRepository, Query query) {
		IPage<AlgorithmRepositoryVO> pages = vlsAlgorithmRepositoryService.selectVlsAlgorithmRepositoryPage(Condition.getPage(query), vlsAlgorithmRepository);
		return R.data(pages);
	}

	/**
	 * Algorithm warehouse table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingvlsAlgorithmRepository")
	public R save(@Valid @RequestBody AlgorithmRepository vlsAlgorithmRepository) {
		return R.status(vlsAlgorithmRepositoryService.save(vlsAlgorithmRepository));
	}

	/**
	 * Algorithm warehouse table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingvlsAlgorithmRepository")
	public R update(@Valid @RequestBody AlgorithmRepository vlsAlgorithmRepository) {
		return R.status(vlsAlgorithmRepositoryService.updateById(vlsAlgorithmRepository));
	}

	/**
	 * Algorithm warehouse table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingvlsAlgorithmRepository")
	public R submit(@Valid @RequestBody AlgorithmRepository vlsAlgorithmRepository) {
		return R.status(vlsAlgorithmRepositoryService.saveOrUpdate(vlsAlgorithmRepository));
	}

	/**
	 * Algorithm warehouse table delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsAlgorithmRepositoryService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsAlgorithmRepository")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description = "incomingvlsAlgorithmRepository")
	public void exportVlsAlgorithmRepository(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAlgorithmRepository, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<AlgorithmRepository> queryWrapper = Condition.getQueryWrapper(vlsAlgorithmRepository, AlgorithmRepository.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsAlgorithmRepositoryEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsAlgorithmRepositoryEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsAlgorithmRepositoryExcel> list = vlsAlgorithmRepositoryService.exportVlsAlgorithmRepository(queryWrapper);
		ExcelUtil.export(response, "Algorithm warehouse table data" + DateUtil.time(), "Algorithm warehouse table data table", list, VlsAlgorithmRepositoryExcel.class);
	}

	/**
	 * Query所有enablealgorithmstorehouse
	 */
	@GetMapping("/enabled")
	@Operation(summary = "Query所有enablealgorithmstorehouse", description = "Get all algorithm repositories with status enabled")
	public R<List<AlgorithmRepository>> getEnabledRepositories() {
		log.info("Query所有enablealgorithmstorehouse");

		List<AlgorithmRepository> repositories = vlsAlgorithmRepositoryService.getEnabledRepositories();
		return R.data(repositories);
	}

	/**
	 * Query algorithm warehouse based on type
	 */
	@GetMapping("/type/{repositoryType}")
	@Operation(summary = "Query algorithm warehouse based on type", description = "Get the list of algorithm warehouses based on warehouse type")
	public R<List<AlgorithmRepository>> getRepositoriesByType(
		@Parameter(description = "Warehouse type", example = "extended") @PathVariable String repositoryType) {

		log.info("Query algorithm warehouse based on type: {}", repositoryType);

		List<AlgorithmRepository> repositories = vlsAlgorithmRepositoryService.getByRepositoryType(repositoryType);
		return R.data(repositories);
	}

	/**
	 * according toIDQuery algorithm warehouse details
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Query algorithm warehouse details", description = "according toIDGet algorithm warehouse details")
	public R<AlgorithmRepository> getRepositoryById(
		@Parameter(description = "storehouseID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Query algorithm warehouse details: ID={}", id);

		AlgorithmRepository repository = vlsAlgorithmRepositoryService.getById(id);
		if (repository == null) {
			return R.fail("Algorithm warehouse does not exist");
		}

		return R.data(repository);
	}

	/**
	 * Create algorithm warehouse
	 */
	@PostMapping
	@Operation(summary = "Create algorithm warehouse", description = "Add algorithm warehouse")
	public R<String> createRepository(@Valid @RequestBody AlgorithmRepository repository) {
		log.info("Create algorithm warehouse: {}", repository.getName());

		boolean success = vlsAlgorithmRepositoryService.createRepository(repository);
		if (success) {
			return R.success("Algorithm warehouse created successfully");
		} else {
			return R.fail("Algorithm warehouse creation failed, name may already exist");
		}
	}

	/**
	 * Update algorithm repository
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Update algorithm repository", description = "according toIDUpdate algorithm warehouse information")
	public R<String> updateRepository(
		@Parameter(description = "storehouseID", example = "1") @PathVariable @NotNull Long id,
		@Valid @RequestBody AlgorithmRepository repository) {

		log.info("Update algorithm repository: ID={}", id);

		repository.setId(id);
		boolean success = vlsAlgorithmRepositoryService.updateRepository(repository);

		if (success) {
			return R.success("Algorithm warehouse updated successfully");
		} else {
			return R.fail("Algorithm warehouse update failed");
		}
	}

	/**
	 * Delete algorithm repository
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete algorithm repository", description = "according toIDDelete algorithm repository(soft delete)")
	public R<String> deleteRepository(
		@Parameter(description = "storehouseID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Delete algorithm repository: ID={}", id);

		boolean success = vlsAlgorithmRepositoryService.deleteRepository(id);
		if (success) {
			return R.success("Algorithm warehouse deleted successfully");
		} else {
			return R.fail("Algorithm warehouse deletion failed, Basic preset algorithm library is not allowed to be deleted");
		}
	}

	/**
	 * Batch deletion of algorithm warehouse
	 */
	@DeleteMapping("/batch")
	@Operation(summary = "Batch deletion of algorithm warehouse", description = "according toIDList batch deletion algorithm warehouse")
	public R<String> batchDeleteRepositories(@RequestBody List<Long> ids) {
		log.info("Batch deletion of algorithm warehouse: IDs={}", ids);

		if (ids == null || ids.isEmpty()) {
			return R.fail("Please select the algorithm repository to delete");
		}

		boolean success = vlsAlgorithmRepositoryService.batchDeleteRepositories(ids);
		if (success) {
			return R.success("Algorithm warehouse batch deletion successful");
		} else {
			return R.fail("Algorithm warehouse batch deletion failed, Some warehouses are not allowed to be deleted");
		}
	}

	/**
	 * Update warehouse status
	 */
	@PutMapping("/{id}/status")
	@Operation(summary = "Update warehouse status", description = "Enable or disable algorithm repository")
	public R<String> updateRepositoryStatus(
		@Parameter(description = "storehouseID", example = "1") @PathVariable @NotNull Long id,
		@Parameter(description = "new status", example = "enabled") @RequestParam @NotNull String status) {

		log.info("Update algorithm warehouse status: ID={}, Status={}", id, status);

		boolean success = vlsAlgorithmRepositoryService.updateRepositoryStatus(id, status);
		if (success) {
			return R.success("Algorithm warehouse status updated successfully");
		} else {
			return R.fail("Algorithm warehouse status update failed");
		}
	}

	/**
	 * Update warehouse status in batches
	 */
	@PutMapping("/batch/status")
	@Operation(summary = "Update warehouse status in batches", description = "Enable or disable algorithm warehouses in batches")
	public R<String> batchUpdateRepositoryStatus(
		@RequestBody List<Long> ids,
		@Parameter(description = "new status", example = "enabled") @RequestParam @NotNull String status) {

		log.info("Batch update algorithm warehouse status: IDs={}, Status={}", ids, status);

		if (ids == null || ids.isEmpty()) {
			return R.fail("Please select the algorithm repository to update");
		}

		boolean success = vlsAlgorithmRepositoryService.batchUpdateRepositoryStatus(ids, status);
		if (success) {
			return R.success("Algorithm warehouse status batch update successful");
		} else {
			return R.fail("Algorithm warehouse status batch update failed");
		}
	}

	/**
	 * Statistical algorithm warehouse quantity
	 */
	@GetMapping("/count")
	@Operation(summary = "Statistical algorithm warehouse quantity", description = "Get the total number of algorithm warehouses")
	public R<Long> countRepositories() {
		log.info("Statistical algorithm warehouse quantity");

		Long count = vlsAlgorithmRepositoryService.countRepositories();
		return R.data(count);
	}

	/**
	 * Refresh warehouse algorithm quantity
	 */
	@PutMapping("/{id}/refresh-count")
	@Operation(summary = "Refresh warehouse algorithm quantity", description = "Recalculate and update the number of algorithms for the warehouse")
	public R<String> refreshAlgorithmCount(
		@Parameter(description = "storehouseID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Refresh algorithm warehouse algorithm number: ID={}", id);

		vlsAlgorithmRepositoryService.updateAlgorithmCount(id);
		return R.success("Algorithm quantity refreshed successfully");
	}

}
