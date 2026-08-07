package org.springblade.common.config;

import org.springblade.core.report.datasource.ReportDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 报表配置类
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "report.enabled", havingValue = "true", matchIfMissing = true)
public class BladeReportConfiguration {

	/**
	 * 自定义报表可选数据源
	 */
	@Bean
	public ReportDataSource reportDataSource(DataSource dataSource) {
		return new ReportDataSource(dataSource);
	}

}
