package org.springblade.modules.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springblade.modules.system.pojo.entity.RecordData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 数据审计表 视图实体类
 *
 * @author Oort
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RecordDataVO extends RecordData {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 记录账号
	 */
	@Schema(description = "记录账号")
	private String recordUserName;

}
