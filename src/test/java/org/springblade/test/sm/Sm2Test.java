package org.springblade.test.sm;

import lombok.SneakyThrows;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.util.encoders.Hex;
import org.springblade.core.tool.utils.SM2Util;
import org.springblade.core.tool.utils.StringUtil;

/**
 * SM2Test
 *
 * @author Chill
 */
public class Sm2Test {

	@SneakyThrows
	public static void main(String[] args) {
		System.out.println("================== SM2 加解密与签名测试 ==================");

		// 1. 生成SM2密钥对
		AsymmetricCipherKeyPair keyPair = SM2Util.generateKeyPair();
		String publicKeyString = SM2Util.getPublicKeyString(keyPair);
		String privateKeyString = SM2Util.getPrivateKeyString(keyPair);
		System.out.println("1. 生成SM2密钥对:");
		System.out.println("   公钥 (16进制): " + publicKeyString);
		System.out.println("   公钥长度: " + publicKeyString.length());
		System.out.println("   私钥 (16进制): " + privateKeyString);
		System.out.println("   私钥长度: " + privateKeyString.length());

		System.out.println("--------------------------------------------------");

		// 2. 从字符串恢复密钥
		System.out.println("2. 从字符串恢复密钥...");
		ECPublicKeyParameters publicKey = SM2Util.stringToPublicKey(publicKeyString);
		ECPrivateKeyParameters privateKey = SM2Util.stringToPrivateKey(privateKeyString);
		System.out.println("   密钥恢复成功！");

		System.out.println("--------------------------------------------------");

		// 3. 准备待加密的原文
		String originalText = "Hello BladeX! 这是一段需要被国密SM2算法加密和签名的敏感信息。1234567890";
		System.out.println("3. 待加密的原文: " + originalText);

		System.out.println("--------------------------------------------------");

		// 4. 执行加密
		System.out.println("4. 正在执行加密操作...");
		byte[] encryptedData = SM2Util.encrypt(originalText, publicKey);
		if (encryptedData != null && encryptedData.length > 0) {
			System.out.println("   加密成功！");
			System.out.println("   加密后的密文 (16进制): " + Hex.toHexString(encryptedData));
			System.out.println("   密文长度: " + encryptedData.length + " 字节");
		} else {
			System.out.println("   加密失败！");
			return;
		}

		System.out.println("--------------------------------------------------");

		// 5. 执行解密
		System.out.println("5. 正在执行解密操作...");
		String decryptedText = SM2Util.decrypt(encryptedData, privateKey);
		if (StringUtil.isNotBlank(decryptedText)) {
			System.out.println("   解密成功！");
			System.out.println("   解密后的明文: " + decryptedText);
		} else {
			System.out.println("   解密失败！");
			return;
		}

		System.out.println("--------------------------------------------------");

		// 6. 执行数字签名
		System.out.println("6. 正在执行数字签名...");
		byte[] signature = SM2Util.sign(originalText, privateKey);
		if (signature != null && signature.length > 0) {
			System.out.println("   签名成功！");
			System.out.println("   数字签名 (16进制): " + Hex.toHexString(signature));
			System.out.println("   签名长度: " + signature.length + " 字节");
		} else {
			System.out.println("   签名失败！");
			return;
		}

		System.out.println("--------------------------------------------------");

		// 7. 验证签名
		System.out.println("7. 正在验证数字签名...");
		boolean isVerified = SM2Util.verify(originalText, signature, publicKey);
		System.out.println("   签名验证结果: " + (isVerified ? "有效" : "无效"));

		System.out.println("--------------------------------------------------");

		// 8. 校验加解密结果
		System.out.println("8. 正在校验原文与解密后的明文是否一致...");
		boolean isDecryptMatch = originalText.equals(decryptedText);
		System.out.println("   加解密校验结果: " + (isDecryptMatch ? "一致" : "不一致"));

		System.out.println("==================================================");

		// 9. 最终结果汇总
		if (isDecryptMatch && isVerified) {
			System.out.println("\n[成功] SM2加解密与签名流程验证通过！");
			System.out.println("  ✓ 密钥对生成成功");
			System.out.println("  ✓ 加解密功能正常");
			System.out.println("  ✓ 数字签名验证通过");
		} else {
			System.out.println("\n[失败] SM2加解密与签名流程验证失败！");
			if (!isDecryptMatch) {
				System.out.println("  ✗ 加解密校验失败");
			}
			if (!isVerified) {
				System.out.println("  ✗ 数字签名验证失败");
			}
		}
	}

}
