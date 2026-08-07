package org.springblade.common.config;

import lombok.AllArgsConstructor;
import org.springblade.common.handler.BladePermissionHandler;
import org.springblade.common.handler.BladeRecordHandler;
import org.springblade.common.handler.BladeScopeModelHandler;
import org.springblade.core.datarecord.processor.DataRecordHandler;
import org.springblade.core.datascope.handler.ScopeModelHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Blade处理器自动配置
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class BladeHandlerConfiguration {

	private final JdbcTemplate jdbcTemplate;

	@Bean
	public DataRecordHandler dataRecordHandler() {
		return new BladeRecordHandler(jdbcTemplate);
	}

	@Bean
	public ScopeModelHandler scopeModelHandler() {
		return new BladeScopeModelHandler(jdbcTemplate);
	}

	@Bean
	public BladePermissionHandler permissionHandler() {
		return new BladePermissionHandler(jdbcTemplate);
	}

}
