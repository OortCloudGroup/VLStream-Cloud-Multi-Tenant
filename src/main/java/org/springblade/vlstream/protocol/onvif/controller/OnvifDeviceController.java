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
@Tag(name = "protocol-ONVIFequipment", description = "ONVIFDevice interface")
public class OnvifDeviceController extends BladeController {

	private final IOnvifDeviceService onvifDeviceService;

	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Pagination", description = "QueryONVIFequipment")
	public R<IPage<OnvifDeviceEntity>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> params, Query query) {
		IPage<OnvifDeviceEntity> pages = onvifDeviceService.page(Condition.getPage(query), Condition.getQueryWrapper(params, OnvifDeviceEntity.class));
		return R.data(pages);
	}

	@GetMapping("/deviceList")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "list", description = "QueryONVIFDevice list")
	public R<List<OnvifDeviceEntity>> deviceList(OnvifDeviceEntity query) {
		return R.data(onvifDeviceService.list(Condition.getQueryWrapper(query)));
	}

	@GetMapping("/{id}")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Details", description = "QueryONVIFDevice details")
	public R<OnvifDeviceEntity> detail(@PathVariable Long id) {
		return R.data(onvifDeviceService.getById(id));
	}

	@PostMapping
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "NewONVIFequipment")
	public R<OnvifDeviceEntity> add(@RequestBody OnvifDeviceEntity entity) {
		OnvifDeviceEntity exists = onvifDeviceService.getOneByIp(entity == null ? null : entity.getIp());
		if (exists != null) {
			return R.fail("Device already exists");
		}
		boolean saved = onvifDeviceService.save(entity);
		return saved ? R.data(entity) : R.fail("Failed to add");
	}

	@PutMapping
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "ReviseONVIFequipment")
	public R<Boolean> edit(@RequestBody OnvifDeviceEntity entity) {
		return R.status(onvifDeviceService.updateById(entity));
	}

	@DeleteMapping("/{id}")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "delete", description = "deleteONVIFequipment")
	public R<Boolean> remove(@PathVariable Long id) {
		return R.status(onvifDeviceService.removeById(id));
	}
}
