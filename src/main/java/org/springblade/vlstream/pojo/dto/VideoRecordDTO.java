package org.springblade.vlstream.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.VideoRecord;

import java.io.Serial;

/**
 * Video recording record sheet Data transfer object entity class
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
