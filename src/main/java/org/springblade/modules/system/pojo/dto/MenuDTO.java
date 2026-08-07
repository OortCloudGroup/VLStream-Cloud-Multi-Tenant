package org.springblade.modules.system.pojo.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 数据传输对象实体类
 *
 * @author Chill
 */
@Data
public class MenuDTO implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private String alias;
	private String path;
}
