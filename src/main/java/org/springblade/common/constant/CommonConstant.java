package org.springblade.common.constant;

import org.springblade.core.launch.constant.AppConstant;

/**
 * Universal constant
 *
 * @author Chill
 */
public interface CommonConstant {

	/**
	 * app name
	 */
	String APPLICATION_NAME = AppConstant.APPLICATION_NAME_PREFIX + "api";

	/**
	 * sword System name
	 */
	String SWORD_NAME = "sword";

	/**
	 * saber System name
	 */
	String SABER_NAME = "saber";

	/**
	 * top parent nodeid
	 */
	Long TOP_PARENT_ID = 0L;

	/**
	 * Top-level parent node name
	 */
	String TOP_PARENT_NAME = "Top";

	/**
	 * Unarchived status value
	 */
	Integer NOT_SEALED_ID = 0;

	/**
	 * default password
	 */
	String DEFAULT_PASSWORD = "123456";

	/**
	 * Default password parameter value
	 */
	String DEFAULT_PARAM_PASSWORD = "account.initPassword";

	/**
	 * Default sort field
	 */
	String SORT_FIELD = "sort";

	/**
	 * Data permission type
	 */
	Integer DATA_SCOPE_CATEGORY = 1;

	/**
	 * Interface permission type
	 */
	Integer API_SCOPE_CATEGORY = 2;

	/**
	 * Dataset path
	 */
    String BASE_DATASETS_PATH = "/data/work/ultralytics_yolov8-main/datasets/";

	/**
	 * yoloworking path
	 */
	String BASE_YOLO_PATH = "/data/work/ultralytics_yolov8-main/";

	/**
	 * synsetdocument
	 */
	String SYNSET_TXT = "synset.txt";
}
