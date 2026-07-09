package org.springblade.vlstream.detection;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Attitude estimation start trigger: Trigger a pose estimation task refresh when the application starts. 
 */
@Component
@ConditionalOnProperty(value = "vlstream.pose-detection.enabled", havingValue = "true", matchIfMissing = false)
public class DevicePoseDetectionRunner extends AbstractDetectionRunner<DevicePoseDetectionManager> {

	public DevicePoseDetectionRunner(ObjectProvider<DevicePoseDetectionManager> detectionManagerProvider) {
		super(detectionManagerProvider);
	}
}
