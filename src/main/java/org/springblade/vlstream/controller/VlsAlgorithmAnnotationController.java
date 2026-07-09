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
import org.springblade.vlstream.excel.VlsAlgorithmAnnotationExcel;
import org.springblade.vlstream.pojo.entity.AlgorithmAnnotation;
import org.springblade.vlstream.pojo.vo.AlgorithmAnnotationVO;
import org.springblade.vlstream.service.IVlsAlgorithmAnnotationService;
import org.springblade.vlstream.wrapper.VlsAlgorithmAnnotationWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Algorithm annotation data table controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/vlsAlgorithmAnnotation")
@Tag(name = "Algorithm annotation data table", description = "Algorithm annotation data table interface")
public class VlsAlgorithmAnnotationController extends BladeController {

	private final IVlsAlgorithmAnnotationService vlsAlgorithmAnnotationService;

	/**
	 * Algorithm annotation data table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingvlsAlgorithmAnnotation")
	public R<AlgorithmAnnotationVO> detail(AlgorithmAnnotation vlsAlgorithmAnnotation) {
		AlgorithmAnnotation detail = vlsAlgorithmAnnotationService.getOne(Condition.getQueryWrapper(vlsAlgorithmAnnotation));
		return R.data(VlsAlgorithmAnnotationWrapper.build().entityVO(detail));
	}

	/**
	 * Algorithm annotation data table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingvlsAlgorithmAnnotation")
	public R<IPage<AlgorithmAnnotationVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAlgorithmAnnotation, Query query) {
		IPage<AlgorithmAnnotation> pages = vlsAlgorithmAnnotationService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsAlgorithmAnnotation, AlgorithmAnnotation.class));
		return R.data(VlsAlgorithmAnnotationWrapper.build().pageVO(pages));
	}


	/**
	 * Algorithm annotation data table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description = "incomingvlsAlgorithmAnnotation")
	public R<IPage<AlgorithmAnnotationVO>> page(AlgorithmAnnotationVO vlsAlgorithmAnnotation, Query query) {
		IPage<AlgorithmAnnotationVO> pages = vlsAlgorithmAnnotationService.selectVlsAlgorithmAnnotationPage(Condition.getPage(query), vlsAlgorithmAnnotation);
		return R.data(pages);
	}

	/**
	 * Algorithm annotation data table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingvlsAlgorithmAnnotation")
	public R save(@Valid @RequestBody AlgorithmAnnotation vlsAlgorithmAnnotation) {
		return R.status(vlsAlgorithmAnnotationService.save(vlsAlgorithmAnnotation));
	}

	/**
	 * Algorithm annotation data table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingvlsAlgorithmAnnotation")
	public R update(@Valid @RequestBody AlgorithmAnnotation vlsAlgorithmAnnotation) {
		return R.status(vlsAlgorithmAnnotationService.updateById(vlsAlgorithmAnnotation));
	}

	/**
	 * Algorithm annotation data table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingvlsAlgorithmAnnotation")
	public R submit(@Valid @RequestBody AlgorithmAnnotation vlsAlgorithmAnnotation) {
		return R.status(vlsAlgorithmAnnotationService.saveOrUpdate(vlsAlgorithmAnnotation));
	}

	/**
	 * Algorithm annotation data table delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsAlgorithmAnnotationService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsAlgorithmAnnotation")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description = "incomingvlsAlgorithmAnnotation")
	public void exportVlsAlgorithmAnnotation(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAlgorithmAnnotation, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<AlgorithmAnnotation> queryWrapper = Condition.getQueryWrapper(vlsAlgorithmAnnotation, AlgorithmAnnotation.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsAlgorithmAnnotationEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsAlgorithmAnnotationEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsAlgorithmAnnotationExcel> list = vlsAlgorithmAnnotationService.exportVlsAlgorithmAnnotation(queryWrapper);
		ExcelUtil.export(response, "Algorithm annotation data table data" + DateUtil.time(), "Algorithm annotation data table data table", list, VlsAlgorithmAnnotationExcel.class);
	}

	/**
	 * Query annotation list based on annotation type
	 */
	@GetMapping("/type/{annotationType}")
	@Operation(summary = "Query annotation list based on annotation type", description = "Get all annotations of a specified type")
	public R<List<AlgorithmAnnotation>> getAnnotationsByType(
		@Parameter(description = "Dimension type", example = "object_detection") @PathVariable String annotationType) {

		log.info("Query annotation list based on annotation type: {}", annotationType);

		List<AlgorithmAnnotation> annotations = vlsAlgorithmAnnotationService.getByAnnotationType(annotationType);
		return R.data(annotations);
	}

