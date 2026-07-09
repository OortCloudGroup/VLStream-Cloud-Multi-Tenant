package org.springblade.vlstream.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springblade.vlstream.excel.VlsAlgorithmTrainingExcel;
import org.springblade.vlstream.pojo.dto.AlgorithmTrainingStatisticsDTO;
import org.springblade.vlstream.pojo.entity.AlgorithmTraining;
import org.springblade.vlstream.pojo.vo.AlgorithmTrainingVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Algorithm training task list Mapper interface
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsAlgorithmTrainingMapper extends BaseMapper<AlgorithmTraining> {

	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAlgorithmTraining query parameters
	 * @return List<VlsAlgorithmTrainingVO>
	 */
	List<AlgorithmTrainingVO> selectVlsAlgorithmTrainingPage(IPage page, AlgorithmTrainingVO vlsAlgorithmTraining);

	/**
	 * Get export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAlgorithmTrainingExcel>
	 */
	List<VlsAlgorithmTrainingExcel> exportVlsAlgorithmTraining(@Param("ew") Wrapper<AlgorithmTraining> queryWrapper);

	/**
	 * Paginated query training tasks(Contains associated information)
	 *
	 * @param page Pagination object
	 * @param taskName Task name
	 * @param algorithmId algorithmID
	 * @param datasetId DatasetID
	 * @param trainType training type
	 * @param trainStatus training status
	 * @param createdBy Creator
	 * @param startTimeBegin start time range start
	 * @param startTimeEnd Start time range ends
	 * @param createdTimeBegin Creation time range starts
	 * @param createdTimeEnd End of creation time range
	 * @param orderBy sort field
	 * @param order sort by
	 * @return Paginated results
	 */
	IPage<AlgorithmTraining> selectPageWithDetails(
		Page<AlgorithmTraining> page,
		@Param("taskName") String taskName,
		@Param("algorithmId") Long algorithmId,
		@Param("datasetId") Long datasetId,
		@Param("trainType") String trainType,
		@Param("trainStatus") String trainStatus,
		@Param("createdBy") Long createdBy,
		@Param("startTimeBegin") LocalDateTime startTimeBegin,
		@Param("startTimeEnd") LocalDateTime startTimeEnd,
		@Param("createdTimeBegin") LocalDateTime createdTimeBegin,
		@Param("createdTimeEnd") LocalDateTime createdTimeEnd,
		@Param("orderBy") String orderBy,
		@Param("order") String order
	);

	/**
	 * according toIDQuery training task details(Contains associated information)
	 *
	 * @param id training tasksID
	 * @return Training task details
	 */
	AlgorithmTraining selectByIdWithDetails(@Param("id") Long id);

	/**
	 * Get training task statistics
	 *
	 * @return Statistics
	 */
	AlgorithmTrainingStatisticsDTO selectStatistics();

	/**
	 * According to algorithmIDQuery training task list
	 *
	 * @param algorithmId algorithmID
	 * @return Training task list
	 */
	List<AlgorithmTraining> selectByAlgorithmId(@Param("algorithmId") Long algorithmId);

	/**
	 * According to the data setIDQuery training task list
	 *
	 * @param datasetId DatasetID
	 * @return Training task list
	 */
	List<AlgorithmTraining> selectByDatasetId(@Param("datasetId") Long datasetId);

	/**
	 * Get the list of tasks being trained
	 *
	 * @return Task list being trained
	 */
	@Select("SELECT * FROM vls_algorithm_training WHERE train_status = 'training' AND is_deleted = 0")
	List<AlgorithmTraining> selectTrainingTasks();

	/**
	 * Get the list of waiting tasks
	 *
	 * @return Waiting list of tasks
	 */
	@Select("SELECT * FROM vls_algorithm_training WHERE train_status = 'pending' AND is_deleted = 0 ORDER BY create_time ASC")
	List<AlgorithmTraining> selectPendingTasks();

	/**
	 * Update training task status
	 *
	 * @param id training tasksID
	 * @param trainStatus training status
	 * @param startTime start time
	 * @param endTime end time
	 * @param errorMessage error message
	 * @return Update row count
	 */
	@Update("UPDATE vls_algorithm_training SET train_status = #{trainStatus}, " +
		"start_time = #{startTime}, end_time = #{endTime}, error_message = #{errorMessage}, " +
		"update_time = NOW() WHERE id = #{id}")
	int updateTrainStatus(@Param("id") Long id,
						  @Param("trainStatus") String trainStatus,
						  @Param("startTime") LocalDateTime startTime,
						  @Param("endTime") LocalDateTime endTime,
						  @Param("errorMessage") String errorMessage);

	/**
	 * Update training progress
	 *
	 * @param id training tasksID
	 * @param progress progress percentage
	 * @param epochCurrent current round
	 * @return Update row count
	 */
	@Update("UPDATE vls_algorithm_training SET progress = #{progress}, " +
		"epoch_current = #{epochCurrent}, update_time = NOW() WHERE id = #{id}")
	int updateProgress(@Param("id") Long id,
					   @Param("progress") Integer progress,
					   @Param("epochCurrent") Integer epochCurrent);

	/**
	 * Delete training tasks in batches
	 *
	 * @param ids TaskIDlist
	 * @return Number of rows to delete
	 */
	@Update("<script>" +
		"UPDATE vls_algorithm_training SET deleted = 1, update_time = NOW() WHERE id IN " +
		"<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
		"#{id}" +
		"</foreach>" +
		"</script>")
	int deleteBatch(@Param("ids") List<Long> ids);

	/**
	 * Query algorithm training tasks
	 *
	 * @param id Algorithm training task primary key
	 * @return Algorithm training tasks
	 */
	AlgorithmTraining selectAlgorithmTrainingById(@Param("id") Long id);

	/**
	 * Query algorithm training task list
	 *
	 * @param algorithmTraining Algorithm training tasks
	 * @return Algorithm training task set
	 */
	List<AlgorithmTraining> selectAlgorithmTrainingList(AlgorithmTraining algorithmTraining);

	/**
	 * Added algorithm training tasks
	 *
	 * @param algorithmTraining Algorithm training tasks
	 * @return result
	 */
	int insertAlgorithmTraining(AlgorithmTraining algorithmTraining);

	/**
	 * Modify algorithm training tasks
	 *
	 * @param algorithmTraining Algorithm training tasks
	 * @return result
	 */
	int updateAlgorithmTraining(AlgorithmTraining algorithmTraining);

	/**
	 * Delete algorithm training tasks
	 *
	 * @param id Algorithm training task primary key
	 * @return result
	 */
	int deleteAlgorithmTrainingById(@Param("id") Long id);

	/**
	 * Batch deletion of algorithm training tasks
	 *
	 * @param ids Algorithm training task primary key set that needs to be deleted
	 * @return result
	 */
	int deleteAlgorithmTrainingByIds(@Param("ids") Long[] ids);

}
