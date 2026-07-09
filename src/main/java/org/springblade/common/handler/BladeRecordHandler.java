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
 * Log data audit processor
 * <p>
 * Output data audit to log
 *
 * @author Oort
 */
//If the dynamic data source function is enabled, then add@MasterAnnotate the specified permission database as the main database
//@Master
@Slf4j
@RequiredArgsConstructor
public class BladeRecordHandler implements DataRecordHandler {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public void handle(DataRecordInfo recordInfo, DataRecord dataRecord) {
		// Get formatted record information
		String recordMessage = formatRecordInfo(recordInfo);
		// Get data audit level
		DataRecordLevel level = dataRecord.level();

		// Data audit and warehousing processing
		int affectedRows = saveRecordData(recordInfo, level.name(), recordMessage);
		if (affectedRows > 0) {
			// Output according to the configured log level
			switch (level) {
				case DEBUG:
					log.debug("Data audit[{}]: {}", recordInfo.getModule(), recordMessage);
					break;
				case INFO:
					log.info("Data audit[{}]: {}", recordInfo.getModule(), recordMessage);
					break;
				case WARN:
					log.warn("Data audit[{}]: {}", recordInfo.getModule(), recordMessage);
					break;
				case ERROR:
					log.error("Data audit[{}]: {}", recordInfo.getModule(), recordMessage);
					break;
			}
		} else {
			// If storage fails, Record error log
			log.error("Data audit[{}] Failed to save into database", recordInfo.getTableName());
		}

	}

	/**
	 * Format record information
	 *
	 * @param recordInfo Record information
	 * @return Formatted string
	 */
	private String formatRecordInfo(DataRecordInfo recordInfo) {
		StringBuilder sb = new StringBuilder();
		// Add module information
		sb.append("\n").append("[table name]: ").append(recordInfo.getTableName()).append("\n");
		sb.append("[operate]: ").append(recordInfo.getOperation()).append("\n");
		if (recordInfo.getPrimaryKeyValue() != null) {
			sb.append("[primary key]: ").append(recordInfo.getPrimaryKeyValue()).append("\n");
		}
		// Add user information
		if (recordInfo.getUserName() != null) {
			sb.append("[user]: ").append(recordInfo.getUserName()).append("\n");
		}
		// Add toIPinformation
		if (recordInfo.getRemoteIp() != null) {
			sb.append("[IP]: ").append(recordInfo.getRemoteIp()).append("\n");
		}
		// Add requestURI
		if (recordInfo.getRequestUri() != null) {
			sb.append("[URI]: ").append(recordInfo.getRequestUri()).append("\n");
		}
		if (recordInfo.getChangedFields() != null && !recordInfo.getChangedFields().isEmpty()) {
			sb.append("[change]: ").append(recordInfo.getChangedFields()).append("\n");
		}
		if (recordInfo.getCost() != null) {
			sb.append("[time consuming]: ").append(recordInfo.getCost()).append("ms").append("\n");
		}
		// If you need details, Add change details
		if (recordInfo.getChangeData() != null && !recordInfo.getChangeData().isEmpty()) {
			sb.append("[Details]: {");
			recordInfo.getChangeData().forEach((field, change) -> sb.append(change.getChangeDescription()).append(", "));
			if (sb.toString().endsWith(", ")) {
				sb.setLength(sb.length() - 2);
			}
			sb.append("}");
		}
		return sb.append("\n").toString();
	}

	/**
	 * use JdbcTemplate Keep data audit records
	 *
	 * @param recordInfo    Record information
	 * @param recordLevel   Logging level
	 * @param recordMessage Log message
	 * @return number of rows affected
	 */
	private int saveRecordData(DataRecordInfo recordInfo, String recordLevel, String recordMessage) {
		return jdbcTemplate.update(RECORD_INSERT_SQL,
			// primary key
			recordInfo.getRecordId(),
			// Basic service information
			recordInfo.getServiceId(),
			recordInfo.getServerHost(),
			recordInfo.getServerIp(),
			recordInfo.getEnv(),
			// Audit level
			recordLevel,
			// request information
			recordInfo.getMethod(),
			recordInfo.getRequestUri(),
			recordInfo.getUserAgent(),
			recordInfo.getRemoteIp(),
			// Operation information
			recordInfo.getOperation(),
			recordInfo.getTableName(),
			// data conversion: Will Map Convert to JSON string
			JsonUtil.toJson(recordInfo.getOldData()),
			JsonUtil.toJson(recordInfo.getNewData()),
			// audit message
			recordMessage,
			// Audit results
			recordInfo.getRecordResult(),
			// Recording time
			Func.toStr(recordInfo.getCost()),
			// Record time
			recordInfo.getRecordTime(),
			// Recorder
			Func.toLong(recordInfo.getUserId()),
			// business status: Default is normal state
			BladeConstant.DB_STATUS_NORMAL,
			// Tombstone status: The default is not deleted
			BladeConstant.DB_NOT_DELETED
		);
	}
}
