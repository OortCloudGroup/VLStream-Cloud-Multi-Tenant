package org.springblade.vlstream.detection;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Instance split start trigger: Trigger an instance split task refresh when the application starts. 
 */
@Component
@ConditionalOnProperty(value = "vlstream.instance-seg-detection.enabled", havingValue = "true", matchIfMissing = false)
public class DeviceInstanceSegDetectionRunner extends AbstractDetectionRunner<DeviceInstanceSegDetectionManager> {

	public DeviceInstanceSegDetectionRunner(ObjectProvider<DeviceInstanceSegDetectionManager> detectionManagerProvider) {
		super(detectionManagerProvider);
	}
}
