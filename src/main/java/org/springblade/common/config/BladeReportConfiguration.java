package org.springblade.common.config;

import org.springblade.core.report.datasource.ReportDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Report configuration class
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "report.enabled", havingValue = "true", matchIfMissing = true)
public class BladeReportConfiguration {

	/**
	 * Optional data sources for custom reports
	 */
	@Bean
	public ReportDataSource reportDataSource(DataSource dataSource) {
		return new ReportDataSource(dataSource);
	}

}
