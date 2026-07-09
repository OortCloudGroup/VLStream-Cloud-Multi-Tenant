package org.springblade.vlstream.protocol.onvif.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springblade.vlstream.protocol.onvif.dto.OnvifAbsoluteMoveDTO;
import org.springblade.vlstream.protocol.onvif.dto.OnvifFetchStreamUrisDTO;
import org.springblade.vlstream.protocol.onvif.dto.OnvifPresetsDTO;
import org.springblade.vlstream.protocol.onvif.service.IOnvifService;
import org.springblade.vlstream.protocol.onvif.vo.OnvifStreamUrisVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/onvif/service")
@Tag(name = "protocol-ONVIFcontrol", description = "ONVIFcontrol interface")
public class OnvifServiceController extends BladeController {

	private final IOnvifService onvifService;

	@GetMapping("/getDigitalChannel")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Get digital channel")
	public R<Set<String>> getDigitalChannel(OnvifFetchStreamUrisDTO dto) {
		return R.data(onvifService.getDigitalChannel(dto));
	}

	@GetMapping("/getInfo")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Get device information")
	public R<OnvifStreamUrisVO> getOnvifDeviceInfo(OnvifFetchStreamUrisDTO dto) {
		return R.data(onvifService.getOnvifDeviceInfo(dto));
	}

	@GetMapping("/getChannelToken")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Get channeltoken")
	public R<List<Map<String, String>>> getChannelToken(OnvifFetchStreamUrisDTO dto) {
		return R.data(onvifService.getChannelToken(dto));
	}

	@GetMapping("/absoluteMove")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Absolute position movement")
	public R<Void> absoluteMove(OnvifAbsoluteMoveDTO dto) {
		return onvifService.generateAbsoluteMove(dto);
	}

	@GetMapping("/continuousMove")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "continuous movement")
	public R<Void> continuousMove(OnvifAbsoluteMoveDTO dto) {
		return onvifService.generateContinuousMove(dto);
	}

	@GetMapping("/continuousMoveStop")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Continuous movement stops")
	public R<Void> continuousMoveStop(OnvifAbsoluteMoveDTO dto) {
		return onvifService.continuousMoveStop(dto);
	}

	@GetMapping("/getPresets")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "Get preset point")
	public R<List<Map<String, String>>> getPresets(OnvifAbsoluteMoveDTO dto) {
		return R.data(onvifService.getPresetList(dto));
	}

	@GetMapping("/gotoPreset")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Jump to preset point")
	public R<Void> gotoPreset(OnvifPresetsDTO dto) {
		onvifService.gotoPreset(dto);
		return R.success("OK");
	}

	@GetMapping("/removePreset")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "Delete preset point")
	public R<Void> removePreset(OnvifPresetsDTO dto) {
		onvifService.removePreset(dto);
		return R.success("OK");
	}

	@GetMapping("/addPreset")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "Add preset point")
	public R<Void> addPreset(OnvifPresetsDTO dto) {
		onvifService.addPreset(dto);
		return R.success("OK");
	}
}
