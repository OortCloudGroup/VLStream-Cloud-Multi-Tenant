package org.springblade.common.event;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.launch.props.BladeProperties;
import org.springblade.core.launch.server.ServerInfo;
import org.springblade.core.log.constant.EventConstant;
import org.springblade.core.log.event.ErrorLogEvent;
import org.springblade.core.log.model.LogError;
import org.springblade.core.log.utils.LogAbstractUtil;
import org.springblade.modules.system.service.ILogService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 异步监听错误日志事件
 *
 * @author Chill
 */
@Slf4j
@AllArgsConstructor
public class ErrorLogListener {

	private final ILogService logService;
	private final ServerInfo serverInfo;
	private final BladeProperties bladeProperties;

	@Async
	@Order
	@EventListener(ErrorLogEvent.class)
	public void saveErrorLog(ErrorLogEvent event) {
		try {
			Map<String, Object> source = (Map<String, Object>) event.getSource();
			LogError logError = (LogError) source.get(EventConstant.EVENT_LOG);
			LogAbstractUtil.addOtherInfoToLog(logError, bladeProperties, serverInfo);
			logService.saveErrorLog(logError);
		} catch (Exception e) {
			// 可以根据需要进行更多的异常处理，例如发送警报等
			log.error("保存错误日志时发生异常", e);
		}
	}
}
