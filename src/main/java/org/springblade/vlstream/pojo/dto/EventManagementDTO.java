package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.EventManagement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * event management table Data transfer object entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EventManagementDTO extends EventManagement {
	@Serial
	private static final long serialVersionUID = 1L;

}
