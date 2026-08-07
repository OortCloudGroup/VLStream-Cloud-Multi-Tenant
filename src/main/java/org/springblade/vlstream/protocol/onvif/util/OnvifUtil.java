package org.springblade.vlstream.protocol.onvif.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.api.R;
import org.springblade.vlstream.protocol.onvif.dto.OnvifAbsoluteMoveDTO;
import org.springblade.vlstream.protocol.onvif.dto.OnvifFetchStreamUrisDTO;
import org.springblade.vlstream.protocol.onvif.dto.OnvifPresetsDTO;
import org.springblade.vlstream.protocol.onvif.vo.OnvifStreamUrisVO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class OnvifUtil {

	public static void setPreset(OnvifPresetsDTO preset) {
		HttpResponse response = postSoapRequest(preset.getIp(), SetPresetRequest(preset));
		if (response.getStatus() != 200) {
			throw new RuntimeException("添加预置点失败");
		}
	}

	public static void removePreset(OnvifPresetsDTO preset) {
		HttpResponse response = postSoapRequest(preset.getIp(), RemovePresetRequest(preset));
		if (response.getStatus() != 200) {
			throw new RuntimeException("删除预置点失败");
		}
	}

	public static void gotoPreset(OnvifPresetsDTO preset) {
		HttpResponse response = postSoapRequest(preset.getIp(), GotoPresetsStopRequest(preset));
		if (response.getStatus() != 200) {
			throw new RuntimeException("gotoPreset失败");
		}
	}

	public static List<Map<String, String>> getPresets(OnvifAbsoluteMoveDTO moveDTO) {
		HttpResponse response = postSoapRequest(moveDTO.getIp(), GetPresetsRequest(moveDTO));
		if (response.getStatus() == 200) {
			try {
				return parseSoapResponseGetPresets(response.body());
			} catch (Exception exception) {
				log.error("解析预置点失败", exception);
			}
		}
		throw new RuntimeException("获取预置点列表失败");
	}

	public static Set<String> getDigitalChannel(OnvifFetchStreamUrisDTO deviceInfo) {
		HttpResponse response = postSoapRequest(deviceInfo.getIp(), GetDigitalChannel(deviceInfo));
		if (response.getStatus() == 200) {
			OnvifStreamUrisVO info = getOnvifDeviceInfo(deviceInfo);
			try {
				if ("Dahua".equalsIgnoreCase(info.getFirm())) {
					return parseSoapResponseDigitalChannelDahua(response.body());
				}
				return parseSoapResponseDigitalChannelHikvision(response.body());
			} catch (Exception exception) {
				log.error("解析数字通道失败", exception);
			}
		}
		throw new RuntimeException("获取数字通道失败");
	}

	public static R<Void> continuousMoveStop(OnvifAbsoluteMoveDTO moveDTO) {
		HttpResponse response = postSoapRequest(moveDTO.getIp(), GenerateStopRequest(moveDTO));
		if (response.getStatus() == 200) {
			return R.success("OK");
		}
		if (response.getStatus() == 500) {
			throw new RuntimeException("该命名空间设备不支持");
		}
		throw new RuntimeException("鉴权失败");
	}

	public static R<Void> continuousMove(OnvifAbsoluteMoveDTO moveDTO) {
		HttpResponse response = postSoapRequest(moveDTO.getIp(), GenerateContinuousMoveRequest(moveDTO));
		if (response.getStatus() == 200) {
			return R.success("OK");
		}
		if (response.getStatus() == 500) {
			throw new RuntimeException("该命名空间设备不支持");
		}
		throw new RuntimeException("鉴权失败");
	}

	public static R<Void> absoluteMove(OnvifAbsoluteMoveDTO moveDTO) {
		HttpResponse response = postSoapRequest(moveDTO.getIp(), GenerateAbsoluteMoveRequest(moveDTO));
		if (response.getStatus() == 200) {
			return R.success("OK");
		}
		if (response.getStatus() == 500) {
			throw new RuntimeException("该命名空间设备不支持");
		}
		throw new RuntimeException("鉴权失败");
	}

	public static OnvifStreamUrisVO getOnvifDeviceInfo(OnvifFetchStreamUrisDTO deviceInfo) {
		OnvifStreamUrisVO basicInfo = getBasicDeviceInfo(deviceInfo);
		List<String> profileTokens = getProfileToken(deviceInfo);
		for (String token : profileTokens) {
			String urlByToken = getProfilesUrlByToken(deviceInfo, token);
			String authUrl = urlByToken.replace("rtsp://", "rtsp://" + deviceInfo.getUsername() + ":" + deviceInfo.getPassword() + "@");
			basicInfo.addStreamUri(token, authUrl);
		}
		return basicInfo;
	}

	public static String getProfilesUrlByToken(OnvifFetchStreamUrisDTO deviceInfo, String profileToken) {
		HttpResponse response = postSoapRequest(deviceInfo.getIp(), GetProfilesUrl(deviceInfo, profileToken));
		if (response.getStatus() == 200) {
			try {
				return parseSoapResponseProfilesUrlByToken(response.body());
			} catch (Exception exception) {
				log.error("解析流地址失败", exception);
			}
		} else if (response.getStatus() == 500) {
			throw new RuntimeException("该命名空间设备不支持");
		} else if (response.getStatus() == 401) {
			throw new RuntimeException("鉴权失败");
		}
		throw new RuntimeException("获取视频流地址失败");
	}

	public static List<String> getProfileToken(OnvifFetchStreamUrisDTO deviceInfo) {
		HttpResponse response = postSoapRequest(deviceInfo.getIp(), GetProfiles(deviceInfo));
		if (response.getStatus() == 200) {
			try {
				return parseSoapResponseProfileToken(response.body());
			} catch (Exception exception) {
				log.error("解析ProfileToken失败", exception);
			}
		} else if (response.getStatus() == 500) {
			throw new RuntimeException("该命名空间设备不支持");
		} else if (response.getStatus() == 401) {
			throw new RuntimeException("鉴权失败");
		}
		throw new RuntimeException("获取流token失败");
	}

	private static OnvifStreamUrisVO getBasicDeviceInfo(OnvifFetchStreamUrisDTO deviceInfo) {
		HttpResponse response = postSoapRequest(deviceInfo.getIp(), GetDeviceInformation(deviceInfo));
		if (response.getStatus() == 200) {
			try {
				return parseSoapResponseDeviceInfo(response.body());
			} catch (Exception exception) {
				log.error("解析设备信息失败", exception);
			}
		} else if (response.getStatus() == 500) {
			throw new RuntimeException("该命名空间设备不支持");
		} else if (response.getStatus() == 401) {
			throw new RuntimeException("鉴权失败");
		}
		throw new RuntimeException("获取基本信息失败");
	}

	private static HttpResponse postSoapRequest(String ip, String body) {
		String url = "http://" + ip + "/onvif/media_service";
		return HttpRequest.post(url)
			.header("Content-Type", "application/soap+xml; charset=utf-8")
			.body(body)
			.execute();
	}

	private static List<Map<String, String>> parseSoapResponseGetPresets(String responseBody) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes()));
		NodeList presetNodes = document.getElementsByTagName("tptz:Preset");
		List<Map<String, String>> presets = new ArrayList<>();
		for (int index = 0; index < presetNodes.getLength(); index++) {
			Node presetNode = presetNodes.item(index);
			if (presetNode.getNodeType() == Node.ELEMENT_NODE) {
				Element presetElement = (Element) presetNode;
				String token = presetElement.getAttribute("token");
				NodeList nameNodes = presetElement.getElementsByTagName("tt:Name");
				String name = nameNodes.getLength() > 0 ? nameNodes.item(0).getTextContent() : "";
				Map<String, String> preset = new HashMap<>();
				preset.put("token", token);
				preset.put("name", name);
				presets.add(preset);
			}
		}
		return presets;
	}

	private static Set<String> parseSoapResponseDigitalChannelHikvision(String responseBody) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes()));
		NodeList videoSourceConfigNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "SourceToken");
		Set<String> digitalChannels = new HashSet<>();
		Pattern pattern = Pattern.compile("\\d+");
		for (int index = 0; index < videoSourceConfigNodes.getLength(); index++) {
			Node node = videoSourceConfigNodes.item(index);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String sourceTokenValue = node.getTextContent().trim();
				Matcher matcher = pattern.matcher(sourceTokenValue);
				if (matcher.find()) {
					digitalChannels.add(matcher.group());
				}
			}
		}
		return digitalChannels;
	}

	private static Set<String> parseSoapResponseDigitalChannelDahua(String responseBody) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes()));
		NodeList videoSourceConfigNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "VideoSourceConfiguration");
		Set<String> uniqueNumbers = new HashSet<>();
		Pattern pattern = Pattern.compile("\\d+");
		for (int index = 0; index < videoSourceConfigNodes.getLength(); index++) {
			Node videoSourceConfigNode = videoSourceConfigNodes.item(index);
			if (videoSourceConfigNode.getNodeType() == Node.ELEMENT_NODE) {
				Element videoSourceConfigElement = (Element) videoSourceConfigNode;
				NodeList nameNodes = videoSourceConfigElement.getElementsByTagNameNS("http://www.onvif.org/ver10/schema", "Name");
				if (nameNodes.getLength() > 0) {
					String nameValue = nameNodes.item(0).getTextContent().trim();
					Matcher matcher = pattern.matcher(nameValue);
					if (matcher.find()) {
						uniqueNumbers.add(matcher.group());
					}
				}
			}
		}
		return uniqueNumbers;
	}

	private static String parseSoapResponseProfilesUrlByToken(String responseBody) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));
		NodeList uriNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver20/media/wsdl", "Uri");
		if (uriNodes.getLength() > 0) {
			return uriNodes.item(0).getTextContent();
		}
		throw new RuntimeException("Uri not found in the SOAP response");
	}

	private static List<String> parseSoapResponseProfileToken(String responseBody) throws Exception {
		List<String> profileNames = new ArrayList<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));
		NodeList profilesNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver20/media/wsdl", "Profiles");
		for (int index = 0; index < profilesNodes.getLength(); index++) {
			Element profileElement = (Element) profilesNodes.item(index);
			String token = profileElement.getAttribute("token");
			if (token != null && !token.isEmpty()) {
				profileNames.add(token);
			}
		}
		return profileNames;
	}

	private static OnvifStreamUrisVO parseSoapResponseDeviceInfo(String responseBody) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new java.io.ByteArrayInputStream(responseBody.getBytes("UTF-8")));
		NodeList manufacturerNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "Manufacturer");
		NodeList modelNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "Model");
		NodeList firmwareVersionNodes = document.getElementsByTagNameNS("http://www.onvif.org/ver10/device/wsdl", "FirmwareVersion");
		OnvifStreamUrisVO info = new OnvifStreamUrisVO();
		info.setFirm(manufacturerNodes.item(0).getTextContent());
		info.setModel(modelNodes.item(0).getTextContent());
		info.setFirmwareVersion(firmwareVersionNodes.item(0).getTextContent());
		return info;
	}

	private static String calculatePasswordDigest(byte[] nonceBytes, String created, String password) {
		byte[] createdBytes = created.getBytes(CharsetUtil.CHARSET_UTF_8);
		byte[] passwordBytes = password.getBytes(CharsetUtil.CHARSET_UTF_8);
		byte[] combinedBytes = new byte[nonceBytes.length + createdBytes.length + passwordBytes.length];
		System.arraycopy(nonceBytes, 0, combinedBytes, 0, nonceBytes.length);
		System.arraycopy(createdBytes, 0, combinedBytes, nonceBytes.length, createdBytes.length);
		System.arraycopy(passwordBytes, 0, combinedBytes, nonceBytes.length + createdBytes.length, passwordBytes.length);
		return Base64.encode(DigestUtil.sha1(combinedBytes));
	}

	private static String GetDeviceInformation(OnvifFetchStreamUrisDTO deviceInfo) {
		DigestContext context = DigestContext.from(deviceInfo.getUsername(), deviceInfo.getPassword());
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tds=\"http://www.onvif.org/ver10/device/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">\n" +
			"  <s:Header xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
			"    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
			"      <wsse:UsernameToken>\n" +
			"        <wsse:Username>" + context.username + "</wsse:Username>\n" +
			"        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + context.passwordDigest + "</wsse:Password>\n" +
			"        <wsse:Nonce>" + context.nonce + "</wsse:Nonce>\n" +
			"        <wsu:Created>" + context.created + "</wsu:Created>\n" +
			"      </wsse:UsernameToken>\n" +
			"    </wsse:Security>\n" +
			"  </s:Header>\n" +
			"  <soap:Body>\n" +
			"    <tds:GetDeviceInformation />\n" +
			"  </soap:Body>\n" +
			"</soap:Envelope>";
	}

	private static String GetProfiles(OnvifFetchStreamUrisDTO deviceInfo) {
		DigestContext context = DigestContext.from(deviceInfo.getUsername(), deviceInfo.getPassword());
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
			"  <s:Header>\n" +
			"    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
			"      <wsse:UsernameToken>\n" +
			"        <wsse:Username>" + context.username + "</wsse:Username>\n" +
			"        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + context.passwordDigest + "</wsse:Password>\n" +
			"        <wsse:Nonce>" + context.nonce + "</wsse:Nonce>\n" +
			"        <wsu:Created>" + context.created + "</wsu:Created>\n" +
			"      </wsse:UsernameToken>\n" +
			"    </wsse:Security>\n" +
			"  </s:Header>\n" +
			"  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
			"    <GetProfiles xmlns=\"http://www.onvif.org/ver20/media/wsdl\" />\n" +
			"  </s:Body>\n" +
			"</s:Envelope>";
	}

	private static String GetProfilesUrl(OnvifFetchStreamUrisDTO deviceInfo, String profileToken) {
		DigestContext context = DigestContext.from(deviceInfo.getUsername(), deviceInfo.getPassword());
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
			"  <s:Header>\n" +
			"    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
			"      <wsse:UsernameToken>\n" +
			"        <wsse:Username>" + context.username + "</wsse:Username>\n" +
			"        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + context.passwordDigest + "</wsse:Password>\n" +
			"        <wsse:Nonce>" + context.nonce + "</wsse:Nonce>\n" +
			"        <wsu:Created>" + context.created + "</wsu:Created>\n" +
			"      </wsse:UsernameToken>\n" +
			"    </wsse:Security>\n" +
			"  </s:Header>\n" +
			"  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
			"    <GetStreamUri xmlns=\"http://www.onvif.org/ver20/media/wsdl\">\n" +
			"      <Protocol>RtspUnicast</Protocol>\n" +
			"      <ProfileToken>" + profileToken + "</ProfileToken>\n" +
			"    </GetStreamUri>\n" +
			"  </s:Body>\n" +
			"</s:Envelope>";
	}

	private static String GenerateAbsoluteMoveRequest(OnvifAbsoluteMoveDTO moveDTO) {
		DigestContext context = DigestContext.from(moveDTO.getUsername(), moveDTO.getPassword());
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">\n" +
			"  <s:Header>\n" +
			"    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
			"      <wsse:UsernameToken>\n" +
			"        <wsse:Username>" + context.username + "</wsse:Username>\n" +
			"        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + context.passwordDigest + "</wsse:Password>\n" +
			"        <wsse:Nonce>" + context.nonce + "</wsse:Nonce>\n" +
			"        <wsu:Created>" + context.created + "</wsu:Created>\n" +
			"      </wsse:UsernameToken>\n" +
			"    </wsse:Security>\n" +
			"  </s:Header>\n" +
			"  <s:Body>\n" +
			"    <tptz:AbsoluteMove>\n" +
			"      <tptz:ProfileToken>" + moveDTO.getProfileToken() + "</tptz:ProfileToken>\n" +
			"      <tptz:Position>\n" +
			"        <tt:PanTilt x=\"" + moveDTO.getX() + "\" y=\"" + moveDTO.getY() + "\" space=\"http://www.onvif.org/ver10/tptz/PanTiltSpaces/PositionGenericSpace\" />\n" +
			"      </tptz:Position>\n" +
			"    </tptz:AbsoluteMove>\n" +
			"  </s:Body>\n" +
			"</s:Envelope>";
	}

	private static String GenerateContinuousMoveRequest(OnvifAbsoluteMoveDTO moveDTO) {
		DigestContext context = DigestContext.from(moveDTO.getUsername(), moveDTO.getPassword());
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">\n" +
			"  <s:Header>\n" +
			"    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
			"      <wsse:UsernameToken>\n" +
			"        <wsse:Username>" + context.username + "</wsse:Username>\n" +
			"        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + context.passwordDigest + "</wsse:Password>\n" +
			"        <wsse:Nonce>" + context.nonce + "</wsse:Nonce>\n" +
			"        <wsu:Created>" + context.created + "</wsu:Created>\n" +
			"      </wsse:UsernameToken>\n" +
			"    </wsse:Security>\n" +
			"  </s:Header>\n" +
			"  <s:Body>\n" +
			"    <tptz:ContinuousMove>\n" +
			"      <tptz:ProfileToken>" + moveDTO.getProfileToken() + "</tptz:ProfileToken>\n" +
			"      <tptz:Velocity>\n" +
			"        <tt:PanTilt x=\"" + moveDTO.getX() + "\" y=\"" + moveDTO.getY() + "\" />\n" +
			"        <tt:Zoom x=\"0\" />\n" +
			"      </tptz:Velocity>\n" +
			"    </tptz:ContinuousMove>\n" +
			"  </s:Body>\n" +
			"</s:Envelope>";
	}

	private static String GenerateStopRequest(OnvifAbsoluteMoveDTO moveDTO) {
		DigestContext context = DigestContext.from(moveDTO.getUsername(), moveDTO.getPassword());
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" +
			"  <s:Header>\n" +
			"    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
			"      <wsse:UsernameToken>\n" +
			"        <wsse:Username>" + context.username + "</wsse:Username>\n" +
			"        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + context.passwordDigest + "</wsse:Password>\n" +
			"        <wsse:Nonce>" + context.nonce + "</wsse:Nonce>\n" +
			"        <wsu:Created>" + context.created + "</wsu:Created>\n" +
			"      </wsse:UsernameToken>\n" +
			"    </wsse:Security>\n" +
			"  </s:Header>\n" +
			"  <s:Body>\n" +
			"    <tptz:Stop>\n" +
			"      <tptz:ProfileToken>" + moveDTO.getProfileToken() + "</tptz:ProfileToken>\n" +
			"      <tptz:PanTilt>true</tptz:PanTilt>\n" +
			"      <tptz:Zoom>true</tptz:Zoom>\n" +
			"    </tptz:Stop>\n" +
			"  </s:Body>\n" +
			"</s:Envelope>";
	}

	private static String GetDigitalChannel(OnvifFetchStreamUrisDTO deviceInfo) {
		DigestContext context = DigestContext.from(deviceInfo.getUsername(), deviceInfo.getPassword());
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:trt=\"http://www.onvif.org/ver10/media/wsdl\" xmlns:tt=\"http://www.onvif.org/ver10/schema\">\n" +
			"  <s:Header xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
			"    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
			"      <wsse:UsernameToken>\n" +
			"        <wsse:Username>" + context.username + "</wsse:Username>\n" +
			"        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + context.passwordDigest + "</wsse:Password>\n" +
			"        <wsse:Nonce>" + context.nonce + "</wsse:Nonce>\n" +
			"        <wsu:Created>" + context.created + "</wsu:Created>\n" +
			"      </wsse:UsernameToken>\n" +
			"    </wsse:Security>\n" +
			"  </s:Header>\n" +
			"  <soap:Body>\n" +
			"    <trt:GetProfiles />\n" +
			"  </soap:Body>\n" +
			"</soap:Envelope>";
	}

	private static String GetPresetsRequest(OnvifAbsoluteMoveDTO moveDTO) {
		DigestContext context = DigestContext.from(moveDTO.getUsername(), moveDTO.getPassword());
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" +
			"  <s:Header>\n" +
			"    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
			"      <wsse:UsernameToken>\n" +
			"        <wsse:Username>" + context.username + "</wsse:Username>\n" +
			"        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + context.passwordDigest + "</wsse:Password>\n" +
			"        <wsse:Nonce>" + context.nonce + "</wsse:Nonce>\n" +
			"        <wsu:Created>" + context.created + "</wsu:Created>\n" +
			"      </wsse:UsernameToken>\n" +
			"    </wsse:Security>\n" +
			"  </s:Header>\n" +
			"  <s:Body>\n" +
			"    <tptz:GetPresets>\n" +
			"      <tptz:ProfileToken>" + moveDTO.getProfileToken() + "</tptz:ProfileToken>\n" +
			"    </tptz:GetPresets>\n" +
			"  </s:Body>\n" +
			"</s:Envelope>";
	}

	private static String GotoPresetsStopRequest(OnvifPresetsDTO preset) {
		DigestContext context = DigestContext.from(preset.getUsername(), preset.getPassword());
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" +
			"  <s:Header>\n" +
			"    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
			"      <wsse:UsernameToken>\n" +
			"        <wsse:Username>" + context.username + "</wsse:Username>\n" +
			"        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + context.passwordDigest + "</wsse:Password>\n" +
			"        <wsse:Nonce>" + context.nonce + "</wsse:Nonce>\n" +
			"        <wsu:Created>" + context.created + "</wsu:Created>\n" +
			"      </wsse:UsernameToken>\n" +
			"    </wsse:Security>\n" +
			"  </s:Header>\n" +
			"  <s:Body>\n" +
			"    <tptz:GotoPreset>\n" +
			"      <tptz:ProfileToken>" + preset.getProfileToken() + "</tptz:ProfileToken>\n" +
			"      <tptz:PresetToken>" + preset.getPresetToken() + "</tptz:PresetToken>\n" +
			"    </tptz:GotoPreset>\n" +
			"  </s:Body>\n" +
			"</s:Envelope>";
	}

	private static String RemovePresetRequest(OnvifPresetsDTO preset) {
		DigestContext context = DigestContext.from(preset.getUsername(), preset.getPassword());
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" +
			"  <s:Header>\n" +
			"    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
			"      <wsse:UsernameToken>\n" +
			"        <wsse:Username>" + context.username + "</wsse:Username>\n" +
			"        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + context.passwordDigest + "</wsse:Password>\n" +
			"        <wsse:Nonce>" + context.nonce + "</wsse:Nonce>\n" +
			"        <wsu:Created>" + context.created + "</wsu:Created>\n" +
			"      </wsse:UsernameToken>\n" +
			"    </wsse:Security>\n" +
			"  </s:Header>\n" +
			"  <s:Body>\n" +
			"    <tptz:RemovePreset>\n" +
			"      <tptz:ProfileToken>" + preset.getProfileToken() + "</tptz:ProfileToken>\n" +
			"      <tptz:PresetToken>" + preset.getPresetToken() + "</tptz:PresetToken>\n" +
			"    </tptz:RemovePreset>\n" +
			"  </s:Body>\n" +
			"</s:Envelope>";
	}

	private static String SetPresetRequest(OnvifPresetsDTO preset) {
		DigestContext context = DigestContext.from(preset.getUsername(), preset.getPassword());
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tptz=\"http://www.onvif.org/ver20/ptz/wsdl\">\n" +
			"  <s:Header>\n" +
			"    <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
			"      <wsse:UsernameToken>\n" +
			"        <wsse:Username>" + context.username + "</wsse:Username>\n" +
			"        <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">" + context.passwordDigest + "</wsse:Password>\n" +
			"        <wsse:Nonce>" + context.nonce + "</wsse:Nonce>\n" +
			"        <wsu:Created>" + context.created + "</wsu:Created>\n" +
			"      </wsse:UsernameToken>\n" +
			"    </wsse:Security>\n" +
			"  </s:Header>\n" +
			"  <s:Body>\n" +
			"    <tptz:SetPreset>\n" +
			"      <tptz:ProfileToken>" + preset.getProfileToken() + "</tptz:ProfileToken>\n" +
			"      <tptz:PresetName>" + preset.getPresetToken() + "</tptz:PresetName>\n" +
			"    </tptz:SetPreset>\n" +
			"  </s:Body>\n" +
			"</s:Envelope>";
	}

	private static class DigestContext {
		private final String username;
		private final String passwordDigest;
		private final String nonce;
		private final String created;

		private DigestContext(String username, String passwordDigest, String nonce, String created) {
			this.username = username;
			this.passwordDigest = passwordDigest;
			this.nonce = nonce;
			this.created = created;
		}

		private static DigestContext from(String username, String password) {
			byte[] nonceBytes = RandomUtil.randomBytes(16);
			String nonce = Base64.encode(nonceBytes);
			String created = Instant.now().toString();
			String passwordDigest = calculatePasswordDigest(nonceBytes, created, password);
			return new DigestContext(username, passwordDigest, nonce, created);
		}
	}
}
