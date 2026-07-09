package org.springblade.common.config;


import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.oauth2.endpoint.OAuth2SocialEndpoint;
import org.springblade.core.oauth2.endpoint.OAuth2TokenEndPoint;
import org.springblade.core.secure.provider.HttpMethod;
import org.springblade.core.secure.registry.SecureRegistry;
import org.springblade.core.tool.utils.StringPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * BladeConfiguration
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
public class BladeConfiguration implements WebMvcConfigurer {

	/**
	 * Security framework configuration
	 */
	@Bean
	public SecureRegistry secureRegistry() {
		return new SecureRegistry()
			// Enable authentication configuration
			.enabled()
			// Token strict mode configuration
			.strictTokenDisabled()
			// Request header strict mode configuration
			.strictHeaderDisabled()
			// Authentication release configuration
			.skipUrls(
				"/blade-auth/**",
				"/blade-system/tenant/info",
				"/blade-flow/process/resource-view",
				"/blade-flow/process/diagram-view",
				"/blade-flow/manager/check-upload",
				"/vlsEventManagement/report",
				"/vlsDeviceInfo/latest-training-model",
				"/vlsAlgorithmTraining/download-model",
				"/doc.html",
				"/swagger-ui.html",
				"/static/**",
				"/webjars/**",
				"/swagger-resources/**",
				"/druid/**",
				"/favicon.ico"
			)
			// Authentication configuration
			.authDisabled()
			.addAuthPattern(HttpMethod.ALL, "/blade-chat/message/**", "hasAuth()")
			.addAuthPattern(HttpMethod.POST, "/blade-desk/dashboard/upload", "hasTimeAuth(9, 17)")
			.addAuthPattern(HttpMethod.POST, "/blade-desk/dashboard/submit", "hasAnyRole('administrator', 'admin', 'user')")
			// Basic authentication configuration
			.basicDisabled()
			.addBasicPattern(HttpMethod.POST, "/blade-desk/dashboard/info", "blade", "blade")
			// Signature authentication configuration
			.signDisabled()
			.addSignPattern(HttpMethod.POST, "/blade-desk/dashboard/sign", "sha1");
	}

	/**
	 * Cross-domain configuration
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOriginPatterns("*")
			.allowedHeaders("*")
			.allowedMethods("*")
			.maxAge(3600)
			.allowCredentials(true);
	}

	/**
	 * GiveOAuth2Add prefix on server side
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.addPathPrefix(StringPool.SLASH + AppConstant.APPLICATION_AUTH_NAME,
			c -> c.isAnnotationPresent(RestController.class) && (
				OAuth2TokenEndPoint.class.equals(c) || OAuth2SocialEndpoint.class.equals(c))
		);
	}

}
