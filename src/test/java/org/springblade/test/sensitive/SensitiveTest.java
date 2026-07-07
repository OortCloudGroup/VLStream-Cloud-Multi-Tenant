package org.springblade.test.sensitive;

import lombok.Data;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.jackson.Sensitive;
import org.springblade.core.tool.sensitive.SensitiveConfig;
import org.springblade.core.tool.sensitive.SensitiveType;
import org.springblade.core.tool.sensitive.SensitiveUtil;
import org.springblade.core.tool.sensitive.SensitiveWord;

import java.util.*;
import java.util.regex.Pattern;

/**
 * SensitiveUtil使用示例
 * 展示各种使用场景和数据脱敏效果
 *
 * @author Oort
 */
public class SensitiveTest {

	public static void main(String[] args) {
		// 演示所有使用场景
		demonstrateBasicUsage();
		demonstrateCustomConfig();
		demonstrateSingleType();
		demonstrateMultipleTypes();
		demonstrateCustomRegex();
		demonstrateSensitiveWords();
		demonstrateComplexExample();
		demonstrateJacksonSerializer();
	}

	/**
	 * 1. 基础用法演示 - 使用默认配置
	 */
	private static void demonstrateBasicUsage() {
		System.out.println("\n=== 1.基础用法演示 ===");

		String content = "客户信息：\n" +
			"手机号: 13812345678\n" +
			"邮箱: test@example.com\n" +
			"身份证: 310123199001011234\n" +
			"银行卡: 6222021234567890123";

		String result = SensitiveUtil.process(content);
		System.out.println("原始内容：\n" + content);
		System.out.println("\n处理后：\n" + result);
	}

	/**
	 * 2. 自定义配置演示
	 */
	private static void demonstrateCustomConfig() {
		System.out.println("\n=== 2.自定义配置演示 ===");

		// 创建自定义配置
		SensitiveConfig config = SensitiveConfig.builder()
			.sensitiveTypes(EnumSet.of(
				SensitiveType.MOBILE,
				SensitiveType.EMAIL
			))
			.sensitiveWords(EnumSet.of(
				SensitiveWord.SECURE,
				SensitiveWord.AUTHENTICATION
			))
			.customPatterns(Map.of(
				"CustomerId", Pattern.compile("(?<=CustomerID:)\\s*\\w+"),
				"ProjectCode", Pattern.compile("(?<=ProjectCode:)\\s*\\w+")
			))
			.processLineByLine(true)
			.replacement("***已隐藏***")
			.build();

		String content = "用户数据：\n" +
			"手机: 13812345678\n" +
			"CustomerID: USER123456\n" +
			"ProjectCode: PRJ789\n" +
			"password=admin123\n" +
			"token=abcdef";

		// 转换为LogVO示例
		LogVO logVO = SensitiveUtil.process(content, config, data -> {
			LogVO dto = new LogVO();
			dto.setContent(data);
			dto.setTimestamp(System.currentTimeMillis());
			return dto;
		});

		System.out.println("原始内容：\n" + content);
		System.out.println("\n处理后：\n" + logVO.getContent());
	}

	/**
	 * 3. 单个敏感类型处理演示
	 */
	private static void demonstrateSingleType() {
		System.out.println("\n=== 3.单个敏感类型处理演示 ===");

		String content = "联系方式：13812345678，邮箱：test@example.com";
		String result = SensitiveUtil.process(content, SensitiveType.MOBILE);

		System.out.println("原始内容：" + content);
		System.out.println("处理后：" + result);
	}

	/**
	 * 4. 多个敏感类型处理演示
	 */
	private static void demonstrateMultipleTypes() {
		System.out.println("\n=== 4.多个敏感类型处理演示 ===");

		String content = "用户资料：\n" +
			"手机：13812345678\n" +
			"银行卡：6222021234567890123";

		UserVO userVO = SensitiveUtil.process(content, EnumSet.of(
			SensitiveType.MOBILE,
			SensitiveType.BANK_CARD
		), data -> {
			UserVO vo = new UserVO();
			vo.setUserInfo(data);
			return vo;
		});

		System.out.println("原始内容：\n" + content);
		System.out.println("\n处理后：\n" + userVO.getUserInfo());
	}

