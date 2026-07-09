package org.springblade.modules.system.pojo.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Data transfer object entity class
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
