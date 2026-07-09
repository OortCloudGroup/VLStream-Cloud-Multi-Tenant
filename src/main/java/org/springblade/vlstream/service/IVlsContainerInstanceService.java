package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.core.mp.base.BaseService;
import org.springblade.vlstream.excel.VlsContainerInstanceExcel;
import org.springblade.vlstream.pojo.dto.ContainerInstanceCreateDTO;
import org.springblade.vlstream.pojo.dto.ContainerInstanceQueryDTO;
import org.springblade.vlstream.pojo.dto.ContainerInstanceUpdateDTO;
import org.springblade.vlstream.pojo.entity.ContainerInstance;
import org.springblade.vlstream.pojo.vo.ContainerInstanceVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Container instance table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsContainerInstanceService extends BaseService<ContainerInstance> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsContainerInstance query parameters
	 * @return IPage<VlsContainerInstanceVO>
	 */
	IPage<ContainerInstanceVO> selectVlsContainerInstancePage(IPage<ContainerInstanceVO> page, ContainerInstanceVO vlsContainerInstance);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsContainerInstanceExcel>
	 */
	List<VlsContainerInstanceExcel> exportVlsContainerInstance(Wrapper<ContainerInstance> queryWrapper);

	/**
	 * Paging query container instance
	 *
	 * @param page Paging parameters
	 * @param queryDTO Query conditions
	 * @return Paginated results
	 */
	IPage<ContainerInstance> pageContainerInstances(Page<ContainerInstance> page, ContainerInstanceQueryDTO queryDTO);

	/**
	 * according toIDQuery container instance details
	 *
	 * @param id Container instanceID
	 * @return Container instance details
	 */
	ContainerInstance getContainerInstanceById(Long id);

	/**
	 * Create container instance
	 *
	 * @param createDTO Create parameters
	 * @return Container instance created
	 */
	ContainerInstance createContainerInstance(ContainerInstanceCreateDTO createDTO);

	/**
	 * Update container instance
	 *
	 * @param updateDTO Update parameters
	 * @return Updated container instance
	 */
	ContainerInstance updateContainerInstance(ContainerInstanceUpdateDTO updateDTO);

	/**
	 * Delete container instance
	 *
	 * @param id Container instanceID
	 * @return Is deletion successful?
	 */
	boolean deleteContainerInstance(Long id);

	/**
	 * Delete container instances in batches
	 *
	 * @param ids Container instanceIDlist
	 * @return Is deletion successful?
	 */
	boolean deleteContainerInstanceBatch(List<Long> ids);

	/**
	 * According to the containerIDQuery container instance
	 *
	 * @param containerId containerID
	 * @return Container instance
	 */
	ContainerInstance getByContainerId(String containerId);

	/**
	 * According to algorithmIDQuery container instance list
	 *
	 * @param algorithmId algorithmID
	 * @return Container instance list
	 */
	List<ContainerInstance> getByAlgorithmId(Long algorithmId);

	/**
	 * Query the list of container instances based on status
	 *
	 * @param instanceStatus Instance status
	 * @return Container instance list
	 */
	List<ContainerInstance> getByStatus(String instanceStatus);

	/**
	 * Start container instance
	 *
	 * @param id Container instanceID
	 * @param containerId containerID
	 * @return Whether the startup is successful
	 */
	boolean startContainer(Long id, String containerId);

	/**
	 * Stop a container instance
	 *
	 * @param id Container instanceID
	 * @return Whether the stop was successful
	 */
	boolean stopContainer(Long id);

	/**
	 * Restart container instance
	 *
	 * @param id Container instanceID
	 * @return Whether the restart is successful
	 */
	boolean restartContainer(Long id);

	/**
	 * Update container instance status
	 *
	 * @param id Container instanceID
	 * @param instanceStatus Instance status
	 * @param healthStatus health status
	 * @param containerId containerID
	 * @param startTime Start time
	 * @param stopTime stop time
	 * @return Is the update successful?
	 */
	boolean updateInstanceStatus(Long id, String instanceStatus, String healthStatus,
								 String containerId, Date startTime, Date stopTime);

	/**
	 * Update container monitoring data
	 *
	 * @param id Container instanceID
	 * @param cpuUsage CPUUsage rate
	 * @param memoryUsage memory usage
	 * @param gpuUsage GPUUsage rate
	 * @return Is the update successful?
	 */
	boolean updateMonitoringData(Long id, BigDecimal cpuUsage, BigDecimal memoryUsage, BigDecimal gpuUsage);

	/**
	 * Increase the number of restarts
	 *
	 * @param id Container instanceID
	 * @return Is the update successful?
	 */
	boolean increaseRestartCount(Long id);

	/**
	 * Get container instance statistics
	 *
	 * @return Statistical results
	 */
	Map<String, Object> getStatistics();

	/**
	 * Check for duplicate instance names
	 *
	 * @param instanceName Instance name
	 * @param excludeId excludedID(Used when updating)
	 * @return Whether to repeat
	 */
	boolean checkInstanceNameExists(String instanceName, Long excludeId);

	/**
	 * Get a list of running container instances
	 *
	 * @return List of running container instances
	 */
	List<ContainerInstance> getRunningInstances();

	/**
	 * Get the list of container instances in abnormal status
	 *
	 * @return List of container instances in abnormal status
	 */
	List<ContainerInstance> getErrorInstances();

	/**
	 * Get a list of container instances with abnormal health status
	 *
	 * @return List of container instances with abnormal health status
	 */
	List<ContainerInstance> getUnhealthyInstances();

}
