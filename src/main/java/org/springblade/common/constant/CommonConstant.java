package org.springblade.common.constant;

import org.springblade.core.launch.constant.AppConstant;

/**
 * 通用常量
 *
 * @author Chill
 */
public interface CommonConstant {

	/**
	 * app name
	 */
	String APPLICATION_NAME = AppConstant.APPLICATION_NAME_PREFIX + "api";

	/**
	 * sword 系统名
	 */
	String SWORD_NAME = "sword";

	/**
	 * saber 系统名
	 */
	String SABER_NAME = "saber";

	/**
	 * 顶级父节点id
	 */
	Long TOP_PARENT_ID = 0L;

	/**
	 * 顶级父节点名称
	 */
	String TOP_PARENT_NAME = "顶级";

	/**
	 * 未封存状态值
	 */
	Integer NOT_SEALED_ID = 0;

	/**
	 * 默认密码
	 */
	String DEFAULT_PASSWORD = "123456";

	/**
	 * 默认密码参数值
	 */
	String DEFAULT_PARAM_PASSWORD = "account.initPassword";

	/**
	 * 默认排序字段
	 */
	String SORT_FIELD = "sort";

	/**
	 * 数据权限类型
	 */
	Integer DATA_SCOPE_CATEGORY = 1;

	/**
	 * 接口权限类型
	 */
	Integer API_SCOPE_CATEGORY = 2;

	/**
	 * 数据集路径
	 */
    String BASE_DATASETS_PATH = "/data/work/ultralytics_yolov8-main/datasets/";

	/**
	 * yolo工作路径
	 */
	String BASE_YOLO_PATH = "/data/work/ultralytics_yolov8-main/";

	/**
	 * synset文件
	 */
	String SYNSET_TXT = "synset.txt";
}
