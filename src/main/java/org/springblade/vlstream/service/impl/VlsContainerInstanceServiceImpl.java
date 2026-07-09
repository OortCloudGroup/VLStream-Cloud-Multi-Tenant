package org.springblade.vlstream.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.vlstream.excel.VlsContainerInstanceExcel;
import org.springblade.vlstream.mapper.VlsContainerInstanceMapper;
import org.springblade.vlstream.pojo.dto.ContainerInstanceCreateDTO;
import org.springblade.vlstream.pojo.dto.ContainerInstanceQueryDTO;
import org.springblade.vlstream.pojo.dto.ContainerInstanceUpdateDTO;
import org.springblade.vlstream.pojo.entity.ContainerInstance;
import org.springblade.vlstream.pojo.vo.ContainerInstanceVO;
import org.springblade.vlstream.service.IVlsContainerInstanceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Container instance table Service implementation class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Slf4j
@Service
public class VlsContainerInstanceServiceImpl extends BaseServiceImpl<VlsContainerInstanceMapper, ContainerInstance> implements IVlsContainerInstanceService {

	@Override
	public IPage<ContainerInstanceVO> selectVlsContainerInstancePage(IPage<ContainerInstanceVO> page, ContainerInstanceVO vlsContainerInstance) {
		return page.setRecords(baseMapper.selectVlsContainerInstancePage(page, vlsContainerInstance));
	}

	@Override
	public List<VlsContainerInstanceExcel> exportVlsContainerInstance(Wrapper<ContainerInstance> queryWrapper) {
		List<VlsContainerInstanceExcel> vlsContainerInstanceList = baseMapper.exportVlsContainerInstance(queryWrapper);
		//vlsContainerInstanceList.forEach(vlsContainerInstance -> {
		//	vlsContainerInstance.setTypeName(DictCache.getValue(DictEnum.YES_NO, VlsContainerInstanceEntity.getType()));
		//});
		return vlsContainerInstanceList;
	}

	@Override
	public IPage<ContainerInstance> pageContainerInstances(Page<ContainerInstance> page, ContainerInstanceQueryDTO queryDTO) {
		log.info("Paging query container instance, Current page: {}, page size: {}", page.getCurrent(), page.getSize());

		return baseMapper.selectPageWithDetails(page, queryDTO);
	}

	@Override
	public ContainerInstance getContainerInstanceById(Long id) {
		log.info("according toIDQuery container instance details, ID: {}", id);

		ContainerInstance instance = baseMapper.selectByIdWithDetails(id);
		if (instance == null) {
			throw new RuntimeException("Container instance does not exist, ID: " + id);
		}

		return instance;
	}

	@Override
	@Transactional
	public ContainerInstance createContainerInstance(ContainerInstanceCreateDTO createDTO) {
		log.info("Create container instance, Instance name: {}", createDTO.getInstanceName());

		// Check for duplicate instance names
		if (checkInstanceNameExists(createDTO.getInstanceName(), null)) {
			throw new RuntimeException("Instance name already exists: " + createDTO.getInstanceName());
		}

		// Create container instance
		ContainerInstance instance = new ContainerInstance();
		BeanUtils.copyProperties(createDTO, instance);

		// Set default value
		instance.setInstanceStatus("stopped");
		instance.setHealthStatus("unknown");
		instance.setRestartCount(0);

		// Save to database
		save(instance);

		log.info("Container instance created successfully, ID: {}", instance.getId());
		return instance;
	}

