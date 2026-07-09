package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.EventManagement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * event management table View entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EventManagementVO extends EventManagement {
	@Serial
	private static final long serialVersionUID = 1L;

}
