package org.springblade.vlstream.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.AudioAnomalyDetectionSetting;

import java.io.Serial;

/**
 * 音频异常侦测设置表 视图实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AudioAnomalyDetectionSettingVO extends AudioAnomalyDetectionSetting {
	@Serial
	private static final long serialVersionUID = 1L;
}
