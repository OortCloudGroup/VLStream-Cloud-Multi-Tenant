package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.DeviceTagRelation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 设备标签关联表 视图实体类
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceTagRelationVO extends DeviceTagRelation {
	@Serial
	private static final long serialVersionUID = 1L;

}
