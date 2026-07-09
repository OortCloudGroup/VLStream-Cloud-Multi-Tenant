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
import org.springblade.modules.system.pojo.dto.FileResponseDto;
import org.springblade.modules.system.service.IFileUploadService;
import org.springblade.vlstream.excel.VlsEventManagementExcel;
import org.springblade.vlstream.pojo.entity.EventManagement;
import org.springblade.vlstream.pojo.vo.EventManagementVO;
import org.springblade.vlstream.service.IVlsEventManagementService;
import org.springblade.vlstream.wrapper.VlsEventManagementWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * event management table controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsEventManagement")
@Tag(name = "event management table", description = "Event management table interface")
public class VlsEventManagementController extends BladeController {

	private final IVlsEventManagementService vlsEventManagementService;
	private final IFileUploadService fileUploadService;

	private static final String FILE_UPLOAD_APP_ID = "818301f0e77f4cd8a117414cbeb32d9e";
	private static final String FILE_UPLOAD_SECRET_KEY = "5f0de11687d744bc95e84e207d319493";

	/**
	 * event management table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingvlsEventManagement")
	public R<EventManagementVO> detail(EventManagement vlsEventManagement) {
		EventManagement detail = vlsEventManagementService.getOne(Condition.getQueryWrapper(vlsEventManagement));
		return R.data(VlsEventManagementWrapper.build().entityVO(detail));
	}

	/**
	 * event management table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingvlsEventManagement")
	public R<IPage<EventManagementVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsEventManagement, Query query) {
		IPage<EventManagement> pages = vlsEventManagementService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsEventManagement, EventManagement.class));
		return R.data(VlsEventManagementWrapper.build().pageVO(pages));
	}


	/**
	 * event management table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description = "incomingvlsEventManagement")
	public R<IPage<EventManagementVO>> page(EventManagementVO vlsEventManagement, Query query) {
		IPage<EventManagementVO> pages = vlsEventManagementService.selectVlsEventManagementPage(Condition.getPage(query), vlsEventManagement);
		return R.data(pages);
	}

	/**
	 * event management table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingvlsEventManagement")
	public R save(@Valid @RequestBody EventManagement vlsEventManagement) {
		return R.status(vlsEventManagementService.save(vlsEventManagement));
	}

	/**
	 * event management table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingvlsEventManagement")
	public R update(@Valid @RequestBody EventManagement vlsEventManagement) {
		return R.status(vlsEventManagementService.updateById(vlsEventManagement));
	}

	/**
	 * event management table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingvlsEventManagement")
	public R submit(@Valid @RequestBody EventManagement vlsEventManagement) {
		return R.status(vlsEventManagementService.saveOrUpdate(vlsEventManagement));
	}

	/**
	 * event management table delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsEventManagementService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsEventManagement")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description = "incomingvlsEventManagement")
	public void exportVlsEventManagement(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsEventManagement, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<EventManagement> queryWrapper = Condition.getQueryWrapper(vlsEventManagement, EventManagement.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsEventManagementEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsEventManagementEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsEventManagementExcel> list = vlsEventManagementService.exportVlsEventManagement(queryWrapper);
		ExcelUtil.export(response, "Event management table data" + DateUtil.time(), "Event management table data table", list, VlsEventManagementExcel.class);
	}

	@GetMapping("/{id}")
	@ApiOperation("according toidQuery details")
	public R<EventManagement> getEvent(@ApiParam("Primary id") @PathVariable Long id) {
		EventManagement event = vlsEventManagementService.getEventById(id);
		if (event == null) {
			return R.fail("Event not found");
		}
		return R.data(event);
	}

	@PostMapping
	@ApiOperation("Create event")
	public R<Boolean> createEvent(@RequestBody EventManagement eventManagement) {
		boolean created = vlsEventManagementService.createEvent(eventManagement);
		if (created) {
			return R.data(true);
		}
		return R.fail("Create event failed");
	}

	@PostMapping(value = "/report", consumes = "multipart/form-data")
	@Operation(summary = "Report an incident", description = "Upload image or video and set reportImg")
	public R<Boolean> reportEvent(@Parameter(description = "Event data") @RequestPart("event") EventManagement eventManagement, @Parameter(description = "Image or video file") @RequestPart("file") MultipartFile file) {

		if (eventManagement == null) {
			return R.fail("Event payload is required");
		}
		if (file == null || file.isEmpty()) {
			return R.fail("File is required");
		}
		File localFile = fileUploadService.multipartFileToFile(file);
		if (localFile == null) {
			return R.fail("File convert failed");
		}
		FileResponseDto fileResponseDto = fileUploadService.uploadFile(FILE_UPLOAD_APP_ID, FILE_UPLOAD_SECRET_KEY, localFile);
		if (fileResponseDto == null || StringUtils.isBlank(fileResponseDto.getUrl())) {
			return R.fail("File upload failed");
		}
		eventManagement.setReportImg(fileResponseDto.getUrl());
		boolean created = vlsEventManagementService.createEvent(eventManagement);
		if (created) {
			return R.data(true);
		}
		return R.fail("Event report failed");
	}

}
