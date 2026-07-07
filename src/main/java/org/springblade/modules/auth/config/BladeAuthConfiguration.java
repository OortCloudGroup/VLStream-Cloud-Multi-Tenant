package org.springblade.modules.auth.config;

import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.oauth2.config.OAuth2AutoConfiguration;
import org.springblade.core.oauth2.handler.AuthorizationHandler;
import org.springblade.core.oauth2.handler.PasswordHandler;
import org.springblade.core.oauth2.handler.TokenHandler;
import org.springblade.core.oauth2.props.OAuth2Properties;
import org.springblade.core.oauth2.service.OAuth2ClientService;
import org.springblade.core.oauth2.service.OAuth2UserService;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tenant.BladeTenantProperties;
import org.springblade.modules.auth.handler.BladeAuthorizationHandler;
import org.springblade.modules.auth.handler.BladeLockHandler;
import org.springblade.modules.auth.handler.BladePasswordHandler;
import org.springblade.modules.auth.handler.BladeTokenHandler;
import org.springblade.modules.auth.service.BladeClientDetailService;
import org.springblade.modules.auth.service.BladeUserDetailService;
import org.springblade.modules.system.service.IUserService;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * BladeAuthConfiguration
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(OAuth2AutoConfiguration.class)
public class BladeAuthConfiguration {
	@Bean
	public AuthorizationHandler authorizationHandler(BladeProperties bladeProperties,
													 BladeTenantProperties tenantProperties,
													 OAuth2Properties oAuth2Properties,
													 BladeLockHandler lockHandler) {
		return new BladeAuthorizationHandler(bladeProperties, tenantProperties, oAuth2Properties, lockHandler);
	}

	@Bean
	public BladeLockHandler lockHandler(BladeRedis bladeRedis) {
		return new BladeLockHandler(bladeRedis);
	}

	@Bean
	public PasswordHandler passwordHandler(OAuth2Properties properties) {
		return new BladePasswordHandler(properties);
	}

	@Bean
	public TokenHandler tokenHandler(JwtProperties jwtProperties) {
		return new BladeTokenHandler(jwtProperties);
	}

	@Bean
	public OAuth2ClientService oAuth2ClientService(JdbcTemplate jdbcTemplate) {
		return new BladeClientDetailService(jdbcTemplate);
	}

	@Bean
	public OAuth2UserService oAuth2UserService(IUserService userService) {
		return new BladeUserDetailService(userService);
	}

}
