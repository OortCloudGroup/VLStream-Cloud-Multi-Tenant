package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.EventManagement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 事件管理表 视图实体类
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
