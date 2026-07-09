package org.springblade.vlstream.detection;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springblade.vlstream.enums.AlgorithmCategoryEnum;
import org.springblade.vlstream.pojo.entity.DeviceInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Device Face Detection Task Manager: Scan device configuration regularly and maintain face detection sessions. 
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "vlstream.face-detection.enabled", havingValue = "true", matchIfMissing = false)
public class DeviceFaceDetectionManager extends AbstractDeviceDetectionManager<DeviceFaceDetectionSession> {

    @Scheduled(fixedDelayString = "${vlstream.face-detection.refresh-interval-millis:30000}")
    public void scheduledRefresh() {
        scheduledRefreshSessionsInternal();
    }

    @Override
    protected DesiredDeviceConfig buildDesiredConfig(DeviceInfo deviceInfo) {
        if (deviceInfo == null || deviceInfo.getId() == null) {
            return null;
        }
        String streamUrl = resolveStreamUrl(deviceInfo);
        if (StringUtils.isBlank(streamUrl)) {
            log.warn("equipment {} No flow address configured, Skip face detection", deviceInfo.getDeviceName());
            return null;
        }

        AlgorithmSelection algorithmSelection = selectAlgorithmByCategory(
            deviceInfo,
            AlgorithmCategoryEnum.faceDetect,
            "Face detection",
            this::resolveDefaultModelSourcePath,
            null
        );
        if (algorithmSelection == null || StringUtils.isBlank(algorithmSelection.modelSourcePath)) {
            return null;
        }

        Path outputDir = resolveOutputDir(deviceInfo);
        return new DesiredDeviceConfig(
            deviceInfo,
            algorithmSelection.algorithm,
            algorithmSelection.algorithmId,
            streamUrl,
            algorithmSelection.modelSourcePath,
            outputDir
        );
    }

    @Override
    protected DeviceFaceDetectionSession createSession(DesiredDeviceConfig desiredConfig) {
        return new DeviceFaceDetectionSession(
            sshProperties,
            sshService,
            fileUploadService,
            eventManagementService,
            desiredConfig.deviceInfo,
            desiredConfig.algorithm,
            desiredConfig.algorithmId,
            desiredConfig.streamUrl,
            desiredConfig.modelSourcePath,
            desiredConfig.outputDir
        );
    }

    @Override
    protected String getMissingConfigReason() {
        return "The device is not configured with a face detection algorithm or the configuration is incomplete.";
    }

    @Override
    protected String getConfigChangedReason() {
        return "Device face detection configuration changes";
    }

    @Override
    protected String getRefreshErrorMessage() {
        return "Refresh device face detection task failed";
    }

    @Override
    protected String getStopErrorMessage() {
        return "Stop device face detection failure: deviceId={}, reason={}";
    }

}