	/**
	 * 5. 自定义正则处理演示
	 */
	private static void demonstrateCustomRegex() {
		System.out.println("\n=== 5.自定义正则处理演示 ===");

		// 简单版本
		String simpleContent = "订单号：ORDER20250101001";
		String simpleResult = SensitiveUtil.processWithRegex(
			simpleContent,
			"ORDER\\d{8}\\d{3}"
		);

		System.out.println("简单版本：");
		System.out.println("原始内容：" + simpleContent);
		System.out.println("处理后：" + simpleResult);

		// 自定义替换符版本
		String customContent = "项目编号：PRJ20250101";
		String customResult = SensitiveUtil.processWithRegex(
			customContent,
			"PRJ\\d{8}",
			"PRJ********"
		);

		System.out.println("\n自定义替换符版本：");
		System.out.println("原始内容：" + customContent);
		System.out.println("处理后：" + customResult);
	}

	/**
	 * 6. 敏感词处理演示
	 */
	private static void demonstrateSensitiveWords() {
		System.out.println("\n=== 6.敏感词处理演示 ===");

		String content = "系统配置：\n" +
			"password=admin123\n" +
			"api_key=sk_test_123456\n" +
			"secret=my_secret_key\n" +
			"test=my_test_key";

		List<String> sensitiveWords = Arrays.asList("password", "api_key", "secret");

		// 完整版本的敏感词处理
		String result = SensitiveUtil.processWithWords(
			content,
			sensitiveWords,
			"***",// 可不填
			true // 可不填
		);

		System.out.println("原始内容：\n" + content);
		System.out.println("\n处理后：\n" + result);
	}

	/**
	 * 7. 复杂场景示例
	 */
	private static void demonstrateComplexExample() {
		System.out.println("\n=== 7.复杂场景示例 ===");

		String content = "API调用日志：\n" +
			"时间：2024-01-01 10:30:00\n" +
			"请求头：\n" +
			"Authorization: Bearer sk_test_123456\n" +
			"请求体：\n" +
			"{\n" +
			"    \"user\": {\n" +
			"        \"mobile\": \"13812345678\",\n" +
			"        \"email\": \"test@example.com\",\n" +
			"        \"idCard\": \"310123199001011234\",\n" +
			"        \"test\": \"2333333333\"\n" +
			"    },\n" +
			"    \"payment\": {\n" +
			"        \"cardNo\": \"6222021234567890123\",\n" +
			"        \"secretKey\": \"payment_secret_123\"\n" +
			"    }\n" +
			"}";

		// 创建复杂配置
		SensitiveConfig complexConfig = SensitiveConfig.builder()
			.sensitiveTypes(EnumSet.of(
				SensitiveType.MOBILE,
				SensitiveType.EMAIL,
				SensitiveType.ID_CARD
			))
			.sensitiveWords(EnumSet.allOf(SensitiveWord.class))
			.customPatterns(Map.of(
				"Bearer", Pattern.compile("Bearer\\s+[\\w-]+"),
				"SecretKey", Pattern.compile("(?<=secretKey\":\\s*\")[^\"]+")
			))
			.processLineByLine(true)
			.replacement("***")
			.build();

		ApiLogVO apiLogVO = SensitiveUtil.process(content, complexConfig, data -> {
			ApiLogVO vo = new ApiLogVO();
			vo.setRequestLog(data);
			vo.setTimestamp(System.currentTimeMillis());
			return vo;
		});

		System.out.println("原始内容：\n" + content);
		System.out.println("\n处理后：\n" + apiLogVO.getRequestLog());
	}

	/**
	 * 8. jackson序列化场景
	 */
	private static void demonstrateJacksonSerializer() {
		System.out.println("\n=== 8.jackson序列化场景 ===");

		AccountVO accountVO = new AccountVO();
		accountVO.setMobile("13812345678");
		accountVO.setEmail("test@example.com");
		accountVO.setCredential("系统配置：\n" +
			"password=admin123\n" +
			"api_key=sk_test_123456\n" +
			"secret=my_secret_key\n" +
			"test=my_test");
		accountVO.setToken("Bearer password 123456");
		accountVO.setOrderId("订单号：ORDER20250101001");

		System.out.println("原始内容：\n" + accountVO);
		System.out.println("\n处理后：\n" + JsonUtil.toJson(accountVO));
	}

	/**
	 * 数据传输对象
	 */
	@Data
	static class LogVO {
		private String content;
		private long timestamp;
	}

	@Data
	static class UserVO {
		private String userInfo;
	}

	@Data
	static class ApiLogVO {
		private String requestLog;
		private long timestamp;
	}

	@Data
	static class AccountVO {
		@Sensitive(type = SensitiveType.MOBILE)
		private String mobile;

		@Sensitive(type = SensitiveType.EMAIL)
		private String email;

		@Sensitive(word = SensitiveWord.SECURE)
		private String credential;

		@Sensitive(words = {"password", "secret"})
		private String token;

		@Sensitive(regex = "ORDER(.{2}).*(.{2})", replacement = "ORDER$1****$2")
		private String orderId;
	}
}
