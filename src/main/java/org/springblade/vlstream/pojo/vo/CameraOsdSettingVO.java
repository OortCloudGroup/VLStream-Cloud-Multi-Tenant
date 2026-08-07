package org.springblade.vlstream.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.CameraOsdSetting;

import java.io.Serial;

/**
 * 摄像机OSD设置表 视图实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CameraOsdSettingVO extends CameraOsdSetting {
	@Serial
	private static final long serialVersionUID = 1L;
}
