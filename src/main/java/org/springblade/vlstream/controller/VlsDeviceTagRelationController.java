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
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.vlstream.excel.VlsDeviceTagRelationExcel;
import org.springblade.vlstream.pojo.dto.DeviceTagRelationDTO;
import org.springblade.vlstream.pojo.entity.DeviceTagRelation;
import org.springblade.vlstream.pojo.vo.DeviceTagRelationVO;
import org.springblade.vlstream.service.IVlsDeviceTagRelationService;
import org.springblade.vlstream.wrapper.VlsDeviceTagRelationWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Device tag association table controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsDeviceTagRelation")
@Tag(name = "Device tag association table", description = "Device tag association table interface")
public class VlsDeviceTagRelationController extends BladeController {

	private final IVlsDeviceTagRelationService vlsDeviceTagRelationService;

	/**
	 * Device tag association table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingvlsDeviceTagRelation")
	public R<DeviceTagRelationVO> detail(DeviceTagRelation vlsDeviceTagRelation) {
		DeviceTagRelation detail = vlsDeviceTagRelationService.getOne(Condition.getQueryWrapper(vlsDeviceTagRelation));
		return R.data(VlsDeviceTagRelationWrapper.build().entityVO(detail));
	}

	/**
	 * Device tag association table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingvlsDeviceTagRelation")
	public R<IPage<DeviceTagRelationVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsDeviceTagRelation, Query query) {
		IPage<DeviceTagRelation> pages = vlsDeviceTagRelationService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsDeviceTagRelation, DeviceTagRelation.class));
		return R.data(VlsDeviceTagRelationWrapper.build().pageVO(pages));
	}


	/**
	 * Device tag association table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description = "incomingvlsDeviceTagRelation")
	public R<IPage<DeviceTagRelationVO>> page(DeviceTagRelationVO vlsDeviceTagRelation, Query query) {
		IPage<DeviceTagRelationVO> pages = vlsDeviceTagRelationService.selectVlsDeviceTagRelationPage(Condition.getPage(query), vlsDeviceTagRelation);
		return R.data(pages);
	}

	/**
	 * Device tag association table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingvlsDeviceTagRelation")
	public R save(@Valid @RequestBody DeviceTagRelation vlsDeviceTagRelation) {
		return R.status(vlsDeviceTagRelationService.save(vlsDeviceTagRelation));
	}

	/**
	 * Device tag association table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingvlsDeviceTagRelation")
	public R update(@Valid @RequestBody DeviceTagRelation vlsDeviceTagRelation) {
		return R.status(vlsDeviceTagRelationService.updateById(vlsDeviceTagRelation));
	}

	/**
	 * Device tag association table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingvlsDeviceTagRelation")
	public R submit(@Valid @RequestBody DeviceTagRelation vlsDeviceTagRelation) {
		return R.status(vlsDeviceTagRelationService.saveOrUpdate(vlsDeviceTagRelation));
	}

	/**
	 * Device tag association table delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsDeviceTagRelationService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsDeviceTagRelation")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description = "incomingvlsDeviceTagRelation")
	public void exportVlsDeviceTagRelation(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsDeviceTagRelation, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<DeviceTagRelation> queryWrapper = Condition.getQueryWrapper(vlsDeviceTagRelation, DeviceTagRelation.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsDeviceTagRelationEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsDeviceTagRelationEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsDeviceTagRelationExcel> list = vlsDeviceTagRelationService.exportVlsDeviceTagRelation(queryWrapper);
		ExcelUtil.export(response, "Device tag association table data" + DateUtil.time(), "Device tag association table data table", list, VlsDeviceTagRelationExcel.class);
	}

	/**
	 * Set device label(Overwrite original label)
	 */
	@ApiOperation("Set device label")
	@PutMapping("/device/{deviceId}/tags")
	public R<String> setDeviceTags(
		@ApiParam("equipmentID") @PathVariable Long deviceId,
		@ApiParam("LabelIDlist") @RequestBody List<Long> tagIds) {

		boolean success = vlsDeviceTagRelationService.setDeviceTags(deviceId, tagIds, "admin");
		if (success) {
			return R.success("Device label set successfully");
		} else {
			return R.fail("Device label setting failed");
		}
	}

	/**
	 * Add device label(Append to existing tag)
	 */
	@ApiOperation("Add device label")
	@PostMapping("/device/{deviceId}/tags")
	public R<String> addDeviceTags(
		@ApiParam("equipmentID") @PathVariable Long deviceId,
		@ApiParam("LabelIDlist") @RequestBody List<Long> tagIds) {

		boolean success = vlsDeviceTagRelationService.addDeviceTags(deviceId, tagIds, "admin");
		if (success) {
			return R.success("Device label added successfully");
		} else {
			return R.fail("Failed to add device label");
		}
	}

