package org.springblade.vlstream.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.CameraDisplaySetting;

import java.io.Serial;

/**
 * 摄像机显示设置表 视图实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CameraDisplaySettingVO extends CameraDisplaySetting {
	@Serial
	private static final long serialVersionUID = 1L;
}
