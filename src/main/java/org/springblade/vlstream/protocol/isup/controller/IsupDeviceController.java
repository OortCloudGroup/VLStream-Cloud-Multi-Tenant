package org.springblade.vlstream.protocol.isup.controller;

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
import org.springblade.vlstream.protocol.isup.entity.IsupDeviceEntity;
import org.springblade.vlstream.protocol.isup.service.IIsupDeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/isup/lsupDevice")
@Tag(name = "协议-ISUP设备", description = "ISUP设备接口")
public class IsupDeviceController extends BladeController {

	private final IIsupDeviceService isupDeviceService;

	@GetMapping("/ptzCtrl")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "云台控制", description = "ISUP云台控制")
	public R<String> ptzCtrl(@RequestParam Integer lUserID,
							 @RequestParam Integer direction,
							 @RequestParam Integer controSpeed) {
		return R.fail("ISUP云台控制未配置");
	}

	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "查询ISUP设备")
	public R<IPage<IsupDeviceEntity>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> params, Query query) {
		IPage<IsupDeviceEntity> pages = isupDeviceService.page(Condition.getPage(query), Condition.getQueryWrapper(params, IsupDeviceEntity.class));
		return R.data(pages);
	}

	@GetMapping("/lsupDeviceList")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "列表", description = "查询ISUP设备列表")
	public R<List<IsupDeviceEntity>> lsupDeviceList(IsupDeviceEntity query) {
		return R.data(isupDeviceService.list(Condition.getQueryWrapper(query)));
	}

	@GetMapping("/{id}")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "详情", description = "查询ISUP设备详情")
	public R<IsupDeviceEntity> detail(@PathVariable Long id) {
		return R.data(isupDeviceService.getById(id));
	}

	@PostMapping
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增", description = "新增ISUP设备")
	public R<Boolean> add(@RequestBody IsupDeviceEntity entity) {
		return R.status(isupDeviceService.saveWithUrl(entity));
	}

	@PutMapping
	@ApiOperationSupport(order = 6)
	@Operation(summary = "修改", description = "修改ISUP设备")
	public R<Boolean> edit(@RequestBody IsupDeviceEntity entity) {
		return R.status(isupDeviceService.updateWithUrl(entity));
	}

	@DeleteMapping("/{id}")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "删除", description = "删除ISUP设备")
	public R<Boolean> remove(@PathVariable Long id) {
		return R.status(isupDeviceService.removeById(id));
	}
}
