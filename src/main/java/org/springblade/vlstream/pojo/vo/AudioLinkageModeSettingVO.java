package org.springblade.vlstream.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.AudioLinkageModeSetting;

import java.io.Serial;

/**
 * 音频联动方式设置表 视图实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AudioLinkageModeSettingVO extends AudioLinkageModeSetting {
	@Serial
	private static final long serialVersionUID = 1L;
}
