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
 * equipmentInstance splittingTask管理器: Regularly scan device configuration and maintain instance split sessions. 
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "vlstream.instance-seg-detection.enabled", havingValue = "true", matchIfMissing = false)
public class DeviceInstanceSegDetectionManager extends AbstractDeviceDetectionManager<DeviceInstanceSegDetectionSession> {

    @Scheduled(fixedDelayString = "${vlstream.instance-seg-detection.refresh-interval-millis:30000}")
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
            log.warn("equipment {} No flow address configured, Skip instance splitting", deviceInfo.getDeviceName());
            return null;
        }

        AlgorithmSelection algorithmSelection = selectAlgorithmByCategory(
            deviceInfo,
            AlgorithmCategoryEnum.segment,
            "Instance splitting",
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
    protected DeviceInstanceSegDetectionSession createSession(DesiredDeviceConfig desiredConfig) {
        return new DeviceInstanceSegDetectionSession(
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
        return "The device is not configured with an instance split algorithm or the configuration is incomplete.";
    }

    @Override
    protected String getConfigChangedReason() {
        return "Device instance split configuration changes";
    }

    @Override
    protected String getRefreshErrorMessage() {
        return "Refresh device instance split task failed";
    }

    @Override
    protected String getStopErrorMessage() {
        return "Stop device instance split failed: deviceId={}, reason={}";
    }

}
