package org.springblade.vlstream.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.DeviceInfo;
import org.springblade.vlstream.pojo.entity.DeviceTagRelation;
import org.springblade.vlstream.pojo.entity.TagManagement;

import java.io.Serial;

/**
 * Device tag association table Data transfer object entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceTagRelationDTO extends DeviceTagRelation {
	@Serial
	private static final long serialVersionUID = 1L;

	// The following are related objects, Does not correspond to database fields
	@Schema(description = "Device information", hidden = true)
	private DeviceInfo deviceInfo;

	@Schema(description = "Label information", hidden = true)
	private TagManagement tagInfo;

	@Schema(description = "Tag name", hidden = true)
	private String tagName;

	@Schema(description = "Tag type", hidden = true)
	private String categoryType;

	@Schema(description = "Label color", hidden = true)
	private String tagColor;

}
