package org.springblade.job.processor;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springblade.vlstream.detection.DeviceDetectionOrchestrator;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;
import tech.powerjob.worker.log.OmsLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * 场景治理摄像头检测任务：触发 detection 包下所有算法会话刷新（按 JobParams 中的摄像头ID集合）。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessorDeviceObjectDetect implements BasicProcessor {

    private final DeviceDetectionOrchestrator detectionOrchestrator;

    @Override
    public ProcessResult process(TaskContext taskContext) {
        OmsLogger omsLogger = taskContext.getOmsLogger();
        String jobParams = StringUtils.trimToEmpty(taskContext.getJobParams());
        omsLogger.info("ProcessorDeviceObjectDetect start, jobParams={}", jobParams);

        List<Long> cameraIds = splitToIds(jobParams);
        if (cameraIds.isEmpty()) {
            omsLogger.warn("ProcessorDeviceObjectDetect skipped: empty cameraIds");
            return new ProcessResult(false, "cameraIds is required in jobParams");
        }

        log.info("============== ProcessorDeviceObjectDetect#process ==============");
        log.info("taskContext参数：{}", JSONUtil.toJsonStr(taskContext));
        log.info("cameraIds={}", cameraIds);

        try {
            detectionOrchestrator.refreshAllForDeviceIds(cameraIds);
            return new ProcessResult(true, "triggered detection sessions refresh");
        } catch (Exception exception) {
            omsLogger.error("ProcessorDeviceObjectDetect failed: {}", exception.getMessage());
            log.error("ProcessorDeviceObjectDetect failed", exception);
            return new ProcessResult(false, exception.getMessage());
        }
    }

    private List<Long> splitToIds(String rawIds) {
        if (StringUtils.isBlank(rawIds)) {
            return List.of();
        }
        String[] parts = rawIds.split(",");
        List<Long> ids = new ArrayList<>();
        for (String part : parts) {
            Long parsed = parseId(part);
            if (parsed != null) {
                ids.add(parsed);
            }
        }
        return ids;
    }

    private Long parseId(String rawId) {
        if (StringUtils.isBlank(rawId)) {
            return null;
        }
        try {
            return Long.valueOf(rawId.trim());
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

}