	/**
	 * Query annotation list based on annotation status
	 */
	@GetMapping("/status/{annotationStatus}")
	@Operation(summary = "Query annotation list based on annotation status", description = "get指定stateof所有mark")
	public R<List<AlgorithmAnnotation>> getAnnotationsByStatus(
		@Parameter(description = "Annotation status", example = "partial") @PathVariable String annotationStatus) {

		log.info("Query annotation list based on annotation status: {}", annotationStatus);

		List<AlgorithmAnnotation> annotations = vlsAlgorithmAnnotationService.getByAnnotationStatus(annotationStatus);
		return R.data(annotations);
	}

	/**
	 * according toIDQuery label details
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Query label details", description = "according toIDGet label details")
	public R<AlgorithmAnnotation> getAnnotationById(
		@Parameter(description = "markID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Query label details: ID={}", id);

		AlgorithmAnnotation annotation = vlsAlgorithmAnnotationService.getById(id);
		if (annotation == null) {
			return R.fail("Annotation does not exist");
		}

		return R.data(annotation);
	}

	/**
	 * Create algorithm annotations
	 */
	@PostMapping
	@Operation(summary = "Create algorithm annotations", description = "Add algorithm annotation")
	public R<String> createAnnotation(@Valid @RequestBody AlgorithmAnnotation annotation) {
		log.info("Create algorithm annotations: {}", annotation.getAnnotationName());

		boolean success = vlsAlgorithmAnnotationService.createAnnotation(annotation);
		if (success) {
			return R.data("Label created successfully");
		} else {
			return R.fail("Label creation failed, name may already exist");
		}
	}

	/**
	 * Update algorithm annotation
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Update algorithm annotation", description = "according toIDUpdate label information")
	public R<String> updateAnnotation(
		@Parameter(description = "markID", example = "1") @PathVariable @NotNull Long id,
		@Valid @RequestBody AlgorithmAnnotation annotation) {

		log.info("Update algorithm annotation: ID={}", id);

		annotation.setId(id);
		boolean success = vlsAlgorithmAnnotationService.updateAnnotation(annotation);

		if (success) {
			return R.data("Annotation updated successfully");
		} else {
			return R.fail("Label update failed");
		}
	}

	/**
	 * Delete algorithm annotation
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete algorithm annotation", description = "according toIDDelete callout(soft delete)")
	public R<String> deleteAnnotation(
		@Parameter(description = "markID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Delete algorithm annotation: ID={}", id);

		boolean success = vlsAlgorithmAnnotationService.deleteAnnotation(id);
		if (success) {
			return R.success("Annotation deleted successfully");
		} else {
			return R.fail("Annotation deletion failed");
		}
	}

	/**
	 * Batch deletion of algorithm annotations
	 */
	@DeleteMapping("/batch")
	@Operation(summary = "Batch deletion of algorithm annotations", description = "according toIDDelete labels in batches from list")
	public R<String> batchDeleteAnnotations(@RequestBody List<Long> ids) {
		log.info("Batch deletion of algorithm annotations: IDs={}", ids);

		if (ids == null || ids.isEmpty()) {
			return R.fail("Please select the annotation to delete");
		}

		boolean success = vlsAlgorithmAnnotationService.batchDeleteAnnotations(ids);
		if (success) {
			return R.success("Mark batch deletion successful");
		} else {
			return R.fail("Annotation batch deletion failed");
		}
	}

