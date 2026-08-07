package org.springblade.vlstream.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.VideoRecord;

import java.io.Serial;

/**
 * 视频录制记录表 数据传输对象实体类
 *
 * @author Oort
 * @since 2025-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VideoRecordDTO extends VideoRecord {
	@Serial
	private static final long serialVersionUID = 1L;

}
