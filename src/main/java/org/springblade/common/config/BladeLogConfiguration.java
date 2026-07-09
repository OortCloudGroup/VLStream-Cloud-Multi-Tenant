package org.springblade.common.config;

import lombok.AllArgsConstructor;
import org.springblade.common.event.ApiLogListener;
import org.springblade.common.event.ErrorLogListener;
import org.springblade.common.event.UsualLogListener;
import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.launch.server.ServerInfo;
import org.springblade.modules.system.service.ILogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Log tool automatic configuration
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class BladeLogConfiguration {

	private final ILogService logService;
	private final ServerInfo serverInfo;
	private final BladeProperties bladeProperties;

	@Bean(name = "apiLogListener")
	public ApiLogListener apiLogListener() {
		return new ApiLogListener(logService, serverInfo, bladeProperties);
	}

	@Bean(name = "errorEventListener")
	public ErrorLogListener errorEventListener() {
		return new ErrorLogListener(logService, serverInfo, bladeProperties);
	}

	@Bean(name = "usualEventListener")
	public UsualLogListener usualEventListener() {
		return new UsualLogListener(logService, serverInfo, bladeProperties);
	}

}
