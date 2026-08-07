package org.springblade.vlstream.protocol.onvif.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.vlstream.protocol.onvif.entity.OnvifDeviceEntity;
import org.springblade.vlstream.protocol.onvif.service.IOnvifDeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/onvif/device")
@Tag(name = "协议-ONVIF设备", description = "ONVIF设备接口")
public class OnvifDeviceController extends BladeController {

	private final IOnvifDeviceService onvifDeviceService;

	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "分页", description = "查询ONVIF设备")
	public R<IPage<OnvifDeviceEntity>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> params, Query query) {
		IPage<OnvifDeviceEntity> pages = onvifDeviceService.page(Condition.getPage(query), Condition.getQueryWrapper(params, OnvifDeviceEntity.class));
		return R.data(pages);
	}

	@GetMapping("/deviceList")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "列表", description = "查询ONVIF设备列表")
	public R<List<OnvifDeviceEntity>> deviceList(OnvifDeviceEntity query) {
		return R.data(onvifDeviceService.list(Condition.getQueryWrapper(query)));
	}

	@GetMapping("/{id}")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "详情", description = "查询ONVIF设备详情")
	public R<OnvifDeviceEntity> detail(@PathVariable Long id) {
		return R.data(onvifDeviceService.getById(id));
	}

	@PostMapping
	@ApiOperationSupport(order = 4)
	@Operation(summary = "新增", description = "新增ONVIF设备")
	public R<OnvifDeviceEntity> add(@RequestBody OnvifDeviceEntity entity) {
		OnvifDeviceEntity exists = onvifDeviceService.getOneByIp(entity == null ? null : entity.getIp());
		if (exists != null) {
			return R.fail("设备已存在");
		}
		boolean saved = onvifDeviceService.save(entity);
		return saved ? R.data(entity) : R.fail("新增失败");
	}

	@PutMapping
	@ApiOperationSupport(order = 5)
	@Operation(summary = "修改", description = "修改ONVIF设备")
	public R<Boolean> edit(@RequestBody OnvifDeviceEntity entity) {
		return R.status(onvifDeviceService.updateById(entity));
	}

	@DeleteMapping("/{id}")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "删除", description = "删除ONVIF设备")
	public R<Boolean> remove(@PathVariable Long id) {
		return R.status(onvifDeviceService.removeById(id));
	}
}
