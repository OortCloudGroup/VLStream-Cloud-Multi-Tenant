package org.springblade.common.config;

import org.springblade.common.filter.PreviewFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Demo configuration class
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "blade.preview.enabled", havingValue = "true")
public class BladePreviewConfiguration {

	/**
	 * Demo mode configuration
	 */
	@Bean
	public PreviewFilter previewFilter() {
		return new PreviewFilter();
	}


}