	/**
	 * Remove device label
	 */
	@ApiOperation("Remove device label")
	@DeleteMapping("/device/{deviceId}/tags")
	public R<String> removeDeviceTags(
		@ApiParam("equipmentID") @PathVariable Long deviceId,
		@ApiParam("LabelIDlist") @RequestBody List<Long> tagIds) {

		boolean success = vlsDeviceTagRelationService.removeDeviceTags(deviceId, tagIds);
		if (success) {
			return R.success("Device label removed successfully");
		} else {
			return R.fail("Device label removal failed");
		}
	}

	/**
	 * Clear all tags from device
	 */
	@ApiOperation("Clear all tags from device")
	@DeleteMapping("/device/{deviceId}/tags/all")
	public R<String> clearDeviceTags(
		@ApiParam("equipmentID") @PathVariable Long deviceId) {

		boolean success = vlsDeviceTagRelationService.clearDeviceTags(deviceId);
		if (success) {
			return R.success("Device label cleared successfully");
		} else {
			return R.fail("Device label clearing failed");
		}
	}

	/**
	 * Get all tags of the device
	 */
	@ApiOperation("Get all tags of the device")
	@GetMapping("/device/{deviceId}/tags")
	public R<List<DeviceTagRelationDTO>> getDeviceTags(@ApiParam("equipmentID") @PathVariable Long deviceId) {
		List<DeviceTagRelationDTO> tags = vlsDeviceTagRelationService.getDeviceTags(deviceId);
		return R.data(tags);
	}

	/**
	 * Get the label of the deviceIDlist
	 */
	@ApiOperation("Get the label of the deviceIDlist")
	@GetMapping("/device/{deviceId}/tag-ids")
	public R<List<Long>> getDeviceTagIds(@ApiParam("equipmentID") @PathVariable Long deviceId) {
		List<Long> tagIds = vlsDeviceTagRelationService.getDeviceTagIds(deviceId);
		return R.data(tagIds);
	}

	/**
	 * Get device tag details
	 */
	@ApiOperation("Get device tag details")
	@GetMapping("/device/{deviceId}/tag-details")
	public R<Map<String, Object>> getDeviceTagDetails(
		@ApiParam("equipmentID") @PathVariable Long deviceId) {

		Map<String, Object> details = vlsDeviceTagRelationService.getDeviceTagDetails(deviceId);
		return R.data(details);
	}

	/**
	 * Get a list of devices with a specified label
	 */
	@ApiOperation("Get a list of devices with a specified label")
	@GetMapping("/tag/{tagId}/devices")
	public R<List<Map<String, Object>>> getDevicesByTag(
		@ApiParam("LabelID") @PathVariable Long tagId) {

		List<Map<String, Object>> devices = vlsDeviceTagRelationService.getDevicesByTag(tagId);
		return R.data(devices);
	}

	/**
	 * Query devices based on multiple tags(intersection)
	 */
	@ApiOperation("Query devices based on multiple tags(intersection)")
	@PostMapping("/devices/by-all-tags")
	public R<List<Long>> findDevicesByAllTags(
		@ApiParam("LabelIDlist") @RequestBody List<Long> tagIds) {

		List<Long> deviceIds = vlsDeviceTagRelationService.findDevicesByAllTags(tagIds);
		return R.data(deviceIds);
	}

	/**
	 * Query devices based on multiple tags(union)
	 */
	@ApiOperation("Query devices based on multiple tags(union)")
	@PostMapping("/devices/by-any-tags")
	public R<List<Long>> findDevicesByAnyTags(
		@ApiParam("LabelIDlist") @RequestBody List<Long> tagIds) {

		List<Long> deviceIds = vlsDeviceTagRelationService.findDevicesByAnyTags(tagIds);
		return R.data(deviceIds);
	}

	/**
	 * Set device labels in batches
	 */
	@ApiOperation("Set device labels in batches")
	@PutMapping("/devices/batch-tags")
	public R<String> batchSetDeviceTags(
		@ApiParam("Device tag mapping") @RequestBody Map<Long, List<Long>> deviceTagMap) {

		int successCount = vlsDeviceTagRelationService.batchSetDeviceTags(deviceTagMap, "admin");
		return R.success("Batch setup successful, Successfully set up " + successCount + " device tags");
	}

