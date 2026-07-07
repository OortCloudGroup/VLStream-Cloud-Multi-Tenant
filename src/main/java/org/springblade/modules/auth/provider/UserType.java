package org.springblade.modules.auth.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户类型枚举
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum UserType {

	/**
	 * web
	 */
	WEB("web", 1),

	/**
	 * app
	 */
	APP("app", 2),

	/**
	 * other
	 */
	OTHER("other", 3),
	;

	final String name;
	final int category;

	public static UserType of(String name) {
		return Arrays.stream(UserType.values())
			.filter(userEnum -> userEnum.getName().equalsIgnoreCase(name != null ? name : "web"))
			.findFirst()
			.orElse(UserType.WEB);
	}
}
