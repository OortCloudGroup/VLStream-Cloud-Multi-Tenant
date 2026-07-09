package org.springblade.vlstream.detection;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springblade.vlstream.enums.AlgorithmCategoryEnum;
import org.springblade.vlstream.pojo.entity.Algorithm;
import org.springblade.vlstream.pojo.entity.DeviceInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Device human detection task manager: Schedule scan device configuration and maintain human detection sessions. 
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "vlstream.person-detection.enabled", havingValue = "true", matchIfMissing = true)
public class DevicePersonDetectionManager extends AbstractDeviceDetectionManager<DevicePersonDetectionSession> {

    @Scheduled(fixedDelayString = "${vlstream.person-detection.refresh-interval-millis:30000}")
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
            return null;
        }

        AlgorithmSelection algorithmSelection = selectAlgorithmByCategory(
            deviceInfo,
            AlgorithmCategoryEnum.personDetect,
            "Human detection",
            this::resolveOnnxModelSourcePath,
            (modelPath, latestModel) -> isOnnxModel(modelPath, latestModel)
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
    protected DevicePersonDetectionSession createSession(DesiredDeviceConfig desiredConfig) {
        return new DevicePersonDetectionSession(
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
        return "The device is not configured with an algorithm or the configuration is incomplete.";
    }

    @Override
    protected String getConfigChangedReason() {
        return "Device detection configuration changes";
    }

    @Override
    protected String getRefreshErrorMessage() {
        return "Refresh device detection task failed";
    }

    @Override
    protected String getStopErrorMessage() {
        return "Stop device detection failed: deviceId={}, reason={}";
    }

}
