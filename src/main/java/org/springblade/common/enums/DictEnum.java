package org.springblade.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * System dictionary enumeration class
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum DictEnum {

	/**
	 * gender
	 */
	SEX("sex"),
	/**
	 * notification type
	 */
	NOTICE("notice"),
	/**
	 * Menu type
	 */
	MENU_CATEGORY("menu_category"),
	/**
	 * Button function
	 */
	BUTTON_FUNC("button_func"),
	/**
	 * whether
	 */
	YES_NO("yes_no"),
	/**
	 * Process type
	 */
	FLOW("flow"),
	/**
	 * Institution type
	 */
	ORG_CATEGORY("org_category"),
	/**
	 * Data permissions
	 */
	DATA_SCOPE_TYPE("data_scope_type"),
	/**
	 * Interface permissions
	 */
	API_SCOPE_TYPE("api_scope_type"),
	/**
	 * Permission type
	 */
	SCOPE_CATEGORY("scope_category"),
	/**
	 * Object storage type
	 */
	OSS("oss"),
	/**
	 * SMS service type
	 */
	SMS("sms"),
	/**
	 * Position type
	 */
	POST_CATEGORY("post_category"),
	/**
	 * Administrative division
	 */
	REGION("region"),
	/**
	 * User platform
	 */
	USER_TYPE("user_type"),
	;

	final String name;

}
