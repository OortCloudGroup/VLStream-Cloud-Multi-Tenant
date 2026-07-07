package org.springblade.vlstream.pojo.vo;

import org.springblade.vlstream.pojo.entity.RemoteServers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;

/**
 * 远程服务器配置表 视图实体类
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
