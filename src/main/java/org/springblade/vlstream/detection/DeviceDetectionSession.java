package org.springblade.vlstream.detection;

/**
 * Device detection session abstraction: Used to determine whether the configuration matches, and manage the launch of sessions/Stop life cycle. 
 */
public interface DeviceDetectionSession {

	boolean matches(Long algorithmId, String streamUrl, String modelSourcePath);

	boolean start();

	void stop(String reason);
}