	/**
	 * Copy device labels to other devices
	 */
	@ApiOperation("Copy device labels to other devices")
	@PostMapping("/device/{sourceDeviceId}/copy-tags")
	public R<String> copyDeviceTags(
		@ApiParam("source deviceID") @PathVariable Long sourceDeviceId,
		@ApiParam("target deviceIDlist") @RequestBody List<Long> targetDeviceIds) {

		boolean success = vlsDeviceTagRelationService.copyDeviceTags(sourceDeviceId, targetDeviceIds, "admin");
		if (success) {
			return R.success("Copying device label successfully");
		} else {
			return R.fail("Failed to copy device label");
		}
	}

	/**
	 * Get device tag statistics
	 */
	@ApiOperation("Get device tag statistics")
	@GetMapping("/statistics/device-tags")
	public R<List<Map<String, Object>>> getDeviceTagStatistics() {
		List<Map<String, Object>> statistics = vlsDeviceTagRelationService.getDeviceTagStatistics();
		return R.data(statistics);
	}

	/**
	 * Get tag usage statistics
	 */
	@ApiOperation("Get tag usage statistics")
	@GetMapping("/statistics/tag-usage")
	public R<List<Map<String, Object>>> getTagUsageStatistics() {
		List<Map<String, Object>> statistics = vlsDeviceTagRelationService.getTagUsageStatistics();
		return R.data(statistics);
	}

	/**
	 * Check if the device has the specified label
	 */
	@ApiOperation("Check if the device has the specified label")
	@GetMapping("/device/{deviceId}/has-tag/{tagId}")
	public R<Boolean> hasDeviceTag(
		@ApiParam("equipmentID") @PathVariable Long deviceId,
		@ApiParam("LabelID") @PathVariable Long tagId) {

		boolean hasTag = vlsDeviceTagRelationService.hasDeviceTag(deviceId, tagId);
		return R.data(hasTag);
	}

	/**
	 * Get the number of devices labeled
	 */
	@ApiOperation("Get the number of devices labeled")
	@GetMapping("/tag/{tagId}/device-count")
	public R<Integer> getTagDeviceCount(
		@ApiParam("LabelID") @PathVariable Long tagId) {

		int count = vlsDeviceTagRelationService.getTagDeviceCount(tagId);
		return R.data(count);
	}

	/**
	 * Verification tagIDIs the list valid?
	 */
	@ApiOperation("Verification tagIDIs the list valid?")
	@PostMapping("/validate-tags")
	public R<Map<String, Object>> validateTagIds(
		@ApiParam("LabelIDlist") @RequestBody List<Long> tagIds) {

		Map<String, Object> result = vlsDeviceTagRelationService.validateTagIds(tagIds);
		return R.data(result);
	}

	/**
	 * Sync tag usage count
	 */
	@ApiOperation("Sync tag usage count")
	@PostMapping("/sync-usage-count")

	public R<String> syncTagUsageCount() {
		boolean success = vlsDeviceTagRelationService.syncTagUsageCount();
		if (success) {
			return R.success("Synchronizing tag usage count successfully");
		} else {
			return R.fail("Failed to sync tag usage count");
		}
	}

	/**
	 * Get complete information about device tags(Contains device information and label information)
	 */
	@ApiOperation("Get complete information about device tags")
	@GetMapping("/device/{deviceId}/full-info")
	public R<Map<String, Object>> getDeviceTagFullInfo(@ApiParam("equipmentID") @PathVariable Long deviceId) {

		Map<String, Object> info = new HashMap<>();

		// Get device tag details
		Map<String, Object> tagDetails = vlsDeviceTagRelationService.getDeviceTagDetails(deviceId);
		info.put("tagDetails", tagDetails);

		// Get a list of tags for a device
		List<DeviceTagRelationDTO> tags = vlsDeviceTagRelationService.getDeviceTags(deviceId);
		info.put("tags", tags);

		return R.data(info);
	}

	/**
	 * Obtain device tag related information in batches based on tags
	 */
	@ApiOperation("Obtain device tag related information in batches based on tags")
	@PostMapping("/tags/device-relations")
	public R<Map<String, Object>> getDeviceRelationsByTags(
		@ApiParam("LabelIDlist") @RequestBody List<Long> tagIds) {

		Map<String, Object> result = new HashMap<>();

		for (Long tagId : tagIds) {
			List<Map<String, Object>> devices = vlsDeviceTagRelationService.getDevicesByTag(tagId);
			result.put("tag_" + tagId, devices);
		}

		return R.data(result);
	}

}
