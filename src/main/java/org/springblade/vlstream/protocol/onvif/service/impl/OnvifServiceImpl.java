package org.springblade.vlstream.protocol.onvif.service.impl;

import org.springblade.core.tool.api.R;
import org.springblade.vlstream.protocol.onvif.dto.OnvifAbsoluteMoveDTO;
import org.springblade.vlstream.protocol.onvif.dto.OnvifFetchStreamUrisDTO;
import org.springblade.vlstream.protocol.onvif.dto.OnvifPresetsDTO;
import org.springblade.vlstream.protocol.onvif.service.IOnvifService;
import org.springblade.vlstream.protocol.onvif.util.OnvifUtil;
import org.springblade.vlstream.protocol.onvif.vo.OnvifStreamUrisVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OnvifServiceImpl implements IOnvifService {

	@Override
	public OnvifStreamUrisVO getOnvifDeviceInfo(OnvifFetchStreamUrisDTO dto) {
		return OnvifUtil.getOnvifDeviceInfo(dto);
	}

	@Override
	public List<Map<String, String>> getChannelToken(OnvifFetchStreamUrisDTO dto) {
		List<String> profileToken = OnvifUtil.getProfileToken(dto);
		return profileToken.stream()
			.map(token -> {
				Map<String, String> option = new HashMap<>();
				option.put("value", token);
				option.put("label", token);
				return option;
			})
			.collect(Collectors.toList());
	}

	@Override
	public Set<String> getDigitalChannel(OnvifFetchStreamUrisDTO dto) {
		return OnvifUtil.getDigitalChannel(dto);
	}

	@Override
	public R<Void> generateAbsoluteMove(OnvifAbsoluteMoveDTO dto) {
		return OnvifUtil.absoluteMove(dto);
	}

	@Override
	public R<Void> generateContinuousMove(OnvifAbsoluteMoveDTO dto) {
		return OnvifUtil.continuousMove(dto);
	}

	@Override
	public R<Void> continuousMoveStop(OnvifAbsoluteMoveDTO dto) {
		return OnvifUtil.continuousMoveStop(dto);
	}

	@Override
	public List<Map<String, String>> getPresetList(OnvifAbsoluteMoveDTO dto) {
		return OnvifUtil.getPresets(dto);
	}

	@Override
	public void gotoPreset(OnvifPresetsDTO dto) {
		OnvifUtil.gotoPreset(dto);
	}

	@Override
	public void removePreset(OnvifPresetsDTO dto) {
		OnvifUtil.removePreset(dto);
	}

	@Override
	public void addPreset(OnvifPresetsDTO dto) {
		OnvifUtil.setPreset(dto);
	}
}
