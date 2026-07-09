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
@Tag(name = "protocol-ISUPequipment", description = "ISUPDevice interface")
public class IsupDeviceController extends BladeController {

	private final IIsupDeviceService isupDeviceService;

	@GetMapping("/ptzCtrl")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "PTZ control", description = "ISUPPTZ control")
	public R<String> ptzCtrl(@RequestParam Integer lUserID,
							 @RequestParam Integer direction,
							 @RequestParam Integer controSpeed) {
		return R.fail("ISUPPTZ control is not configured");
	}

	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "QueryISUPequipment")
	public R<IPage<IsupDeviceEntity>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> params, Query query) {
		IPage<IsupDeviceEntity> pages = isupDeviceService.page(Condition.getPage(query), Condition.getQueryWrapper(params, IsupDeviceEntity.class));
		return R.data(pages);
	}

	@GetMapping("/lsupDeviceList")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "list", description = "QueryISUPDevice list")
	public R<List<IsupDeviceEntity>> lsupDeviceList(IsupDeviceEntity query) {
		return R.data(isupDeviceService.list(Condition.getQueryWrapper(query)));
	}

	@GetMapping("/{id}")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Details", description = "QueryISUPDevice details")
	public R<IsupDeviceEntity> detail(@PathVariable Long id) {
		return R.data(isupDeviceService.getById(id));
	}

	@PostMapping
	@ApiOperationSupport(order = 5)
	@Operation(summary = "New", description = "NewISUPequipment")
	public R<Boolean> add(@RequestBody IsupDeviceEntity entity) {
		return R.status(isupDeviceService.saveWithUrl(entity));
	}

	@PutMapping
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Revise", description = "ReviseISUPequipment")
	public R<Boolean> edit(@RequestBody IsupDeviceEntity entity) {
		return R.status(isupDeviceService.updateWithUrl(entity));
	}

	@DeleteMapping("/{id}")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "delete", description = "deleteISUPequipment")
	public R<Boolean> remove(@PathVariable Long id) {
		return R.status(isupDeviceService.removeById(id));
	}
}
