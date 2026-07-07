package org.springblade.vlstream.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springblade.vlstream.pojo.entity.DeviceInfo;
import org.springblade.vlstream.pojo.entity.EventManagement;
import org.springblade.vlstream.service.IVlsDeviceInfoService;
import org.springblade.vlstream.service.IVlsEventManagementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@EnableScheduling
public class AlarmRecordSyncJob {


	@Value("${vlstream.reportUrl}")
	private String reportUrl;

	@Resource
	private IVlsEventManagementService eventManagementService;

	@Resource
	private IVlsDeviceInfoService deviceInfoService;

	/**
	 * 每10秒执行一次（cron表达式：秒 分 时 日 月 周）
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	@Transactional(rollbackFor = Exception.class)
	public void reportUnsentEvent() {
		List<EventManagement> eventManagementList = eventManagementService.lambdaQuery()
			.eq(EventManagement::getIsReport, 0)
			.list();
		if (CollUtil.isEmpty(eventManagementList)) {
			return;
		}
		List<EventManagement> successList = new ArrayList<>();
		List<List<EventManagement>> partitions = CollUtil.split(eventManagementList, 100);
		for (List<EventManagement> batch : partitions) {
			List<Map<String, Object>> eventReports = batch.stream()
				.map(this::buildReportPayload)
				.collect(Collectors.toList());
			Map<String, Object> requestBody = new HashMap<>(2);
			requestBody.put("event_report", eventReports);
			try {
				HttpResponse response = HttpRequest.post(reportUrl)
					.header("Content-Type", "application/json")
					.body(JSONUtil.toJsonStr(requestBody))
					.timeout(5000)
					.execute();
				log.info("推送事件结果：{}", response.body());
				if (response.getStatus() == HttpStatus.HTTP_OK) {
					batch.forEach(warning -> {
						warning.setIsReport(1);
						warning.setUpdateTime(new Date());
					});
					successList.addAll(batch);
				} else {
					log.error("Batch warning report failed: status={}, size={}, body={}",
						response.getStatus(), batch.size(), response.body());
				}
			} catch (Exception reportException) {
				log.error("Batch warning report error: size={}", batch.size(), reportException);
			}
		}
		if (CollUtil.isNotEmpty(successList)) {
			eventManagementService.updateBatchById(successList);
		}
	}


	private Map<String, Object> buildReportPayload(EventManagement eventManagement) {
		DeviceInfo deviceInfo = deviceInfoService.getByDeviceId(eventManagement.getReportDevice());
		Map<String, Object> payload = new HashMap<>(8);
		payload.put("describe", eventManagement.getHandleResult());
		payload.put("device_id", eventManagement.getReportDevice() == null ? "" : eventManagement.getReportDevice());
		payload.put("device_name", deviceInfo.getDeviceName());
		payload.put("device_tag", "");
		payload.put("device_tenant_id", StrUtil.emptyToDefault(eventManagement.getTenantId(), ""));
		payload.put("name", eventManagement.getHandleResult());
		payload.put("pics", buildPics(eventManagement.getReportImg()));
		payload.put("point", buildEmptyPoint());
		payload.put("video", Collections.emptyList());
		return payload;
	}

	private List<String> buildPics(String imageUrl) {
		if (StrUtil.isBlank(imageUrl)) {
			return Collections.emptyList();
		}
		return Collections.singletonList(imageUrl);
	}

	private Map<String, Object> buildEmptyPoint() {
		Map<String, Object> point = new HashMap<>(8);
		point.put("address", "水产大厦");
		point.put("coord_system_type", null);
		point.put("coord_system_type_change", null);
		point.put("lat", 114.12006299999996);
		point.put("lat_change", null);
		point.put("lng", 22.555658);
		point.put("lng_change", null);
		return point;
	}


}
