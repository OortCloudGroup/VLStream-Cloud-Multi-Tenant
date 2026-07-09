package org.springblade.vlstream.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.vlstream.pojo.entity.CameraDisplaySetting;
import org.springblade.vlstream.pojo.vo.CameraDisplaySettingVO;

/**
 * Camera display setting table Packaging
 */
public class VlsCameraDisplaySettingWrapper extends BaseEntityWrapper<CameraDisplaySetting, CameraDisplaySettingVO> {

	public static VlsCameraDisplaySettingWrapper build() {
		return new VlsCameraDisplaySettingWrapper();
	}

	@Override
	public CameraDisplaySettingVO entityVO(CameraDisplaySetting cameraDisplaySetting) {
		if (cameraDisplaySetting == null) {
			return null;
		}
		return BeanUtil.copyProperties(cameraDisplaySetting, CameraDisplaySettingVO.class);
	}
}
