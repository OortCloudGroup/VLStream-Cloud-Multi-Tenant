package org.springblade.vlstream.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.vlstream.enums.AlgorithmAnnotationStatusEnum;
import org.springblade.vlstream.enums.AlgorithmAnnotationTypeEnum;
import org.springblade.vlstream.excel.VlsAnnotationInstanceExcel;
import org.springblade.vlstream.pojo.entity.AlgorithmAnnotation;
import org.springblade.vlstream.pojo.entity.AnnotationInstance;
import org.springblade.vlstream.pojo.vo.AnnotationInstanceVO;
import org.springblade.vlstream.service.IVlsAlgorithmAnnotationService;
import org.springblade.vlstream.service.IVlsAnnotationInstanceService;
import org.springblade.vlstream.wrapper.VlsAnnotationInstanceWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Label instance entity class controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/vlsAnnotationInstance")
@Tag(name = "Label instance entity class", description = "Annotate instance entity class interface")
public class VlsAnnotationInstanceController extends BladeController {

	private final IVlsAnnotationInstanceService vlsAnnotationInstanceService;
	private final IVlsAlgorithmAnnotationService algorithmAnnotationService;

	/**
	 * Label instance entity class Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingvlsAnnotationInstance")
	public R<AnnotationInstanceVO> detail(AnnotationInstance vlsAnnotationInstance) {
		AnnotationInstance detail = vlsAnnotationInstanceService.getOne(Condition.getQueryWrapper(vlsAnnotationInstance));
		return R.data(VlsAnnotationInstanceWrapper.build().entityVO(detail));
	}

	/**
	 * Label instance entity class Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingvlsAnnotationInstance")
	public R<IPage<AnnotationInstanceVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAnnotationInstance, Query query) {
		IPage<AnnotationInstance> pages = vlsAnnotationInstanceService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsAnnotationInstance, AnnotationInstance.class));
		return R.data(VlsAnnotationInstanceWrapper.build().pageVO(pages));
	}


	/**
	 * Label instance entity class Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description = "incomingvlsAnnotationInstance")
	public R<IPage<AnnotationInstanceVO>> page(AnnotationInstanceVO vlsAnnotationInstance, Query query) {
		IPage<AnnotationInstanceVO> pages = vlsAnnotationInstanceService.selectVlsAnnotationInstancePage(Condition.getPage(query), vlsAnnotationInstance);
		return R.data(pages);
	}

	/**
	 * Label instance entity class New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingvlsAnnotationInstance")
	public R save(@Valid @RequestBody AnnotationInstance vlsAnnotationInstance) {
		return R.status(vlsAnnotationInstanceService.save(vlsAnnotationInstance));
	}

	/**
	 * Label instance entity class Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingvlsAnnotationInstance")
	public R update(@Valid @RequestBody AnnotationInstance vlsAnnotationInstance) {
		return R.status(vlsAnnotationInstanceService.updateById(vlsAnnotationInstance));
	}

	/**
	 * Label instance entity class Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingvlsAnnotationInstance")
	public R submit(@Valid @RequestBody AnnotationInstance vlsAnnotationInstance) {
		return R.status(vlsAnnotationInstanceService.saveOrUpdate(vlsAnnotationInstance));
	}

	/**
	 * Label instance entity class delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsAnnotationInstanceService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsAnnotationInstance")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description = "incomingvlsAnnotationInstance")
	public void exportVlsAnnotationInstance(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAnnotationInstance, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<AnnotationInstance> queryWrapper = Condition.getQueryWrapper(vlsAnnotationInstance, AnnotationInstance.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsAnnotationInstanceEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsAnnotationInstanceEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsAnnotationInstanceExcel> list = vlsAnnotationInstanceService.exportVlsAnnotationInstance(queryWrapper);
		ExcelUtil.export(response, "Label instance entity class data" + DateUtil.time(), "Label instance entity class data table", list, VlsAnnotationInstanceExcel.class);
	}


	/**
	 * Get the list of annotation instances of the image
	 *
	 * @param annotationId Label itemsID
	 * @param imageName    Picture name
	 * @return Label instance list
	 */
	@GetMapping("/{annotationId}/instances")
	public R<List<AnnotationInstance>> getAnnotationInstances(@PathVariable Long annotationId,
															  @RequestParam String imageName) {
		try {
			List<AnnotationInstance> instances = vlsAnnotationInstanceService.getByAnnotationIdAndImageName(annotationId, imageName);
			return R.data(instances);
		} catch (Exception e) {
			log.error("Failed to obtain label instance", e);
			return R.fail("Failed to obtain label instance: " + e.getMessage());
		}
	}

	/**
	 * Get a list of all annotation instances of an annotation project
	 *
	 * @param annotationId Label itemsID
	 * @return Label instance list
	 */
	@GetMapping("/{annotationId}/instances/all")
	public R<List<AnnotationInstanceVO>> getAllAnnotationInstances(@PathVariable Long annotationId) {
		try {
			List<AnnotationInstance> instances = vlsAnnotationInstanceService.getByAnnotationId(annotationId);
			return R.data(VlsAnnotationInstanceWrapper.build().listVO(instances));
		} catch (Exception e) {
			log.error("Failed to obtain all instances of annotation items", e);
			return R.fail("Failed to obtain all instances of annotation items: " + e.getMessage());
		}
	}

