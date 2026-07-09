package org.springblade.vlstream.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.vlstream.pojo.entity.TagManagement;

import java.io.Serial;
import java.util.List;

/**
 * Tag management table Data transfer object entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TagManagementDTO extends TagManagement {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "subtag list", hidden = true)
	private List<TagManagementDTO> children;

}
