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
import org.springblade.vlstream.enums.AnalysisRequestStatusEnum;
import org.springblade.vlstream.excel.VlsAnalysisRequestExcel;
import org.springblade.vlstream.pojo.entity.AnalysisRequest;
import org.springblade.vlstream.pojo.entity.DeviceInfo;
import org.springblade.vlstream.pojo.vo.AnalysisRequestVO;
import org.springblade.vlstream.service.IVlsAnalysisRequestService;
import org.springblade.vlstream.service.IVlsDeviceInfoService;
import org.springblade.vlstream.wrapper.VlsAnalysisRequestWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Intelligent analysis request form controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsAnalysisRequest")
@Tag(name = "Intelligent analysis request form", description = "Intelligent analysis request table interface")
public class VlsAnalysisRequestController extends BladeController {

	private final IVlsAnalysisRequestService vlsAnalysisRequestService;
	private final IVlsDeviceInfoService vlsDeviceInfoService;

	/**
	 * Intelligent analysis application
	 */
	@PostMapping("/apply")
	@ApiOperationSupport(order = 0)
	@Operation(summary = "Apply", description = "Submit intelligent analysis application")
	public R<Boolean> apply(@Valid @RequestBody AnalysisRequest vlsAnalysisRequest) {
		vlsAnalysisRequest.setRequestStatus(AnalysisRequestStatusEnum.processing);
		if (vlsAnalysisRequest.getProgress() == null) {
			vlsAnalysisRequest.setProgress(0);
		}
		vlsAnalysisRequest.setResultPath(null);
		vlsAnalysisRequest.setErrorMessage(null);
		vlsAnalysisRequest.setStartTime(null);
		vlsAnalysisRequest.setCompleteTime(null);
		return R.status(vlsAnalysisRequestService.saveOrUpdate(vlsAnalysisRequest));
	}

	/**
	 * Cancel smart analysis application
	 */
	@GetMapping("/cancel")
	@ApiOperationSupport(order = 0)
	@Operation(summary = "Cancel application", description = "according toidCancel smart analysis application")
	public R<Boolean> cancel(@Parameter(description = "ApplyID", required = true) @RequestParam Long id) {
		AnalysisRequest analysisRequest = vlsAnalysisRequestService.getById(id);
		if (analysisRequest == null) {
			return R.fail("Application does not exist");
		}
		if (analysisRequest.getRequestStatus() != AnalysisRequestStatusEnum.processing) {
			return R.fail("Cancellation is not supported in the current status");
		}
		AnalysisRequest updateAnalysisRequest = new AnalysisRequest();
		updateAnalysisRequest.setId(id);
		updateAnalysisRequest.setRequestStatus(AnalysisRequestStatusEnum.cancel);
		return R.status(vlsAnalysisRequestService.updateById(updateAnalysisRequest));
	}

