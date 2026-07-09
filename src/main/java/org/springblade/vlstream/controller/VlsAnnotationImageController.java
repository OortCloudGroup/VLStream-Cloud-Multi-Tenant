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
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.vlstream.excel.VlsAnnotationImageExcel;
import org.springblade.vlstream.pojo.entity.AnnotationImage;
import org.springblade.vlstream.pojo.vo.AnnotationImageVO;
import org.springblade.vlstream.service.IVlsAnnotationImageService;
import org.springblade.vlstream.wrapper.VlsAnnotationImageWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Label image information table controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/vlsAnnotationImage")
@Tag(name = "Label image information table", description = "Annotation picture information table interface")
public class VlsAnnotationImageController extends BladeController {

	private final IVlsAnnotationImageService vlsAnnotationImageService;

	/**
	 * Label image information table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description  = "incomingvlsAnnotationImage")
	public R<AnnotationImageVO> detail(AnnotationImage vlsAnnotationImage) {
		AnnotationImage detail = vlsAnnotationImageService.getOne(Condition.getQueryWrapper(vlsAnnotationImage));
		return R.data(VlsAnnotationImageWrapper.build().entityVO(detail));
	}

	/**
	 * Label image information table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description  = "incomingvlsAnnotationImage")
	public R<IPage<AnnotationImageVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAnnotationImage, Query query) {
		IPage<AnnotationImage> pages = vlsAnnotationImageService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsAnnotationImage, AnnotationImage.class));
		return R.data(VlsAnnotationImageWrapper.build().pageVO(pages));
	}


	/**
	 * Label image information table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description  = "incomingvlsAnnotationImage")
	public R<IPage<AnnotationImageVO>> page(AnnotationImageVO vlsAnnotationImage, Query query) {
		IPage<AnnotationImageVO> pages = vlsAnnotationImageService.selectVlsAnnotationImagePage(Condition.getPage(query), vlsAnnotationImage);
		return R.data(pages);
	}

	/**
	 * Label image information table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description  = "incomingvlsAnnotationImage")
	public R save(@Valid @RequestBody AnnotationImage vlsAnnotationImage) {
		return R.status(vlsAnnotationImageService.save(vlsAnnotationImage));
	}

	/**
	 * Label image information table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description  = "incomingvlsAnnotationImage")
	public R update(@Valid @RequestBody AnnotationImage vlsAnnotationImage) {
		return R.status(vlsAnnotationImageService.updateById(vlsAnnotationImage));
	}

	/**
	 * Label image information table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description  = "incomingvlsAnnotationImage")
	public R submit(@Valid @RequestBody AnnotationImage vlsAnnotationImage) {
		return R.status(vlsAnnotationImageService.saveOrUpdate(vlsAnnotationImage));
	}

	/**
	 * Label image information table delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description  = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsAnnotationImageService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsAnnotationImage")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description  = "incomingvlsAnnotationImage")
	public void exportVlsAnnotationImage(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAnnotationImage, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<AnnotationImage> queryWrapper = Condition.getQueryWrapper(vlsAnnotationImage, AnnotationImage.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsAnnotationImageEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsAnnotationImageEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsAnnotationImageExcel> list = vlsAnnotationImageService.exportVlsAnnotationImage(queryWrapper);
		ExcelUtil.export(response, "Label image information table data" + DateUtil.time(), "Label image information table data table", list, VlsAnnotationImageExcel.class);
	}

	/**
	 * Upload annotated images
	 */
	@PostMapping("/upload")
	public ResponseEntity<?> uploadImages(
		@RequestPart("files") MultipartFile[] files,
		@RequestParam("annotationId") Long annotationId) {
		try {
			List<AnnotationImage> images = vlsAnnotationImageService.uploadImages(files, annotationId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Image uploaded successfully");
			response.put("data", images);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", "Image upload failed: " + e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	/**
	 * Get all images of the dataset
	 */
	@GetMapping("/dataset/{annotationId}")
	public ResponseEntity<?> getImagesByDataset(@PathVariable Long annotationId) {
		try {
			List<AnnotationImage> images = vlsAnnotationImageService.getImagesByDataset(annotationId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", images);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", "Failed to get image list: " + e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	/**
	 * Get image details
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getImageById(@PathVariable Long id) {
		try {
			AnnotationImage image = vlsAnnotationImageService.getImageById(id);
			if (image != null) {
				Map<String, Object> response = new HashMap<>();
				response.put("success", true);
				response.put("data", image);
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", "Failed to obtain image details: " + e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	/**
	 * Update image annotation information
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updateImage(@PathVariable Long id, @RequestBody AnnotationImage image) {
		try {
			image.setId(id);
			AnnotationImage updatedImage = vlsAnnotationImageService.updateImage(image);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Picture information updated successfully");
			response.put("data", updatedImage);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", "Failed to update picture information: " + e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	/**
	 * Delete picture
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteImage(@PathVariable Long id) {
		try {
			vlsAnnotationImageService.deleteImage(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Picture deleted successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", "Failed to delete picture: " + e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	/**
	 * Delete pictures in batches
	 */
	@DeleteMapping("/batch")
	public ResponseEntity<?> batchDeleteImages(@RequestBody List<Long> ids) {
		try {
			vlsAnnotationImageService.batchDeleteImages(ids);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Batch deletion successful");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", "Batch deletion failed: " + e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	/**
	 * Get dataset statistics
	 */
	@GetMapping("/dataset/{datasetId}/stats")
	public ResponseEntity<?> getDatasetStats(@PathVariable Long datasetId) {
		try {
			Map<String, Object> stats = vlsAnnotationImageService.getDatasetStats(datasetId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", stats);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", "Failed to obtain statistics: " + e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	/**
	 * Save image information in batches toannotation_imagesurface
	 *
	 * @param annotationImages Picture information list
	 * @return Save results
	 */
	@PostMapping("/images/batch")
	public ResponseEntity<?> batchSaveImages(@RequestBody List<AnnotationImage> annotationImages) {
		try {
			log.info("Save image information in batches, quantity: {}", annotationImages.size());

			boolean success = vlsAnnotationImageService.batchSaveImages(annotationImages);
			if (success) {
				Map<String, Object> response = new HashMap<>();
				response.put("success", true);
				response.put("message", "Successfully saved image information in batches");
				response.put("data", annotationImages.size());
				return ResponseEntity.ok(response);
			} else {
				Map<String, Object> errorResponse = new HashMap<>();
				errorResponse.put("success", false);
				errorResponse.put("message", "Failed to save image information in batches");
				return ResponseEntity.badRequest().body(errorResponse);
			}
		} catch (Exception e) {
			log.error("Failed to save image information in batches", e);
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", "Failed to save image information in batches: " + e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

}
