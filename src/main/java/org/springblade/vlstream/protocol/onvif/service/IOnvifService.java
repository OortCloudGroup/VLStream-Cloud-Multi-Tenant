package org.springblade.vlstream.protocol.onvif.service;

import org.springblade.core.tool.api.R;
import org.springblade.vlstream.protocol.onvif.dto.OnvifAbsoluteMoveDTO;
import org.springblade.vlstream.protocol.onvif.dto.OnvifFetchStreamUrisDTO;
import org.springblade.vlstream.protocol.onvif.dto.OnvifPresetsDTO;
import org.springblade.vlstream.protocol.onvif.vo.OnvifStreamUrisVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IOnvifService {

	OnvifStreamUrisVO getOnvifDeviceInfo(OnvifFetchStreamUrisDTO dto);

	List<Map<String, String>> getChannelToken(OnvifFetchStreamUrisDTO dto);

	Set<String> getDigitalChannel(OnvifFetchStreamUrisDTO dto);

	R<Void> generateAbsoluteMove(OnvifAbsoluteMoveDTO dto);

	R<Void> generateContinuousMove(OnvifAbsoluteMoveDTO dto);

	R<Void> continuousMoveStop(OnvifAbsoluteMoveDTO dto);

	List<Map<String, String>> getPresetList(OnvifAbsoluteMoveDTO dto);

	void gotoPreset(OnvifPresetsDTO dto);

	void removePreset(OnvifPresetsDTO dto);

	void addPreset(OnvifPresetsDTO dto);
}
