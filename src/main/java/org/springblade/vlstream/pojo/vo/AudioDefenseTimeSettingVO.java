package org.springblade.vlstream.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.AudioDefenseTimeSetting;

import java.io.Serial;

/**
 * 音频布防时间设置表 视图实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AudioDefenseTimeSettingVO extends AudioDefenseTimeSetting {
	@Serial
	private static final long serialVersionUID = 1L;
}