	/**
	 * Save annotation instance
	 *
	 * @param annotationId Label itemsID
	 * @param requestBody  Request body
	 * @return Saved callout instance
	 */
	@PostMapping("/{annotationId}/instances")
	public R<AnnotationInstance> saveAnnotationInstance(@PathVariable Long annotationId,
													@RequestBody Map<String, Object> requestBody) {
		try {
			Long labelId = parseLong(requestBody.get("labelId"));
			Long imageId = parseLong(requestBody.get("imageId"));
			String annotationType = requestBody.get("annotationType") == null ? null : requestBody.get("annotationType").toString();
			String annotationData = (String) requestBody.get("annotationData");

			if (labelId == null || imageId == null) {
				return R.fail("labelIdorimageIdcannot be empty");
			}
			if (StringUtils.isBlank(annotationType)) {
				return R.fail("annotationTypecannot be empty");
			}
			AlgorithmAnnotationTypeEnum typeEnum = AlgorithmAnnotationTypeEnum.of(annotationType);
			if (typeEnum == null) {
				return R.fail("annotationTypeillegal");
			}

			AnnotationInstance instance = vlsAnnotationInstanceService.saveAnnotation(annotationId, labelId, imageId, typeEnum, annotationData);
			return R.data(instance);
		} catch (Exception e) {
			log.error("Failed to save callout instance", e);
			return R.fail("Failed to save callout instance: " + e.getMessage());
		}
	}

	/**
	 * Annotation examples of saving images in batches
	 *
	 * @param annotationId Label itemsID
	 * @param requestBody  Request body
	 * @return Save results
	 */
	@PostMapping("/{annotationId}/instances/batch")
	public R<Boolean> batchSaveAnnotationInstances(@PathVariable Long annotationId,
													   @RequestBody Map<String, Object> requestBody) {
		try {
			Long imageId = parseLong(requestBody.get("imageId"));
			Object instancesObj = requestBody.get("instances");
			if (imageId == null) {
				return R.fail("imageIdcannot be empty");
			}
			if (!(instancesObj instanceof List<?> instancesData) || instancesData.isEmpty()) {
				return R.fail("instancescannot be empty");
			}

			List<AnnotationInstance> instances = new ArrayList<>();
			for (Object rawItem : instancesData) {
				if (!(rawItem instanceof Map<?, ?> data)) {
					return R.fail("instancesThe format is illegal");
				}
				Long labelId = parseLong(data.get("labelId"));
				Object annotationTypeObj = data.get("annotationType");
				String annotationType = annotationTypeObj == null ? null : annotationTypeObj.toString();
				if (labelId == null) {
					return R.fail("labelIdcannot be empty");
				}
				if (StringUtils.isBlank(annotationType)) {
					return R.fail("annotationTypecannot be empty");
				}
				AlgorithmAnnotationTypeEnum typeEnum = AlgorithmAnnotationTypeEnum.of(annotationType);
				if (typeEnum == null) {
					return R.fail("annotationTypeillegal");
				}
				AnnotationInstance instance = new AnnotationInstance();
				instance.setAnnotationId(annotationId);
				instance.setLabelId(labelId);
				instance.setImageId(imageId);
				instance.setAnnotationType(typeEnum);
				Object annotationDataObj = data.get("annotationData");
				instance.setAnnotationData(annotationDataObj == null ? null : annotationDataObj.toString());
				instances.add(instance);
			}

			boolean success = vlsAnnotationInstanceService.batchSaveAnnotations(annotationId, imageId, instances);

			// Update label statistics
			try {
				int annotatedCount = Math.toIntExact(vlsAnnotationInstanceService.count(new QueryWrapper<AnnotationInstance>()
					.eq("annotation_id", annotationId)
					.eq("is_deleted", 1)));

				AlgorithmAnnotation annotation = algorithmAnnotationService.getById(annotationId);
				if (annotation != null) {
					int totalCount = annotation.getTotalCount() == null ? annotatedCount : annotation.getTotalCount();
					int progress = calculateProgress(annotatedCount, totalCount);

					annotation.setAnnotatedCount(annotatedCount);
					annotation.setTotalCount(totalCount);
					annotation.setProgress(progress);
					annotation.setAnnotationStatus(AlgorithmAnnotationStatusEnum.of(calculateAnnotationStatus(progress)));
					algorithmAnnotationService.updateById(annotation);
				}
			} catch (Exception statEx) {
				log.warn("Failed to update statistics after batch saving: annotationId={}, error={}", annotationId, statEx.getMessage());
			}

			return R.data(success);
		} catch (Exception e) {
			log.error("Failed to save labeling instances in batches", e);
			return R.fail("Failed to save labeling instances in batches: " + e.getMessage());
		}
	}

