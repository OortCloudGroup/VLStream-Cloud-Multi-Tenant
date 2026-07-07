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
 * 设备标签关联表 控制器
 *
 * @author Oort
 * @since 2025-12-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsDeviceTagRelation")
@Tag(name = "设备标签关联表", description = "设备标签关联表接口")
public class VlsDeviceTagRelationController extends BladeController {

	private final IVlsDeviceTagRelationService vlsDeviceTagRelationService;

	/**
	 * 设备标签关联表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入vlsDeviceTagRelation")
	public R<DeviceTagRelationVO> detail(DeviceTagRelation vlsDeviceTagRelation) {
		DeviceTagRelation detail = vlsDeviceTagRelationService.getOne(Condition.getQueryWrapper(vlsDeviceTagRelation));
		return R.data(VlsDeviceTagRelationWrapper.build().entityVO(detail));
	}

	/**
	 * 设备标签关联表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入vlsDeviceTagRelation")
	public R<IPage<DeviceTagRelationVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsDeviceTagRelation, Query query) {
		IPage<DeviceTagRelation> pages = vlsDeviceTagRelationService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsDeviceTagRelation, DeviceTagRelation.class));
		return R.data(VlsDeviceTagRelationWrapper.build().pageVO(pages));
	}


	/**
	 * 设备标签关联表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "分页", description = "传入vlsDeviceTagRelation")
	public R<IPage<DeviceTagRelationVO>> page(DeviceTagRelationVO vlsDeviceTagRelation, Query query) {
		IPage<DeviceTagRelationVO> pages = vlsDeviceTagRelationService.selectVlsDeviceTagRelationPage(Condition.getPage(query), vlsDeviceTagRelation);
		return R.data(pages);
	}

	/**
	 * 设备标签关联表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "传入vlsDeviceTagRelation")
	public R save(@Valid @RequestBody DeviceTagRelation vlsDeviceTagRelation) {
		return R.status(vlsDeviceTagRelationService.save(vlsDeviceTagRelation));
	}

	/**
	 * 设备标签关联表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "传入vlsDeviceTagRelation")
	public R update(@Valid @RequestBody DeviceTagRelation vlsDeviceTagRelation) {
		return R.status(vlsDeviceTagRelationService.updateById(vlsDeviceTagRelation));
	}

	/**
	 * 设备标签关联表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "新增或修改", description = "传入vlsDeviceTagRelation")
	public R submit(@Valid @RequestBody DeviceTagRelation vlsDeviceTagRelation) {
		return R.status(vlsDeviceTagRelationService.saveOrUpdate(vlsDeviceTagRelation));
	}

	/**
	 * 设备标签关联表 删除
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(vlsDeviceTagRelationService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 导出数据
	 */
	@IsAdmin
	@GetMapping("/export-vlsDeviceTagRelation")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "导出数据", description = "传入vlsDeviceTagRelation")
	public void exportVlsDeviceTagRelation(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsDeviceTagRelation, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<DeviceTagRelation> queryWrapper = Condition.getQueryWrapper(vlsDeviceTagRelation, DeviceTagRelation.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsDeviceTagRelationEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsDeviceTagRelationEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsDeviceTagRelationExcel> list = vlsDeviceTagRelationService.exportVlsDeviceTagRelation(queryWrapper);
		ExcelUtil.export(response, "设备标签关联表数据" + DateUtil.time(), "设备标签关联表数据表", list, VlsDeviceTagRelationExcel.class);
	}

	/**
	 * 设置设备标签（覆盖原有标签）
	 */
	@ApiOperation("设置设备标签")
	@PutMapping("/device/{deviceId}/tags")
	public R<String> setDeviceTags(
		@ApiParam("设备ID") @PathVariable Long deviceId,
		@ApiParam("标签ID列表") @RequestBody List<Long> tagIds) {

		boolean success = vlsDeviceTagRelationService.setDeviceTags(deviceId, tagIds, "admin");
		if (success) {
			return R.success("设备标签设置成功");
		} else {
			return R.fail("设备标签设置失败");
		}
	}

	/**
	 * 添加设备标签（追加到现有标签）
	 */
	@ApiOperation("添加设备标签")
	@PostMapping("/device/{deviceId}/tags")
	public R<String> addDeviceTags(
		@ApiParam("设备ID") @PathVariable Long deviceId,
		@ApiParam("标签ID列表") @RequestBody List<Long> tagIds) {

		boolean success = vlsDeviceTagRelationService.addDeviceTags(deviceId, tagIds, "admin");
		if (success) {
			return R.success("设备标签添加成功");
		} else {
			return R.fail("设备标签添加失败");
		}
	}

	/**
	 * 移除设备标签
	 */
	@ApiOperation("移除设备标签")
	@DeleteMapping("/device/{deviceId}/tags")
	public R<String> removeDeviceTags(
		@ApiParam("设备ID") @PathVariable Long deviceId,
		@ApiParam("标签ID列表") @RequestBody List<Long> tagIds) {

		boolean success = vlsDeviceTagRelationService.removeDeviceTags(deviceId, tagIds);
		if (success) {
			return R.success("设备标签移除成功");
		} else {
			return R.fail("设备标签移除失败");
		}
	}

	/**
	 * 清除设备的所有标签
	 */
	@ApiOperation("清除设备的所有标签")
	@DeleteMapping("/device/{deviceId}/tags/all")
	public R<String> clearDeviceTags(
		@ApiParam("设备ID") @PathVariable Long deviceId) {

		boolean success = vlsDeviceTagRelationService.clearDeviceTags(deviceId);
		if (success) {
			return R.success("设备标签清除成功");
		} else {
			return R.fail("设备标签清除失败");
		}
	}

	/**
	 * 获取设备的所有标签
	 */
	@ApiOperation("获取设备的所有标签")
	@GetMapping("/device/{deviceId}/tags")
	public R<List<DeviceTagRelationDTO>> getDeviceTags(@ApiParam("设备ID") @PathVariable Long deviceId) {
		List<DeviceTagRelationDTO> tags = vlsDeviceTagRelationService.getDeviceTags(deviceId);
		return R.data(tags);
	}

	/**
	 * 获取设备的标签ID列表
	 */
	@ApiOperation("获取设备的标签ID列表")
	@GetMapping("/device/{deviceId}/tag-ids")
	public R<List<Long>> getDeviceTagIds(@ApiParam("设备ID") @PathVariable Long deviceId) {
		List<Long> tagIds = vlsDeviceTagRelationService.getDeviceTagIds(deviceId);
		return R.data(tagIds);
	}

	/**
	 * 获取设备标签的详细信息
	 */
	@ApiOperation("获取设备标签的详细信息")
	@GetMapping("/device/{deviceId}/tag-details")
	public R<Map<String, Object>> getDeviceTagDetails(
		@ApiParam("设备ID") @PathVariable Long deviceId) {

		Map<String, Object> details = vlsDeviceTagRelationService.getDeviceTagDetails(deviceId);
		return R.data(details);
	}

	/**
	 * 获取带有指定标签的设备列表
	 */
	@ApiOperation("获取带有指定标签的设备列表")
	@GetMapping("/tag/{tagId}/devices")
	public R<List<Map<String, Object>>> getDevicesByTag(
		@ApiParam("标签ID") @PathVariable Long tagId) {

		List<Map<String, Object>> devices = vlsDeviceTagRelationService.getDevicesByTag(tagId);
		return R.data(devices);
	}

	/**
	 * 根据多个标签查询设备（交集）
	 */
	@ApiOperation("根据多个标签查询设备（交集）")
	@PostMapping("/devices/by-all-tags")
	public R<List<Long>> findDevicesByAllTags(
		@ApiParam("标签ID列表") @RequestBody List<Long> tagIds) {

		List<Long> deviceIds = vlsDeviceTagRelationService.findDevicesByAllTags(tagIds);
		return R.data(deviceIds);
	}

	/**
	 * 根据多个标签查询设备（并集）
	 */
	@ApiOperation("根据多个标签查询设备（并集）")
	@PostMapping("/devices/by-any-tags")
	public R<List<Long>> findDevicesByAnyTags(
		@ApiParam("标签ID列表") @RequestBody List<Long> tagIds) {

		List<Long> deviceIds = vlsDeviceTagRelationService.findDevicesByAnyTags(tagIds);
		return R.data(deviceIds);
	}

	/**
	 * 批量设置设备标签
	 */
	@ApiOperation("批量设置设备标签")
	@PutMapping("/devices/batch-tags")
	public R<String> batchSetDeviceTags(
		@ApiParam("设备标签映射") @RequestBody Map<Long, List<Long>> deviceTagMap) {

		int successCount = vlsDeviceTagRelationService.batchSetDeviceTags(deviceTagMap, "admin");
		return R.success("批量设置成功，成功设置 " + successCount + " 个设备的标签");
	}

	/**
	 * 复制设备标签到其他设备
	 */
	@ApiOperation("复制设备标签到其他设备")
	@PostMapping("/device/{sourceDeviceId}/copy-tags")
	public R<String> copyDeviceTags(
		@ApiParam("源设备ID") @PathVariable Long sourceDeviceId,
		@ApiParam("目标设备ID列表") @RequestBody List<Long> targetDeviceIds) {

		boolean success = vlsDeviceTagRelationService.copyDeviceTags(sourceDeviceId, targetDeviceIds, "admin");
		if (success) {
			return R.success("复制设备标签成功");
		} else {
			return R.fail("复制设备标签失败");
		}
	}

	/**
	 * 获取设备标签统计信息
	 */
	@ApiOperation("获取设备标签统计信息")
	@GetMapping("/statistics/device-tags")
	public R<List<Map<String, Object>>> getDeviceTagStatistics() {
		List<Map<String, Object>> statistics = vlsDeviceTagRelationService.getDeviceTagStatistics();
		return R.data(statistics);
	}

	/**
	 * 获取标签使用统计
	 */
	@ApiOperation("获取标签使用统计")
	@GetMapping("/statistics/tag-usage")
	public R<List<Map<String, Object>>> getTagUsageStatistics() {
		List<Map<String, Object>> statistics = vlsDeviceTagRelationService.getTagUsageStatistics();
		return R.data(statistics);
	}

	/**
	 * 检查设备是否有指定标签
	 */
	@ApiOperation("检查设备是否有指定标签")
	@GetMapping("/device/{deviceId}/has-tag/{tagId}")
	public R<Boolean> hasDeviceTag(
		@ApiParam("设备ID") @PathVariable Long deviceId,
		@ApiParam("标签ID") @PathVariable Long tagId) {

		boolean hasTag = vlsDeviceTagRelationService.hasDeviceTag(deviceId, tagId);
		return R.data(hasTag);
	}

	/**
	 * 获取标签的设备数量
	 */
	@ApiOperation("获取标签的设备数量")
	@GetMapping("/tag/{tagId}/device-count")
	public R<Integer> getTagDeviceCount(
		@ApiParam("标签ID") @PathVariable Long tagId) {

		int count = vlsDeviceTagRelationService.getTagDeviceCount(tagId);
		return R.data(count);
	}

	/**
	 * 验证标签ID列表是否有效
	 */
	@ApiOperation("验证标签ID列表是否有效")
	@PostMapping("/validate-tags")
	public R<Map<String, Object>> validateTagIds(
		@ApiParam("标签ID列表") @RequestBody List<Long> tagIds) {

		Map<String, Object> result = vlsDeviceTagRelationService.validateTagIds(tagIds);
		return R.data(result);
	}

	/**
	 * 同步标签使用计数
	 */
	@ApiOperation("同步标签使用计数")
	@PostMapping("/sync-usage-count")

	public R<String> syncTagUsageCount() {
		boolean success = vlsDeviceTagRelationService.syncTagUsageCount();
		if (success) {
			return R.success("同步标签使用计数成功");
		} else {
			return R.fail("同步标签使用计数失败");
		}
	}

	/**
	 * 获取设备标签的完整信息（包含设备信息和标签信息）
	 */
	@ApiOperation("获取设备标签的完整信息")
	@GetMapping("/device/{deviceId}/full-info")
	public R<Map<String, Object>> getDeviceTagFullInfo(@ApiParam("设备ID") @PathVariable Long deviceId) {

		Map<String, Object> info = new HashMap<>();

		// 获取设备标签详情
		Map<String, Object> tagDetails = vlsDeviceTagRelationService.getDeviceTagDetails(deviceId);
		info.put("tagDetails", tagDetails);

		// 获取设备的标签列表
		List<DeviceTagRelationDTO> tags = vlsDeviceTagRelationService.getDeviceTags(deviceId);
		info.put("tags", tags);

		return R.data(info);
	}

	/**
	 * 根据标签批量获取设备标签关联信息
	 */
	@ApiOperation("根据标签批量获取设备标签关联信息")
	@PostMapping("/tags/device-relations")
	public R<Map<String, Object>> getDeviceRelationsByTags(
		@ApiParam("标签ID列表") @RequestBody List<Long> tagIds) {

		Map<String, Object> result = new HashMap<>();

		for (Long tagId : tagIds) {
			List<Map<String, Object>> devices = vlsDeviceTagRelationService.getDevicesByTag(tagId);
			result.put("tag_" + tagId, devices);
		}

		return R.data(result);
	}

}
