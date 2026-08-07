package org.springblade.vlstream.protocol.rtsp.controller;

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
import org.springblade.vlstream.protocol.rtsp.dto.AlarmClockDTO;
import org.springblade.vlstream.protocol.rtsp.entity.RtspDeviceEntity;
import org.springblade.vlstream.protocol.rtsp.service.IRtspDeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/rtsp/RtspDevice")
@Tag(name = "协议-RTSP设备", description = "RTSP设备接口")
public class RtspDeviceController extends BladeController {

	private final IRtspDeviceService rtspDeviceService;

	@GetMapping("/alarmClock")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "历史播放")
	public R<String> alarmClock(AlarmClockDTO dto) {
		return R.data(rtspDeviceService.buildPlaybackUrl(dto));
	}

	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "查询RTSP设备")
	public R<IPage<RtspDeviceEntity>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> params, Query query) {
		IPage<RtspDeviceEntity> pages = rtspDeviceService.page(Condition.getPage(query), Condition.getQueryWrapper(params, RtspDeviceEntity.class));
		return R.data(pages);
	}

	@GetMapping("/rtspDeviceList")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "列表", description = "查询RTSP设备列表")
	public R<List<RtspDeviceEntity>> rtspDeviceList(RtspDeviceEntity query) {
		return R.data(rtspDeviceService.list(Condition.getQueryWrapper(query)));
	}

	@GetMapping("/{id}")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "详情", description = "查询RTSP设备详情")
	public R<RtspDeviceEntity> detail(@PathVariable Long id) {
		return R.data(rtspDeviceService.getById(id));
	}

	@PostMapping
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增", description = "新增RTSP设备")
	public R<RtspDeviceEntity> add(@RequestBody RtspDeviceEntity entity) {
		boolean saved = rtspDeviceService.saveWithUrl(entity);
		return saved ? R.data(entity) : R.fail("新增失败");
	}

	@PutMapping
	@ApiOperationSupport(order = 6)
	@Operation(summary = "修改", description = "修改RTSP设备")
	public R<Boolean> edit(@RequestBody RtspDeviceEntity entity) {
		return R.status(rtspDeviceService.updateWithUrl(entity));
	}

	@DeleteMapping("/{id}")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "删除", description = "删除RTSP设备")
	public R<Boolean> remove(@PathVariable Long id) {
		return R.status(rtspDeviceService.removeById(id));
	}
}
