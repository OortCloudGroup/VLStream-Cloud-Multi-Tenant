package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.TagManagement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Tag management table View entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TagManagementVO extends TagManagement {
	@Serial
	private static final long serialVersionUID = 1L;

}