	/**
	 * Update labeling progress
	 */
	@PutMapping("/{id}/progress")
	@Operation(summary = "Update labeling progress", description = "Update annotated progress information")
	public R<String> updateAnnotationProgress(
		@Parameter(description = "markID", example = "1") @PathVariable @NotNull Long id,
		@Parameter(description = "Quantity marked", example = "50") @RequestParam @NotNull Integer annotatedCount) {

		log.info("Update labeling progress: ID={}, AnnotatedCount={}", id, annotatedCount);

		boolean success = vlsAlgorithmAnnotationService.updateAnnotationProgress(id, annotatedCount);
		if (success) {
			return R.success("Annotation progress updated successfully");
		} else {
			return R.fail("Annotation progress update failed");
		}
	}

	/**
	 * Update annotation status in batches
	 */
	@PutMapping("/batch/status")
	@Operation(summary = "Update annotation status in batches", description = "Update label status in batches")
	public R<String> batchUpdateAnnotationStatus(
		@RequestBody List<Long> ids,
		@Parameter(description = "Annotation status", example = "completed") @RequestParam @NotNull String annotationStatus) {

		log.info("Update annotation status in batches: IDs={}, Status={}", ids, annotationStatus);

		if (ids == null || ids.isEmpty()) {
			return R.fail("Please select annotations to update");
		}

		boolean success = vlsAlgorithmAnnotationService.batchUpdateAnnotationStatus(ids, annotationStatus);
		if (success) {
			return R.success("Annotation status batch update successful");
		} else {
			return R.fail("Label status batch update failed");
		}
	}

	/**
	 * Start labeling task
	 */
	@PostMapping("/{id}/start")
	@Operation(summary = "Start labeling task", description = "Start the specified labeling task")
	public R<String> startAnnotationTask(
		@Parameter(description = "markID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Start labeling task: ID={}", id);

		boolean success = vlsAlgorithmAnnotationService.startAnnotationTask(id);
		if (success) {
			return R.success("The labeling task started successfully");
		} else {
			return R.fail("Labeling task failed to start");
		}
	}

	/**
	 * Complete the labeling task
	 */
	@PostMapping("/{id}/complete")
	@Operation(summary = "Complete the labeling task", description = "Complete designated labeling tasks")
	public R<String> completeAnnotationTask(
		@Parameter(description = "markID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Complete the labeling task: ID={}", id);

		boolean success = vlsAlgorithmAnnotationService.completeAnnotationTask(id);
		if (success) {
			return R.success("Labeling task completed successfully");
		} else {
			return R.fail("Annotation task failed to complete");
		}
	}

	/**
	 * Reset labeling task
	 */
	@PostMapping("/{id}/reset")
	@Operation(summary = "Reset labeling task", description = "Resets the specified labeling task")
	public R<String> resetAnnotationTask(
		@Parameter(description = "markID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Reset labeling task: ID={}", id);

		boolean success = vlsAlgorithmAnnotationService.resetAnnotationTask(id);
		if (success) {
			return R.success("Labeling task reset successfully");
		} else {
			return R.fail("Annotation task reset failed");
		}
	}

	/**
	 * Export annotation data
	 */
	@PostMapping("/{id}/export")
	@Operation(summary = "Export annotation data", description = "Export data for specified annotations")
	public void exportAnnotationData(@Parameter(description = "markID", example = "1") @PathVariable @NotNull Long id, HttpServletResponse response) {
		log.info("Download dataset zip request, id={}", id);
		vlsAlgorithmAnnotationService.downloadAnnotationDataset(id, response);
	}

	/**
	 * Import annotation data
	 */
	@PostMapping("/{id}/import")
	@Operation(summary = "Import annotation data", description = "Import annotation data")
	public R<Map<String, Object>> importAnnotationData(
		@Parameter(description = "markID", example = "1") @PathVariable @NotNull Long id,
		@Parameter(description = "data path") @RequestParam @NotNull String dataPath) {

		log.info("Import annotation data: ID={}, DataPath={}", id, dataPath);

		Map<String, Object> result = vlsAlgorithmAnnotationService.importAnnotationData(id, dataPath);
		if (result != null) {
			return R.data(result);
		} else {
			return R.fail("Label data import failed");
		}
	}