	/**
	 * Delete annotation instance
	 *
	 * @param instanceId ExampleID
	 * @return Delete results
	 */
	@DeleteMapping("/instances/{instanceId}")
	public R<Boolean> deleteAnnotationInstance(@PathVariable Long instanceId) {
		log.info("Request to delete annotation instance received: instanceId={}", instanceId);
		try {
			boolean success = vlsAnnotationInstanceService.deleteAnnotation(instanceId);
			log.info("Delete label instance results: instanceId={}, success={}", instanceId, success);
			return R.data(success);
		} catch (Exception e) {
			log.error("Failed to delete callout instance: instanceId={}", instanceId, e);
			return R.fail("Failed to delete callout instance: " + e.getMessage());
		}
	}

	/**
	 * Delete label instances in batches
	 *
	 * @param instanceIds ExampleIDlist
	 * @return Delete results
	 */
	@DeleteMapping("/instances/batch")
	public R<String> batchDeleteAnnotationInstances(@RequestBody List<Long> instanceIds) {
		log.info("Delete label instances in batches: IDs={}", instanceIds);

		if (instanceIds == null || instanceIds.isEmpty()) {
			return R.fail("ExampleIDList cannot be empty");
		}

		try {
			int totalDeleted = 0;
			for (Long instanceId : instanceIds) {
				boolean success = vlsAnnotationInstanceService.deleteAnnotation(instanceId);
				if (success) {
					totalDeleted++;
				}
			}

			log.info("Batch deletion completed, successfully deleted {} instances", totalDeleted);
			return R.success("Batch deletion successful, Total deleted " + totalDeleted + " instances");
		} catch (Exception e) {
			log.error("Failed to delete label instances in batches", e);
			return R.fail("Batch deletion failed: " + e.getMessage());
		}
	}

	/**
	 * Batch delete annotation instances and related data by image names.
	 *
	 * @param params request body containing annotationId and imageNames/imageName
	 * @return deletion result message
	 */
	@DeleteMapping("/instances/by-image")
	public R<String> deleteAnnotationInstancesByImage(@RequestBody Map<String, Object> params) {
		Object annotationIdObj = params.get("annotationId");
		if (annotationIdObj == null) {
			return R.fail("annotationId cannot be null");
		}
		Long annotationId = parseLong(annotationIdObj);
		if (annotationId == null) {
			return R.fail("annotationIdillegal");
		}

		List<Long> imageIds = new ArrayList<>();
		Object imageIdsObj = params.get("imageIds");
		if (imageIdsObj instanceof List<?>) {
			List<Long> finalImageIds = imageIds;
			((List<?>) imageIdsObj).forEach(item -> {
				if (item != null) {
					Long imageId = parseLong(item);
					if (imageId != null) {
						finalImageIds.add(imageId);
					}
				}
			});
		}

		imageIds = imageIds.stream()
			.filter(id -> id != null)
			.distinct()
			.collect(Collectors.toList());

		if (imageIds.isEmpty()) {
			return R.fail("imageNames cannot be empty");
		}

		log.info("Batch deleting annotation data: annotationId={}, imageNames={}", annotationId, imageIds);

		List<Long> failedImageNames = new ArrayList<>();
		for (Long imageId : imageIds) {
			try {
				boolean success = vlsAnnotationInstanceService.deleteImageAndRelatedData(annotationId, imageId);
				if (success) {
					log.info("Deleted annotation data for image: annotationId={}, imageName={}", annotationId, imageId);
				} else {
					log.warn("Delete annotation data failed for image: annotationId={}, imageName={}", annotationId, imageId);
					failedImageNames.add(imageId);
				}
			} catch (Exception e) {
				log.error("Exception deleting annotation data: annotationId={}, imageName={}", annotationId, imageId, e);
				failedImageNames.add(imageId);
			}
		}

		if (failedImageNames.isEmpty()) {
			return R.success("Deleted annotation data for " + imageIds.size() + " images");
		}

		String errorMsg = "Failed to delete images: " + StringUtils.join(", ", failedImageNames);
		if (failedImageNames.size() == imageIds.size()) {
			return R.fail(errorMsg);
		}
		return R.fail(errorMsg + "; others deleted");
	}

	/**
	 * Calculate labeling progress(0-100)
	 */
	private int calculateProgress(Integer annotatedCount, Integer totalCount) {
		if (totalCount == null || totalCount == 0) {
			return 0;
		}
		if (annotatedCount == null) {
			return 0;
		}
		return Math.min(100, (annotatedCount * 100) / totalCount);
	}

	/**
	 * Calculate annotation status based on progress
	 */
	private String calculateAnnotationStatus(int progress) {
		if (progress == 0) {
			return "none";
		} else if (progress < 100) {
			return "partial";
		} else {
			return "completed";
		}
	}

	private Long parseLong(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Number number) {
			return number.longValue();
		}
		try {
			return Long.valueOf(value.toString());
		} catch (NumberFormatException numberFormatException) {
			return null;
		}
	}

}
