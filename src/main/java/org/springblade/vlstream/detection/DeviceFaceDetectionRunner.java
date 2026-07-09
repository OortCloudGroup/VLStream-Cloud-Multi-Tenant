package org.springblade.vlstream.detection;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Face detection start trigger: Trigger a face detection task refresh when the application starts. 
 */
@Component
@ConditionalOnProperty(value = "vlstream.face-detection.enabled", havingValue = "true", matchIfMissing = false)
public class DeviceFaceDetectionRunner extends AbstractDetectionRunner<DeviceFaceDetectionManager> {

	public DeviceFaceDetectionRunner(ObjectProvider<DeviceFaceDetectionManager> detectionManagerProvider) {
		super(detectionManagerProvider);
	}
}
