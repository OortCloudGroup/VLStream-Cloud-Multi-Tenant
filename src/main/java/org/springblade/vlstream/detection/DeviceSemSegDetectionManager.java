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
 * Device Semantic Segmentation Task Manager: Regularly scan device configurations and maintain semantic segmentation sessions(Support images URL / Device image path as input). 
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "vlstream.semseg-detection.enabled", havingValue = "true", matchIfMissing = false)
public class DeviceSemSegDetectionManager extends AbstractDeviceDetectionManager<DeviceSemSegDetectionSession> {

    @Scheduled(fixedDelayString = "${vlstream.semseg-detection.refresh-interval-millis:30000}")
    public void scheduledRefresh() {
        scheduledRefreshSessionsInternal();
    }

    @Override
    protected DesiredDeviceConfig buildDesiredConfig(DeviceInfo deviceInfo) {
        if (deviceInfo == null || deviceInfo.getId() == null) {
            return null;
        }
        String streamUrl = resolveStreamUrl(deviceInfo);
        String imagePath = StringUtils.trimToNull(deviceInfo.getImagePath());
        if (StringUtils.isBlank(streamUrl) && StringUtils.isBlank(imagePath)) {
            log.warn("equipment {} Image source not configured, Skip semantic segmentation", deviceInfo.getDeviceName());
            return null;
        }
        if (StringUtils.isBlank(imagePath) && !isHttpUrl(streamUrl)) {
            log.warn("equipment {} Available image address is not configured, Skip semantic segmentation: streamUrl={}", deviceInfo.getDeviceName(), streamUrl);
            return null;
        }


        AlgorithmSelection algorithmSelection = selectAlgorithmByCategory(
            deviceInfo,
            AlgorithmCategoryEnum.semanticSeg,
            "Semantic segmentation",
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
    protected DeviceSemSegDetectionSession createSession(DesiredDeviceConfig desiredConfig) {
        return new DeviceSemSegDetectionSession(
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
        return "The device is not configured with a semantic segmentation algorithm or the configuration is incomplete.";
    }

    @Override
    protected String getConfigChangedReason() {
        return "Device semantic segmentation configuration changes";
    }

    @Override
    protected String getRefreshErrorMessage() {
        return "Refresh device semantic segmentation task failed";
    }

    @Override
    protected String getStopErrorMessage() {
        return "Stop device semantic segmentation failed: deviceId={}, reason={}";
    }

    private boolean isHttpUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        String lower = url.trim().toLowerCase();
        return lower.startsWith("http://") || lower.startsWith("https://");
    }
}
