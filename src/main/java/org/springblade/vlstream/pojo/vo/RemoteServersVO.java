package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.RemoteServers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * Remote server configuration table View entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RemoteServersVO extends RemoteServers {
	@Serial
	private static final long serialVersionUID = 1L;

}