	@Override
	@Transactional
	public ContainerInstance updateContainerInstance(ContainerInstanceUpdateDTO updateDTO) {
		log.info("Update container instance, ID: {}", updateDTO.getId());

		ContainerInstance instance = getById(updateDTO.getId());
		if (instance == null) {
			throw new RuntimeException("Container instance does not exist, ID: " + updateDTO.getId());
		}

		// Check for duplicate instance names(Exclude current instance)
		if (StringUtils.hasText(updateDTO.getInstanceName()) &&
			checkInstanceNameExists(updateDTO.getInstanceName(), updateDTO.getId())) {
			throw new RuntimeException("Instance name already exists: " + updateDTO.getInstanceName());
		}

		// Copy non-empty properties
		if (StringUtils.hasText(updateDTO.getInstanceName())) {
			instance.setInstanceName(updateDTO.getInstanceName());
		}
		if (StringUtils.hasText(updateDTO.getImageType())) {
			instance.setImageType(updateDTO.getImageType());
		}
		if (updateDTO.getResourceTypeId() != null) {
			instance.setResourceTypeId(updateDTO.getResourceTypeId());
		}
		if (updateDTO.getResourceSpecId() != null) {
			instance.setResourceSpecId(updateDTO.getResourceSpecId());
		}
		if (updateDTO.getInstanceCount() != null) {
			instance.setInstanceCount(updateDTO.getInstanceCount());
		}
		if (StringUtils.hasText(updateDTO.getContainerId())) {
			instance.setContainerId(updateDTO.getContainerId());
		}
		if (StringUtils.hasText(updateDTO.getInstanceStatus())) {
			instance.setInstanceStatus(updateDTO.getInstanceStatus());
		}
		if (StringUtils.hasText(updateDTO.getHealthStatus())) {
			instance.setHealthStatus(updateDTO.getHealthStatus());
		}
		if (updateDTO.getRestartCount() != null) {
			instance.setRestartCount(updateDTO.getRestartCount());
		}
		if (updateDTO.getCpuUsage() != null) {
			instance.setCpuUsage(updateDTO.getCpuUsage());
		}
		if (updateDTO.getMemoryUsage() != null) {
			instance.setMemoryUsage(updateDTO.getMemoryUsage());
		}
		if (updateDTO.getGpuUsage() != null) {
			instance.setGpuUsage(updateDTO.getGpuUsage());
		}
		if (StringUtils.hasText(updateDTO.getCpuLimit())) {
			instance.setCpuLimit(updateDTO.getCpuLimit());
		}
		if (StringUtils.hasText(updateDTO.getMemoryLimit())) {
			instance.setMemoryLimit(updateDTO.getMemoryLimit());
		}
		if (StringUtils.hasText(updateDTO.getGpuLimit())) {
			instance.setGpuLimit(updateDTO.getGpuLimit());
		}
		if (StringUtils.hasText(updateDTO.getPortConfig())) {
			instance.setPortConfig(updateDTO.getPortConfig());
		}
		if (StringUtils.hasText(updateDTO.getEnvConfig())) {
			instance.setEnvConfig(updateDTO.getEnvConfig());
		}
		if (StringUtils.hasText(updateDTO.getVolumeConfig())) {
			instance.setVolumeConfig(updateDTO.getVolumeConfig());
		}
		if (StringUtils.hasText(updateDTO.getLogsPath())) {
			instance.setLogsPath(updateDTO.getLogsPath());
		}

		// Update to database
		updateById(instance);

		log.info("Container instance updated successfully, ID: {}", instance.getId());
		return instance;
	}

	@Override
	@Transactional
	public boolean deleteContainerInstance(Long id) {
		log.info("Delete container instance, ID: {}", id);

		ContainerInstance instance = getById(id);
		if (instance == null) {
			throw new RuntimeException("Container instance does not exist, ID: " + id);
		}

		// Check if it can be deleted
		if ("running".equals(instance.getInstanceStatus()) || "starting".equals(instance.getInstanceStatus())) {
			throw new RuntimeException("Container instance is running, cannot be deleted");
		}

		return removeById(id);
	}

	@Override
	@Transactional
	public boolean deleteContainerInstanceBatch(List<Long> ids) {
		log.info("Delete container instances in batches, quantity: {}", ids.size());

		// Check if there is a running instance
		List<ContainerInstance> instances = listByIds(ids);
		for (ContainerInstance instance : instances) {
			if ("running".equals(instance.getInstanceStatus()) || "starting".equals(instance.getInstanceStatus())) {
				throw new RuntimeException("There is a running container instance, cannot be deleted");
			}
		}

		return baseMapper.deleteBatch(ids) > 0;
	}

