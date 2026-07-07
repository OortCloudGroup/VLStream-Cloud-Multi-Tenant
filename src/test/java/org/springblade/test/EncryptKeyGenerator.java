package org.springblade.test;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.springblade.core.tool.utils.AesUtil;
import org.springblade.core.tool.utils.DesUtil;
import org.springblade.core.tool.utils.SM2Util;
import org.springblade.core.tool.utils.SM4Util;

/**
 * 加密密钥生成器
 *
 * @author BladeX
 */
public class EncryptKeyGenerator {

	public static void main(String[] args) {
		// 生成对称加密密钥
		String aesKey = AesUtil.genAesKey();
		String desKey = DesUtil.genDesKey();
		String sm4Key = SM4Util.generateKey();

		// 生成SM2非对称加密密钥对
		AsymmetricCipherKeyPair sm2KeyPair = SM2Util.generateKeyPair();
		String sm2PublicKey = SM2Util.getPublicKeyString(sm2KeyPair);
		String sm2PrivateKey = SM2Util.getPrivateKeyString(sm2KeyPair);

		System.out.println("\n================== Encryption Keys Generated ==================\n");

		System.out.println("[Symmetric Keys / 对称加密密钥]");
		System.out.println();
		System.out.println("AES-256-Key: " + aesKey);
		System.out.println("DES-Key: " + desKey);
		System.out.println("SM4-Key(国密): " + sm4Key);

		System.out.println("\n----------------------------------------------------------------\n");

		System.out.println("[Asymmetric Keys / 非对称加密密钥]");
		System.out.println();
		System.out.println("SM2-PublicKey(国密公钥): " + sm2PublicKey);
		System.out.println("SM2-PrivateKey(国密私钥): " + sm2PrivateKey);

		System.out.println("\n================================================================\n");
	}

}
