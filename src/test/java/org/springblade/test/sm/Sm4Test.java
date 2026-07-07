package org.springblade.test.sm;

import lombok.SneakyThrows;
import org.springblade.core.tool.utils.SM4Util;
import org.springblade.core.tool.utils.StringUtil;

/**
 * Sm4Test
 *
 * @author BladeX
 */
public class Sm4Test {

	@SneakyThrows
	public static void main(String[] args) {
		System.out.println("================== SM4 加解密测试 ==================");

		// 1. 生成SM4密钥
		String sm4Key = SM4Util.generateKey();
		System.out.println("1. 生成SM4密钥 (16进制): " + sm4Key);
		System.out.println("   密钥长度 (Hex): " + sm4Key.length());

		System.out.println("--------------------------------------------------");

		// 2. 准备待加密的原文
		String originalText = "Hello BladeX! 这是一段需要被国密SM4算法加密的敏感信息。1234567890";
		System.out.println("2. 待加密的原文: " + originalText);

		System.out.println("--------------------------------------------------");

		// 3. 执行加密
		System.out.println("3. 正在执行加密操作...");
		String encryptedText = SM4Util.encrypt(originalText, sm4Key);
		if (StringUtil.isNotBlank(encryptedText)) {
			System.out.println("   加密成功！");
			System.out.println("   加密后的密文 (16进制): " + encryptedText);
		} else {
			System.out.println("   加密失败！");
			return;
		}

		System.out.println("--------------------------------------------------");

		// 4. 执行解密
		System.out.println("4. 正在执行解密操作...");
		String decryptedText = SM4Util.decrypt(encryptedText, sm4Key);
		if (StringUtil.isNotBlank(decryptedText)) {
			System.out.println("   解密成功！");
			System.out.println("   解密后的明文: " + decryptedText);
		} else {
			System.out.println("   解密失败！");
			return;
		}

		System.out.println("--------------------------------------------------");

		// 5. 校验结果
		System.out.println("5. 正在校验原文与解密后的明文是否一致...");
		boolean isMatch = originalText.equals(decryptedText);
		System.out.println("   校验结果: " + (isMatch ? "一致" : "不一致"));

		System.out.println("==================================================");

		if (isMatch) {
			System.out.println("\n[成功] SM4加解密流程验证通过！");
		} else {
			System.out.println("\n[失败] SM4加解密流程验证失败！");
		}
	}

}
