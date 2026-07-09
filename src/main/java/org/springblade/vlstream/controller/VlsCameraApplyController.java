package org.springblade.vlstream.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.vlstream.pojo.dto.*;
import org.springblade.vlstream.pojo.vo.CameraApplyRecordVO;
import org.springblade.vlstream.service.IVlsCameraApplyRecordService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/vlsCameraApply")
@Tag(name = "Camera application", description = "Camera application form interface")
public class VlsCameraApplyController extends BladeController {

	private final IVlsCameraApplyRecordService vlsCameraApplyRecordService;

	/**
	 * Submit application for camera use
	 */
	@PostMapping("/camera-apply/submit")
	@ApiOperationSupport(order = 31)
	@Operation(summary = "Camera use application", description = "Submit camera application record")
	public R<Boolean> submitCameraApply(@Valid @RequestBody CameraApplySubmitDTO cameraApplySubmitDTO) {
		return R.data(vlsCameraApplyRecordService.submit(cameraApplySubmitDTO));
	}

	/**
	 * Application for camera use approved
	 */
	@PostMapping("/camera-apply/approve")
	@ApiOperationSupport(order = 32)
	@Operation(summary = "Camera application approved", description = "After approval, the status changes toapproved")
	public R<Boolean> approveCameraApply(@Valid @RequestBody CameraApplyApproveDTO cameraApplyApproveDTO) {
		return R.data(vlsCameraApplyRecordService.approve(cameraApplyApproveDTO));
	}

	/**
	 * Camera use application approval rejected
	 */
	@PostMapping("/camera-apply/reject")
	@ApiOperationSupport(order = 33)
	@Operation(summary = "Camera application approval rejected", description = "After rejection, the status changes torejected")
	public R<Boolean> rejectCameraApply(@Valid @RequestBody CameraApplyRejectDTO cameraApplyRejectDTO) {
		return R.data(vlsCameraApplyRecordService.reject(cameraApplyRejectDTO));
	}

	/**
	 * Camera use application completed
	 */
	@PostMapping("/camera-apply/complete")
	@ApiOperationSupport(order = 34)
	@Operation(summary = "Camera application completed", description = "onlyapprovedThe status can be completed ascompleted")
	public R<Boolean> completeCameraApply(@Valid @RequestBody CameraApplyCompleteDTO cameraApplyCompleteDTO) {
		return R.data(vlsCameraApplyRecordService.complete(cameraApplyCompleteDTO));
	}

	/**
	 * Camera use application page
	 */
	@GetMapping("/camera-apply/page")
	@ApiOperationSupport(order = 35)
	@Operation(summary = "Camera application paging", description = "by device、state、Applicant page query")
	public R<IPage<CameraApplyRecordVO>> pageCameraApply(CameraApplyQueryDTO cameraApplyQueryDTO, Query query) {
		IPage<CameraApplyRecordVO> pages = vlsCameraApplyRecordService.page(Condition.getPage(query), cameraApplyQueryDTO);
		return R.data(pages);
	}

}
