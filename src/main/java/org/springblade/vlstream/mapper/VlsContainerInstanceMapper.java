package org.springblade.vlstream.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springblade.vlstream.excel.VlsContainerInstanceExcel;
import org.springblade.vlstream.pojo.dto.ContainerInstanceQueryDTO;
import org.springblade.vlstream.pojo.entity.ContainerInstance;
import org.springblade.vlstream.pojo.vo.ContainerInstanceVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Container instance table Mapper interface
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsContainerInstanceMapper extends BaseMapper<ContainerInstance> {

	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsContainerInstance query parameters
	 * @return List<VlsContainerInstanceVO>
	 */
	List<ContainerInstanceVO> selectVlsContainerInstancePage(IPage page, ContainerInstanceVO vlsContainerInstance);

	/**
	 * Get export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsContainerInstanceExcel>
	 */
	List<VlsContainerInstanceExcel> exportVlsContainerInstance(@Param("ew") Wrapper<ContainerInstance> queryWrapper);

	/**
	 * Paging query container instance(Contains associated information)
	 *
	 * @param page Paging parameters
	 * @param queryDTO Query conditions
	 * @return Paginated results
	 */
	IPage<ContainerInstance> selectPageWithDetails(IPage<ContainerInstance> page, @Param("query") ContainerInstanceQueryDTO queryDTO);

	/**
	 * according toIDQuery container instance details(Contains associated information)
	 *
	 * @param id Container instanceID
	 * @return Container instance details
	 */
	ContainerInstance selectByIdWithDetails(@Param("id") Long id);

	/**
	 * According to the containerIDQuery container instance
	 *
	 * @param containerId containerID
	 * @return Container instance
	 */
	@Select("SELECT * FROM vls_container_instance WHERE container_id = #{containerId} AND is_deleted = 0")
	ContainerInstance selectByContainerId(@Param("containerId") String containerId);

	/**
	 * Query container instances based on instance name(Used for duplicate name checking)
	 *
	 * @param instanceName Instance name
	 * @param excludeId excludedID(Used when updating)
	 * @return Container instance
	 */
	@Select("<script>" +
		"SELECT * FROM vls_container_instance WHERE instance_name = #{instanceName} AND is_deleted = 0" +
		"<if test='excludeId != null'> AND id != #{excludeId}</if>" +
		"</script>")
	ContainerInstance selectByInstanceName(@Param("instanceName") String instanceName, @Param("excludeId") Long excludeId);

	/**
	 * According to algorithmIDQuery container instance list
	 *
	 * @param algorithmId algorithmID
	 * @return Container instance list
	 */
	@Select("SELECT * FROM vls_container_instance WHERE algorithm_id = #{algorithmId} AND is_deleted = 0 ORDER BY create_time DESC")
	List<ContainerInstance> selectByAlgorithmId(@Param("algorithmId") Long algorithmId);

	/**
	 * Query the list of container instances based on status
	 *
	 * @param instanceStatus Instance status
	 * @return Container instance list
	 */
	@Select("SELECT * FROM vls_container_instance WHERE instance_status = #{instanceStatus} AND is_deleted = 0")
	List<ContainerInstance> selectByStatus(@Param("instanceStatus") String instanceStatus);

	/**
	 * Update container instance status
	 *
	 * @param id Container instanceID
	 * @param instanceStatus Instance status
	 * @param healthStatus health status
	 * @param containerId containerID
	 * @param startTime Start time
	 * @param stopTime stop time
	 * @return Update row count
	 */
	@Update("UPDATE vls_container_instance SET instance_status = #{instanceStatus}, " +
		"health_status = #{healthStatus}, container_id = #{containerId}, " +
		"start_time = #{startTime}, stop_time = #{stopTime}, update_time = NOW() " +
		"WHERE id = #{id}")
	int updateInstanceStatus(@Param("id") Long id,
							 @Param("instanceStatus") String instanceStatus,
							 @Param("healthStatus") String healthStatus,
							 @Param("containerId") String containerId,
							 @Param("startTime") Date startTime,
							 @Param("stopTime") Date stopTime);

	/**
	 * Update container monitoring data
	 *
	 * @param id Container instanceID
	 * @param cpuUsage CPUUsage rate
	 * @param memoryUsage memory usage
	 * @param gpuUsage GPUUsage rate
	 * @return Update row count
	 */
	@Update("UPDATE vls_container_instance SET cpu_usage = #{cpuUsage}, " +
		"memory_usage = #{memoryUsage}, gpu_usage = #{gpuUsage}, update_time = NOW() " +
		"WHERE id = #{id}")
	int updateMonitoringData(@Param("id") Long id,
							 @Param("cpuUsage") java.math.BigDecimal cpuUsage,
							 @Param("memoryUsage") java.math.BigDecimal memoryUsage,
							 @Param("gpuUsage") java.math.BigDecimal gpuUsage);

	/**
	 * Increase the number of restarts
	 *
	 * @param id Container instanceID
	 * @return Update row count
	 */
	@Update("UPDATE vls_container_instance SET restart_count = restart_count + 1, update_time = NOW() WHERE id = #{id}")
	int increaseRestartCount(@Param("id") Long id);

	/**
	 * Get container instance statistics
	 *
	 * @return Statistical results
	 */
	Map<String, Object> selectStatistics();

	/**
	 * Delete container instances in batches
	 *
	 * @param ids Container instanceIDlist
	 * @return Number of rows to delete
	 */
	@Update("<script>" +
		"UPDATE vls_container_instance SET deleted = 1, update_time = NOW() WHERE id IN " +
		"<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
		"#{id}" +
		"</foreach>" +
		"</script>")
	int deleteBatch(@Param("ids") List<Long> ids);

}