	/**
	 * Intelligent analysis request form Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description  = "incomingvlsAnalysisRequest")
	public R<AnalysisRequestVO> detail(AnalysisRequest vlsAnalysisRequest) {
		AnalysisRequest detail = vlsAnalysisRequestService.getOne(Condition.getQueryWrapper(vlsAnalysisRequest));
		AnalysisRequestVO analysisRequestVO = VlsAnalysisRequestWrapper.build().entityVO(detail);
		fillCameraName(analysisRequestVO);
		return R.data(analysisRequestVO);
	}

	/**
	 * Intelligent analysis request form Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description  = "incomingvlsAnalysisRequest")
	public R<IPage<AnalysisRequestVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAnalysisRequest, Query query) {
		IPage<AnalysisRequest> pages = vlsAnalysisRequestService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsAnalysisRequest, AnalysisRequest.class));
		IPage<AnalysisRequestVO> pageVO = VlsAnalysisRequestWrapper.build().pageVO(pages);
		fillCameraName(pageVO.getRecords());
		return R.data(pageVO);
	}

	private void fillCameraName(AnalysisRequestVO analysisRequestVO) {
		if (analysisRequestVO == null) {
			return;
		}
		List<AnalysisRequestVO> analysisRequestVOList = new ArrayList<>();
		analysisRequestVOList.add(analysisRequestVO);
		fillCameraName(analysisRequestVOList);
	}

	private void fillCameraName(List<AnalysisRequestVO> analysisRequestVOList) {
		if (analysisRequestVOList == null || analysisRequestVOList.isEmpty()) {
			return;
		}
		Set<Long> deviceIdSet = new HashSet<>();
		for (AnalysisRequestVO analysisRequestVO : analysisRequestVOList) {
			if (analysisRequestVO == null || StringUtils.isBlank(analysisRequestVO.getDeviceIds())) {
				continue;
			}
			String[] deviceIdArray = analysisRequestVO.getDeviceIds().split(",");
			for (String deviceIdText : deviceIdArray) {
				String deviceIdTrimText = deviceIdText == null ? null : deviceIdText.trim();
				if (StringUtils.isBlank(deviceIdTrimText) || !StringUtils.isNumeric(deviceIdTrimText)) {
					continue;
				}
				deviceIdSet.add(Long.valueOf(deviceIdTrimText));
			}
		}
		Map<Long, String> deviceNameMap = new HashMap<>();
		if (!deviceIdSet.isEmpty()) {
			List<DeviceInfo> deviceInfoList = vlsDeviceInfoService.listByIds(deviceIdSet);
			for (DeviceInfo deviceInfo : deviceInfoList) {
				if (deviceInfo == null || deviceInfo.getId() == null) {
					continue;
				}
				deviceNameMap.put(deviceInfo.getId(), deviceInfo.getDeviceName());
			}
		}
		for (AnalysisRequestVO analysisRequestVO : analysisRequestVOList) {
			if (analysisRequestVO == null || StringUtils.isBlank(analysisRequestVO.getDeviceIds())) {
				continue;
			}
			List<String> cameraNameList = new ArrayList<>();
			String[] deviceIdArray = analysisRequestVO.getDeviceIds().split(",");
			for (String deviceIdText : deviceIdArray) {
				String deviceIdTrimText = deviceIdText == null ? null : deviceIdText.trim();
				if (StringUtils.isBlank(deviceIdTrimText)) {
					continue;
				}
				String cameraName = null;
				if (StringUtils.isNumeric(deviceIdTrimText)) {
					cameraName = deviceNameMap.get(Long.valueOf(deviceIdTrimText));
				}
				if (StringUtils.isBlank(cameraName)) {
					DeviceInfo deviceInfo = vlsDeviceInfoService.getByDeviceId(deviceIdTrimText);
					if (deviceInfo != null) {
						cameraName = deviceInfo.getDeviceName();
					}
				}
				if (StringUtils.isNotBlank(cameraName)) {
					cameraNameList.add(cameraName);
				}
			}
			analysisRequestVO.setCameraName(String.join(",", cameraNameList));
		}
	}

	/**
	 * Intelligent analysis request form New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description  = "incomingvlsAnalysisRequest")
	public R save(@Valid @RequestBody AnalysisRequest vlsAnalysisRequest) {
		return R.status(vlsAnalysisRequestService.save(vlsAnalysisRequest));
	}

	/**
	 * Intelligent analysis request form Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description  = "incomingvlsAnalysisRequest")
	public R update(@Valid @RequestBody AnalysisRequest vlsAnalysisRequest) {
		return R.status(vlsAnalysisRequestService.updateById(vlsAnalysisRequest));
	}

	/**
	 * Intelligent analysis request form Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description  = "incomingvlsAnalysisRequest")
	public R submit(@Valid @RequestBody AnalysisRequest vlsAnalysisRequest) {
		return R.status(vlsAnalysisRequestService.saveOrUpdate(vlsAnalysisRequest));
	}

	/**
	 * Intelligent analysis request form delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description  = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsAnalysisRequestService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsAnalysisRequest")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description  = "incomingvlsAnalysisRequest")
	public void exportVlsAnalysisRequest(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsAnalysisRequest, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<AnalysisRequest> queryWrapper = Condition.getQueryWrapper(vlsAnalysisRequest, AnalysisRequest.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsAnalysisRequestEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsAnalysisRequestEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsAnalysisRequestExcel> list = vlsAnalysisRequestService.exportVlsAnalysisRequest(queryWrapper);
		ExcelUtil.export(response, "Intelligent analysis of request table data" + DateUtil.time(), "Intelligent analysis request form data table", list, VlsAnalysisRequestExcel.class);
	}

}
