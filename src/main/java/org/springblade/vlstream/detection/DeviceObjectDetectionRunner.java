package org.springblade.vlstream.detection;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Target detection start trigger: Trigger a target detection task refresh when the application starts. 
 */
@Component
@ConditionalOnProperty(value = "vlstream.object-detection.enabled", havingValue = "true", matchIfMissing = false)
public class DeviceObjectDetectionRunner extends AbstractDetectionRunner<DeviceObjectDetectionManager> {

	public DeviceObjectDetectionRunner(ObjectProvider<DeviceObjectDetectionManager> detectionManagerProvider) {
		super(detectionManagerProvider);
	}
}
