package org.springblade.vlstream.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MQTTConfiguration
 */
@Data
@Component
@ConfigurationProperties(prefix = "vlstream.mqtt")
public class VlsMqttProperties {

	/**
	 * MQTTService address
	 */
	private String host;

	/**
	 * MQTTservice port
	 */
	private Integer port;

	/**
	 * MQTTusername
	 */
	private String username;

	/**
	 * MQTTpassword
	 */
	private String password;

	/**
	 * Common theme prefix
	 */
	private String topicPrefix = "oortcloud";

	/**
	 * Camera display settings message topic
	 */
	private String vlsCameraDisplaySettingTopic = "oortcloud/vlsCameraDisplaySetting";

	/**
	 * cameraOSDSet message topic
	 */
	private String vlsCameraOsdSettingTopic = "oortcloud/vlsCameraOsdSetting";

	/**
	 * Audio anomaly detection setting message theme
	 */
	private String vlsAudioAnomalyDetectionSettingTopic = "oortcloud/vlsAudioAnomalyDetectionSetting";

	/**
	 * Audio arming time setting message theme
	 */
	private String vlsAudioDefenseTimeSettingTopic = "oortcloud/vlsAudioDefenseTimeSetting";

	/**
	 * Audio linkage method to set message theme
	 */
	private String vlsAudioLinkageModeSettingTopic = "oortcloud/vlsAudioLinkageModeSetting";

	/**
	 * Time policy message topic
	 */
	private String vlsTimeStrategyTopic = "oortcloud/vlsTimeStrategy";

	/**
	 * Recording event policy message topic
	 */
	private String vlsRecordEventStrategyTopic = "oortcloud/vlsRecordEventStrategy";

	/**
	 * MQTTclientIDprefix
	 */
	private String clientIdPrefix = "vls-dispatch";

	/**
	 * MQTTMessage quality level
	 */
	private Integer qos = 1;

	/**
	 * MQTTheartbeat duration(Second)
	 */
	private Integer keepAliveSeconds = 60;

	/**
	 * MQTTConnection timeout(Second)
	 */
	private Integer connectionTimeoutSeconds = 10;

}