	@Override
	public ContainerInstance getByContainerId(String containerId) {
		return baseMapper.selectByContainerId(containerId);
	}

	@Override
	public List<ContainerInstance> getByAlgorithmId(Long algorithmId) {
		return baseMapper.selectByAlgorithmId(algorithmId);
	}

	@Override
	public List<ContainerInstance> getByStatus(String instanceStatus) {
		return baseMapper.selectByStatus(instanceStatus);
	}

	@Override
	@Transactional
	public boolean startContainer(Long id, String containerId) {
		log.info("Start container instance, ID: {}", id);

		ContainerInstance instance = getById(id);
		if (instance == null) {
			throw new RuntimeException("Container instance does not exist, ID: " + id);
		}

		if ("running".equals(instance.getInstanceStatus())) {
			throw new RuntimeException("Container instance is already running");
		}

		// This should be calledDocker APIStart container
		// TODO: integratedDocker API

		// update status
		return updateInstanceStatus(id, "starting", "unknown", containerId, new Date(), null);
	}

	@Override
	@Transactional
	public boolean stopContainer(Long id) {
		log.info("Stop a container instance, ID: {}", id);

		ContainerInstance instance = getById(id);
		if (instance == null) {
			throw new RuntimeException("Container instance does not exist, ID: " + id);
		}

		if ("stopped".equals(instance.getInstanceStatus())) {
			throw new RuntimeException("Container instance stopped");
		}

		// This should be calledDocker APIStop container
		// TODO: integratedDocker API

		// update status
		return updateInstanceStatus(id, "stopping", "unknown", instance.getContainerId(),
			instance.getStartTime(), new Date());
	}

	@Override
	@Transactional
	public boolean restartContainer(Long id) {
		log.info("Restart container instance, ID: {}", id);

		ContainerInstance instance = getById(id);
		if (instance == null) {
			throw new RuntimeException("Container instance does not exist, ID: " + id);
		}

		// This should be calledDocker APIRestart container
		// TODO: integratedDocker API

		// Increase the number of restarts
		increaseRestartCount(id);

		// update status
		return updateInstanceStatus(id, "starting", "unknown", instance.getContainerId(),
			new Date(), null);
	}

	@Override
	public boolean updateInstanceStatus(Long id, String instanceStatus, String healthStatus,
										String containerId, Date startTime, Date stopTime) {
		return baseMapper.updateInstanceStatus(id, instanceStatus, healthStatus, containerId, startTime, stopTime) > 0;
	}

	@Override
	public boolean updateMonitoringData(Long id, BigDecimal cpuUsage, BigDecimal memoryUsage, BigDecimal gpuUsage) {
		return baseMapper.updateMonitoringData(id, cpuUsage, memoryUsage, gpuUsage) > 0;
	}

	@Override
	public boolean increaseRestartCount(Long id) {
		return baseMapper.increaseRestartCount(id) > 0;
	}

	@Override
	public Map<String, Object> getStatistics() {
		return baseMapper.selectStatistics();
	}

	@Override
	public boolean checkInstanceNameExists(String instanceName, Long excludeId) {
		ContainerInstance instance = baseMapper.selectByInstanceName(instanceName, excludeId);
		return instance != null;
	}

	@Override
	public List<ContainerInstance> getRunningInstances() {
		return getByStatus("running");
	}

	@Override
	public List<ContainerInstance> getErrorInstances() {
		return getByStatus("error");
	}

	@Override
	public List<ContainerInstance> getUnhealthyInstances() {
		ContainerInstanceQueryDTO queryDTO = new ContainerInstanceQueryDTO();
		queryDTO.setHealthStatus("unhealthy");

		Page<ContainerInstance> page = new Page<>(1, Integer.MAX_VALUE);
		IPage<ContainerInstance> result = pageContainerInstances(page, queryDTO);
		return result.getRecords();
	}

}
