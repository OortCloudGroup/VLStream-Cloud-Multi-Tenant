package org.springblade.test;

import org.springblade.core.tool.utils.AesUtil;
import org.springblade.core.tool.utils.DesUtil;
import org.springblade.core.tool.utils.StringPool;

/**
 * Key生成器
 *
 * @author Chill
 */
public class CryptoKeyGenerator {

	public static void main(String[] args) {
		String cryptoKey = AesUtil.genAesKey();
		String aesKey = AesUtil.genAesKey();
		String desKey = DesUtil.genDesKey();

		System.out.println("=========== blade.token.crypto-key 配置如下 ============");
		System.out.println("#blade配置\n" +
			"blade:\n" +
			"  token:\n" +
			"    crypto-key: " + cryptoKey);
		System.out.println("=======================================================");

		System.out.println(StringPool.EMPTY);
		System.out.println("============== blade.api.crypto 配置如下 ===============");
		System.out.println("#blade配置\n" +
			"blade:\n" +
			"  api:\n" +
			"    crypto:\n" +
			"      enabled: true\n" +
			"      aes-key: " + aesKey + "\n" +
			"      des-key: " + desKey);
		System.out.println("=======================================================");

		System.out.println(StringPool.EMPTY);
		System.out.println("============== saber crypto.js 配置如下 ===============");
		System.out.println("export default class crypto {\n" +
			"  static cryptoKey = '" + cryptoKey + "';\n" +
			"  static aesKey = '" + aesKey + "';\n" +
			"  static desKey = '" + desKey + "';\n" +
			"}");
		System.out.println("=======================================================");
	}

}