	/**
	 * Verify annotation data
	 */
	/**
	 * Import annotation dataset zip.
	 */
	@PostMapping(value = "/{id}/import-zip", consumes = "multipart/form-data")
	@Operation(summary = "Import annotation dataset zip", description = "Import annotation dataset zip")
	public R<Map<String, Object>> importAnnotationZip(
		@Parameter(description = "Annotation ID", example = "1") @PathVariable @NotNull Long id,
		@Parameter(description = "Zip dataset file") @RequestPart("file") @NotNull MultipartFile zipFile) {

		String originalFileName = zipFile == null ? null : zipFile.getOriginalFilename();
		log.info("Import annotation dataset zip request: id={}, fileName={}", id, originalFileName);

		try {
			Map<String, Object> result = vlsAlgorithmAnnotationService.importAnnotationDatasetZip(id, zipFile);
			if (result == null || !Boolean.TRUE.equals(result.get("success"))) {
				String message = result == null ? "Import failed" : String.valueOf(result.get("message"));
				return R.fail(message);
			}
			return R.data(result);
		} catch (Exception importException) {
			log.error("Import annotation dataset zip failed: id={}, fileName={}, error={}",
				id, originalFileName, importException.getMessage(), importException);
			return R.fail("Import failed: " + importException.getMessage());
		}
	}

	@PostMapping("/{id}/validate")
	@Operation(summary = "Verify annotation data", description = "verify指定markofdata质量")
	public R<Map<String, Object>> validateAnnotationData(
		@Parameter(description = "markID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Verify annotation data: ID={}", id);

		Map<String, Object> result = vlsAlgorithmAnnotationService.validateAnnotationData(id);
		if (result != null) {
			return R.data(result);
		} else {
			return R.fail("Annotation data validation failed");
		}
	}

	/**
	 * Get annotation type statistics
	 */
	@GetMapping("/statistics/type")
	@Operation(summary = "Get annotation type statistics", description = "Get statistics on the number of annotations of various types")
	public R<List<Map<String, Object>>> getAnnotationTypeStatistics() {
		log.info("Get annotation type statistics");

		List<Map<String, Object>> statistics = vlsAlgorithmAnnotationService.getAnnotationTypeStatistics();
		return R.data(statistics);
	}

	/**
	 * Get annotation status statistics
	 */
	@GetMapping("/statistics/status")
	@Operation(summary = "Get annotation status statistics", description = "Get statistics on the number of annotations in each status")
	public R<List<Map<String, Object>>> getAnnotationStatusStatistics() {
		log.info("Get annotation status statistics");

		List<Map<String, Object>> statistics = vlsAlgorithmAnnotationService.getAnnotationStatusStatistics();
		return R.data(statistics);
	}

	/**
	 * Get annotation progress statistics
	 */
	@GetMapping("/statistics/progress")
	@Operation(summary = "Get annotation progress statistics", description = "Obtain the statistics of the number of annotations in each progress interval")
	public R<List<Map<String, Object>>> getProgressStatistics() {
		log.info("Get annotation progress statistics");

		List<Map<String, Object>> statistics = vlsAlgorithmAnnotationService.getProgressStatistics();
		return R.data(statistics);
	}

	/**
	 * Get annotation workload statistics
	 */
	@GetMapping("/statistics/workload")
	@Operation(summary = "Get annotation workload statistics", description = "Get overall statistics on annotation workload")
	public R<Map<String, Object>> getWorkloadStatistics() {
		log.info("Get annotation workload statistics");

		Map<String, Object> statistics = vlsAlgorithmAnnotationService.getWorkloadStatistics();
		return R.data(statistics);
	}

	/**
	 * Save annotation data to dataset file
	 */
	@PostMapping("/{id}/save-dataset")
	@Operation(summary = "Save annotation data to dataset", description = "Save annotation data to dataset file and update database path")
	public R<String> saveAnnotationToDataset(@Parameter(description = "markID", example = "1") @PathVariable @NotNull Long id) {

		log.info("Save annotation data to dataset: ID={}", id);

		boolean success = vlsAlgorithmAnnotationService.saveAnnotationToDataset(id);
		if (success) {
			return R.success("Annotation data is saved to the dataset successfully");
		} else {
			return R.fail("Failed to save annotation data to dataset");
		}
	}

}
