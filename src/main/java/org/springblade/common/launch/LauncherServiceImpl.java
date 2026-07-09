package org.springblade.common.launch;

import org.springblade.common.constant.LauncherConstant;
import org.springblade.core.auto.service.AutoService;
import org.springblade.core.launch.service.LauncherService;
import org.springblade.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * Start parameter expansion
 *
 * @author smallchil
 */
@AutoService(LauncherService.class)
public class LauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		PropsUtil.setProperty(props, "spring.cloud.sentinel.transport.dashboard", LauncherConstant.sentinelAddr(profile));
		PropsUtil.setProperty(props, "spring.datasource.dynamic.enabled", "true");
		// turn onelklog
		//PropsUtil.setProperty(props, "blade.log.elk.destination", LauncherConstant.elkAddr(profile));
	}

}
