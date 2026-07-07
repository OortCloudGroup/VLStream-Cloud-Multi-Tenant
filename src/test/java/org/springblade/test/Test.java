package org.springblade.test;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.json.JSONUtil;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Chill
 */
public class Test {

	public static void main(String[] args) throws SQLException {
		List<Entity> result = Db.use().query("show TABLES");
		System.out.println(JSONUtil.toJsonStr(result));

		for (Entity entity : result) {
			String tableName = entity.getStr("tables_in_taitans");

			String updateSql = StrUtil.format("ALTER TABLE `taitans`.`{}` \n" +
				"ADD COLUMN `tenant_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',\n" +
				"ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人' AFTER `tenant_id`,\n" +
				"ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门' AFTER `create_user`,\n" +
				"ADD COLUMN `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间' AFTER `create_dept`,\n" +
				"ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人' AFTER `create_time`,\n" +
				"ADD COLUMN `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间' AFTER `update_user`,\n" +
				"ADD COLUMN `status` int NULL DEFAULT 1 COMMENT '状态' AFTER `update_time`,\n" +
				"ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否已删除' AFTER `status`;", tableName);
			System.out.println(updateSql);

		}
	}

}
