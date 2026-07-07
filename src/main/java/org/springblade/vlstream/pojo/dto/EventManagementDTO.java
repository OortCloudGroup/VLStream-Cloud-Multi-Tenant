package org.springblade.vlstream.pojo.dto;

import org.springblade.vlstream.pojo.entity.EventManagement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 事件管理表 数据传输对象实体类
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
