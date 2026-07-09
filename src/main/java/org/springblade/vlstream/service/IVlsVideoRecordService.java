package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.vlstream.excel.VlsVideoRecordExcel;
import org.springblade.vlstream.pojo.entity.VideoRecord;
import org.springblade.vlstream.pojo.vo.VideoRecordVO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Video recording record sheet Service category
 *
 * @author Oort
 * @since 2025-12-25
 */
public interface IVlsVideoRecordService extends BaseService<VideoRecord> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsVideoRecord query parameters
	 * @return IPage<VlsVideoRecordVO>
	 */
	IPage<VideoRecordVO> selectVlsVideoRecordPage(IPage<VideoRecordVO> page, VideoRecordVO vlsVideoRecord);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsVideoRecordExcel>
	 */
	List<VlsVideoRecordExcel> exportVlsVideoRecord(Wrapper<VideoRecord> queryWrapper);

	/**
	 * Query playback records by time range
	 *
	 * @param deviceId equipmentID
	 * @param startTime start time
	 * @param endTime end time
	 * @return Playback record list
	 */
	List<VideoRecord> listPlaybackRecords(Long deviceId, LocalDateTime startTime, LocalDateTime endTime);

	/**
	 * Query the recording records of the device on a certain day
	 *
	 * @param deviceId equipmentID
	 * @param recordDate date
	 * @return Recording list
	 */
	List<VideoRecord> listDayRecords(Long deviceId, LocalDate recordDate);

	/**
	 * Query the list of dates on which the device has recording records in a specified year
	 *
	 * @param deviceId equipmentID
	 * @param year years(No limit when empty)
	 * @return date list
	 */
	List<LocalDate> listRecordDates(Long deviceId, Integer year);

}
