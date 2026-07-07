package org.springblade.common.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.datarecord.annotation.DataRecord;
import org.springblade.core.datarecord.annotation.DataRecordLevel;
import org.springblade.core.datarecord.model.DataRecordInfo;
import org.springblade.core.datarecord.processor.DataRecordHandler;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.springblade.core.datarecord.constant.DataRecordConstant.RECORD_INSERT_SQL;

/**
 * 日志数据审计处理器
 * <p>
 * 将数据审计输出到日志
 *
 * @author Oort
 */
//若开启动态数据源功能，则加上@Master注解指定权限数据库为主库
//@Master
@Slf4j
@RequiredArgsConstructor
public class BladeRecordHandler implements DataRecordHandler {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void handle(DataRecordInfo recordInfo, DataRecord dataRecord) {
		// 获取格式化的记录信息
		String recordMessage = formatRecordInfo(recordInfo);
		// 获取数据审计级别
		DataRecordLevel level = dataRecord.level();

		// 数据审计入库处理
		int affectedRows = saveRecordData(recordInfo, level.name(), recordMessage);
		if (affectedRows > 0) {
			// 根据配置的日志级别输出
			switch (level) {
				case DEBUG:
					log.debug("数据审计[{}]: {}", recordInfo.getModule(), recordMessage);
					break;
				case INFO:
					log.info("数据审计[{}]: {}", recordInfo.getModule(), recordMessage);
					break;
				case WARN:
					log.warn("数据审计[{}]: {}", recordInfo.getModule(), recordMessage);
					break;
				case ERROR:
					log.error("数据审计[{}]: {}", recordInfo.getModule(), recordMessage);
					break;
			}
		} else {
			// 如果入库失败，记录错误日志
			log.error("数据审计[{}] 保存入库失败", recordInfo.getTableName());
		}

	}

	/**
	 * 格式化记录信息
	 *
	 * @param recordInfo 记录信息
	 * @return 格式化后的字符串
	 */
	private String formatRecordInfo(DataRecordInfo recordInfo) {
		StringBuilder sb = new StringBuilder();
		// 添加模块信息
		sb.append("\n").append("[表名]: ").append(recordInfo.getTableName()).append("\n");
		sb.append("[操作]: ").append(recordInfo.getOperation()).append("\n");
		if (recordInfo.getPrimaryKeyValue() != null) {
			sb.append("[主键]: ").append(recordInfo.getPrimaryKeyValue()).append("\n");
		}
		// 添加用户信息
		if (recordInfo.getUserName() != null) {
			sb.append("[用户]: ").append(recordInfo.getUserName()).append("\n");
		}
		// 添加IP信息
		if (recordInfo.getRemoteIp() != null) {
			sb.append("[IP]: ").append(recordInfo.getRemoteIp()).append("\n");
		}
		// 添加请求URI
		if (recordInfo.getRequestUri() != null) {
			sb.append("[URI]: ").append(recordInfo.getRequestUri()).append("\n");
		}
		if (recordInfo.getChangedFields() != null && !recordInfo.getChangedFields().isEmpty()) {
			sb.append("[变更]: ").append(recordInfo.getChangedFields()).append("\n");
		}
		if (recordInfo.getCost() != null) {
			sb.append("[耗时]: ").append(recordInfo.getCost()).append("ms").append("\n");
		}
		// 如果需要详细信息，添加变更详情
		if (recordInfo.getChangeData() != null && !recordInfo.getChangeData().isEmpty()) {
			sb.append("[详情]: {");
			recordInfo.getChangeData().forEach((field, change) -> sb.append(change.getChangeDescription()).append(", "));
			if (sb.toString().endsWith(", ")) {
				sb.setLength(sb.length() - 2);
			}
			sb.append("}");
		}
		return sb.append("\n").toString();
	}

	/**
	 * 使用 JdbcTemplate 保存数据审计记录
	 *
	 * @param recordInfo    记录信息
	 * @param recordLevel   记录级别
	 * @param recordMessage 记录消息
	 * @return 影响的行数
	 */
	private int saveRecordData(DataRecordInfo recordInfo, String recordLevel, String recordMessage) {
		return jdbcTemplate.update(RECORD_INSERT_SQL,
			// 主键
			recordInfo.getRecordId(),
			// 基础服务信息
			recordInfo.getServiceId(),
			recordInfo.getServerHost(),
			recordInfo.getServerIp(),
			recordInfo.getEnv(),
			// 审计级别
			recordLevel,
			// 请求信息
			recordInfo.getMethod(),
			recordInfo.getRequestUri(),
			recordInfo.getUserAgent(),
			recordInfo.getRemoteIp(),
			// 操作信息
			recordInfo.getOperation(),
			recordInfo.getTableName(),
			// 数据转换：将 Map 转换为 JSON 字符串
			JsonUtil.toJson(recordInfo.getOldData()),
			JsonUtil.toJson(recordInfo.getNewData()),
			// 审计消息
			recordMessage,
			// 审计结果
			recordInfo.getRecordResult(),
			// 记录耗时
			Func.toStr(recordInfo.getCost()),
			// 记录时间
			recordInfo.getRecordTime(),
			// 记录人
			Func.toLong(recordInfo.getUserId()),
			// 业务状态：默认为正常状态
			BladeConstant.DB_STATUS_NORMAL,
			// 逻辑删除状态：默认为未删除状态
			BladeConstant.DB_NOT_DELETED
		);
	}
}
