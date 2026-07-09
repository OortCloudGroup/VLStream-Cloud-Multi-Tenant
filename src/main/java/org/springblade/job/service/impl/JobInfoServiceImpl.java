package org.springblade.job.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.powerjob.constant.PowerJobConstant;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.ConvertUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.job.pojo.dto.JobDTO;
import org.springblade.job.pojo.entity.JobInfo;
import org.springblade.job.pojo.entity.JobServer;
import org.springblade.job.mapper.JobInfoMapper;
import org.springblade.job.service.IJobInfoService;
import org.springblade.job.service.IJobServerService;
import org.springblade.job.pojo.vo.JobInfoVO;
import org.springblade.vlstream.detection.DeviceDetectionOrchestrator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.powerjob.client.PowerJobClient;
import tech.powerjob.common.enums.DispatchStrategy;
import tech.powerjob.common.enums.ExecuteType;
import tech.powerjob.common.enums.ProcessorType;
import tech.powerjob.common.enums.TimeExpressionType;
import tech.powerjob.common.model.AlarmConfig;
import tech.powerjob.common.model.LifeCycle;
import tech.powerjob.common.model.LogConfig;
import tech.powerjob.common.request.http.SaveJobInfoRequest;
import tech.powerjob.common.response.JobInfoDTO;
import tech.powerjob.common.response.ResultDTO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Task information sheet Service implementation class
 *
 * @author Oort
 */
@Service
@AllArgsConstructor
@Slf4j
public class JobInfoServiceImpl extends BaseServiceImpl<JobInfoMapper, JobInfo> implements IJobInfoService {
	private final IJobServerService jobServerService;
	private final ObjectProvider<DeviceDetectionOrchestrator> detectionOrchestratorProvider;

	private static final String SCENE_DETECTION_PROCESSOR = "org.springblade.job.processor.ProcessorDeviceObjectDetectModelCheck";
	private static final String SCENE_DETECTION_PROCESSOR_V2 = "org.springblade.job.processor.ProcessorDeviceObjectDetect";

	@Override
	public IPage<JobInfoVO> selectJobInfoPage(IPage<JobInfoVO> page, JobInfoVO jobInfo) {
		return page.setRecords(baseMapper.selectJobInfoPage(page, jobInfo));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean submitAndSync(JobInfo jobInfo) {
		//Get application group server information
		JobServer jobServer = jobServerService.getById(jobInfo.getJobServerId());
		//buildJobclient
		PowerJobClient client = new PowerJobClient(jobServer.getJobServerUrl(), jobServer.getJobAppName(), jobServer.getJobAppPassword());
		SaveJobInfoRequest request = convertToServer(jobInfo);
		//Get upload results
		ResultDTO<Long> result = client.saveJob(request);
		if (result.isSuccess()) {
			jobInfo.setJobId(result.getData());
			return this.saveOrUpdate(jobInfo);
		} else {
			throw new ServiceException(result.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean removeAndSync(List<Long> ids) {
		ids.forEach(id -> {
			JobDTO jobDTO = JobData(id);
			if (Func.isNotEmpty(jobDTO)) {
				JobInfo jobInfo = jobDTO.getJobInfo();
				PowerJobClient powerJobClient = jobDTO.getPowerJobClient();
				//Delete service data
				ResultDTO<Void> result = powerJobClient.deleteJob(jobInfo.getJobId());
				if (result.isSuccess()) {
					this.removeById(id);
				} else {
					throw new ServiceException(result.getMessage());
				}
			}
		});
		return true;
	}

	@Override
	public Boolean changeServerJob(Long id, Integer enable) {
		JobDTO jobDTO = JobData(id);
		if (Func.isNotEmpty(jobDTO)) {
			JobInfo jobInfo = jobDTO.getJobInfo();
			PowerJobClient powerJobClient = jobDTO.getPowerJobClient();
			//Change server status
			ResultDTO<Void> result = (enable == PowerJobConstant.JOB_ENABLED) ?
				powerJobClient.enableJob(jobInfo.getJobId()) :
				powerJobClient.disableJob(jobInfo.getJobId());
			//Delete client data
			if (result.isSuccess()) {
				boolean updated = this.update(Wrappers.<JobInfo>update().lambda().set(JobInfo::getEnable, enable).eq(JobInfo::getId, id));
				if (enable == null || !enable.equals(PowerJobConstant.JOB_ENABLED)) {
					stopSceneGovernanceDetectionIfNeeded(jobInfo);
				}
				return updated;
			} else {
				throw new ServiceException(result.getMessage());
			}
		}
		return false;
	}

	private void stopSceneGovernanceDetectionIfNeeded(JobInfo jobInfo) {
		if (jobInfo == null) {
			return;
		}
		String processorInfo = jobInfo.getProcessorInfo();
		if (!SCENE_DETECTION_PROCESSOR.equals(processorInfo) && !SCENE_DETECTION_PROCESSOR_V2.equals(processorInfo)) {
			return;
		}
		DeviceDetectionOrchestrator orchestrator = detectionOrchestratorProvider.getIfAvailable();
		if (orchestrator == null) {
			return;
		}
		List<Long> cameraIds = splitToIds(jobInfo.getJobParams());
		if (cameraIds.isEmpty()) {
			return;
		}
		try {
			orchestrator.stopAllForDeviceIds(cameraIds, "Scenario management task has been disabled");
		} catch (Exception exception) {
			// The start and stop task interface needs to ensure that the main process is available, Stop failure does not affect Job status change
			log.warn("Stop scene management detection session failure: jobInfoId={}, businessId={}, cameras={}", jobInfo.getId(), jobInfo.getBusinessId(), cameraIds, exception);
		}
	}

	private List<Long> splitToIds(String rawIds) {
		if (Func.isEmpty(rawIds)) {
			return List.of();
		}
		String[] parts = rawIds.split(",");
		List<Long> ids = new ArrayList<>();
		for (String part : parts) {
			Long parsed = parseLong(part);
			if (parsed != null) {
				ids.add(parsed);
			}
		}
		return ids;
	}

	private Long parseLong(String raw) {
		if (Func.isEmpty(raw)) {
			return null;
		}
		try {
			return Long.valueOf(raw.trim());
		} catch (NumberFormatException numberFormatException) {
			return null;
		}
	}

	@Override
	public Boolean runServerJob(Long id) {
		JobDTO jobDTO = JobData(id);
		if (Func.isNotEmpty(jobDTO)) {
			JobInfo jobInfo = jobDTO.getJobInfo();
			PowerJobClient powerJobClient = jobDTO.getPowerJobClient();
			ResultDTO<Long> result = powerJobClient.runJob(jobInfo.getJobId());
			return result.isSuccess();
		}
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean sync() {
		//Task information list
		List<JobInfo> jobInfos = this.list();
		//Task service list
		List<JobServer> jobServers = jobServerService.list();
		//Group by application
		Map<Long, List<JobInfo>> jobGroups = jobInfos.stream().collect(Collectors.groupingBy(JobInfo::getJobServerId));
		//Handle server-side data download
		jobServers.forEach(jobServer -> {
			//buildJobclient
			PowerJobClient client = new PowerJobClient(jobServer.getJobServerUrl(), jobServer.getJobAppName(), jobServer.getJobAppPassword());
			//Get data from server
			List<JobInfoDTO> serverInfoList = Optional.ofNullable(client.fetchAllJob())
				.filter(ResultDTO::isSuccess)
				.map(ResultDTO::getData)
				.orElseGet(ArrayList::new);
			//Get client data
			List<JobInfo> localInfoList = jobGroups.get(jobServer.getId());
			//Process data that needs to be downloaded from the server
			List<JobInfoDTO> jobInfoDTOList = serverInfoList.stream()
				.filter(serverData -> serverData.getStatus() != PowerJobConstant.JOB_DELETED)
				.filter(serverData -> Func.isEmpty(localInfoList) || localInfoList.stream().noneMatch(localData -> Func.equalsSafe(localData.getJobId(), serverData.getId())))
				.collect(Collectors.toList());
			List<JobInfo> dataToDownload = convertToLocalList(jobInfoDTOList, jobServer.getId());
			//call localServicesave data
			this.saveBatch(dataToDownload);
		});
		//Handle client data upload
		jobGroups.forEach((jobServerId, localInfoList) -> {
			//Get application group server information
			JobServer jobServer = jobServers.stream().filter(js -> Func.equalsSafe(js.getId(), jobServerId))
				.findFirst().orElseThrow(() -> new ServiceException(PowerJobConstant.JOB_SYNC_ALERT));
			//buildJobclient
			PowerJobClient client = new PowerJobClient(jobServer.getJobServerUrl(), jobServer.getJobAppName(), jobServer.getJobAppPassword());
			//Process the data that needs to be uploaded to the server
			localInfoList.forEach(localData -> {
				//Convert data format
				SaveJobInfoRequest data = convertToServer(localData);
				//callOpenAPIInterface upload data
				ResultDTO<Long> saveResult = client.saveJob(data);
				if (saveResult.isSuccess()) {
					//Update serverJobIdto client
					this.update(Wrappers.<JobInfo>update().lambda().set(JobInfo::getJobId, saveResult.getData()).eq(JobInfo::getId, localData.getId()));
				} else {
					throw new ServiceException(saveResult.getMessage());
				}
			});
		});
		return true;
	}

	/**
	 * getJobdata collection
	 *
	 * @param jobInfoId Service informationID
	 * @return PowerJobClient
	 */
	public JobDTO JobData(Long jobInfoId) {
		//buildDTOkind
		JobDTO jobDTO = new JobDTO();
		//Get task information
		JobInfo jobInfo = this.getById(jobInfoId);
		jobDTO.setJobInfo(jobInfo);
		if (Func.isEmpty(jobInfo.getJobId())) {
			throw new ServiceException(PowerJobConstant.JOB_SYNC_ALERT);
		}
		if (Func.isNotEmpty(jobInfo.getJobServerId())) {
			//Get application group server information
			JobServer jobServer = jobServerService.getById(jobInfo.getJobServerId());
			jobDTO.setJobServer(jobServer);
			//buildJobclient
			PowerJobClient powerJobClient = new PowerJobClient(jobServer.getJobServerUrl(), jobServer.getJobAppName(), jobServer.getJobAppPassword());
			jobDTO.setPowerJobClient(powerJobClient);
			return jobDTO;
		}
		return null;
	}

	/**
	 * ServerJobList conversion
	 *
	 * @param jobInfoList Local task information list
	 * @return List<SaveJobInfoRequest>
	 */
	public List<SaveJobInfoRequest> convertToServerList(List<JobInfo> jobInfoList) {
		return jobInfoList.stream().map(this::convertToServer).collect(Collectors.toList());
	}

	/**
	 * localJobList conversion
	 *
	 * @param jobInfoDTOList Server task information list
	 * @return List<JobInfo>
	 */
	public List<JobInfo> convertToLocalList(List<JobInfoDTO> jobInfoDTOList, Long jobServerId) {
		return jobInfoDTOList.stream().map(jobInfoDTO -> convertToLocal(jobInfoDTO, jobServerId)).collect(Collectors.toList());
	}

	/**
	 * ServerJobsingle conversion
	 *
	 * @param jobInfo local task information
	 * @return SaveJobInfoRequest
	 */
	public SaveJobInfoRequest convertToServer(JobInfo jobInfo) {
		SaveJobInfoRequest saveJobInfoRequest = new SaveJobInfoRequest();
		if (Func.toLong(jobInfo.getJobId()) > 0L) {
			saveJobInfoRequest.setId(jobInfo.getJobId());
		}
		saveJobInfoRequest.setJobName(jobInfo.getJobName());
		saveJobInfoRequest.setJobDescription(jobInfo.getJobDescription());
		saveJobInfoRequest.setJobParams(jobInfo.getJobParams());
		saveJobInfoRequest.setTimeExpressionType(TimeExpressionType.of(jobInfo.getTimeExpressionType()));
		saveJobInfoRequest.setTimeExpression(jobInfo.getTimeExpression());
		saveJobInfoRequest.setExecuteType(ExecuteType.of(jobInfo.getExecuteType()));
		saveJobInfoRequest.setProcessorType(ProcessorType.of(jobInfo.getProcessorType()));
		saveJobInfoRequest.setProcessorInfo(jobInfo.getProcessorInfo());
		saveJobInfoRequest.setMaxInstanceNum(jobInfo.getMaxInstanceNum());
		saveJobInfoRequest.setConcurrency(jobInfo.getConcurrency());
		saveJobInfoRequest.setInstanceTimeLimit(jobInfo.getInstanceTimeLimit());
		saveJobInfoRequest.setInstanceRetryNum(jobInfo.getInstanceRetryNum());
		saveJobInfoRequest.setTaskRetryNum(jobInfo.getTaskRetryNum());
		saveJobInfoRequest.setMinCpuCores(jobInfo.getMinCpuCores().doubleValue());
		saveJobInfoRequest.setMinMemorySpace(jobInfo.getMinMemorySpace().doubleValue());
		saveJobInfoRequest.setMinDiskSpace(jobInfo.getMinDiskSpace().doubleValue());
		saveJobInfoRequest.setDesignatedWorkers(jobInfo.getDesignatedWorkers());
		saveJobInfoRequest.setMaxWorkerCount(jobInfo.getMaxWorkerCount());
		saveJobInfoRequest.setNotifyUserIds(Func.toLongList(jobInfo.getNotifyUserIds()));
		saveJobInfoRequest.setEnable(jobInfo.getEnable() == 1);
		saveJobInfoRequest.setDispatchStrategy(DispatchStrategy.of(jobInfo.getDispatchStrategy()));
		saveJobInfoRequest.setAlarmConfig(new AlarmConfig(jobInfo.getAlertThreshold(), jobInfo.getStatisticWindowLen(), jobInfo.getSilenceWindowLen()));
		saveJobInfoRequest.setLogConfig(new LogConfig().setLevel(jobInfo.getLogLevel()).setType(jobInfo.getLogType()));
		if (Func.isNotEmpty(jobInfo.getLifecycle())) {
			LifeCycle lifeCycle = new LifeCycle();
			String[] lifeCycleArr = Func.toStrArray(jobInfo.getLifecycle());
			lifeCycle.setStart(DateUtil.parse(lifeCycleArr[0], DateUtil.PATTERN_DATETIME).getTime());
			lifeCycle.setEnd(DateUtil.parse(lifeCycleArr[1], DateUtil.PATTERN_DATETIME).getTime());
			saveJobInfoRequest.setLifeCycle(lifeCycle);
		}
		saveJobInfoRequest.setExtra(jobInfo.getExtra());
		return saveJobInfoRequest;
	}

	/**
	 * localJobsingle conversion
	 *
	 * @param jobInfoDTO Server task information
	 * @return SaveJobInfoRequest
	 */
	public JobInfo convertToLocal(JobInfoDTO jobInfoDTO, Long jobServerId) {
		JobInfo jobInfo = new JobInfo();
		jobInfo.setJobServerId(jobServerId);
		jobInfo.setJobId(jobInfoDTO.getId());
		jobInfo.setJobName(jobInfoDTO.getJobName());
		jobInfo.setJobDescription(jobInfoDTO.getJobDescription());
		jobInfo.setJobParams(jobInfoDTO.getJobParams());
		jobInfo.setTimeExpressionType(jobInfoDTO.getTimeExpressionType());
		jobInfo.setTimeExpression(jobInfoDTO.getTimeExpression());
		jobInfo.setExecuteType(jobInfoDTO.getExecuteType());
		jobInfo.setProcessorType(jobInfoDTO.getProcessorType());
		jobInfo.setProcessorInfo(jobInfoDTO.getProcessorInfo());
		jobInfo.setMaxInstanceNum(jobInfoDTO.getMaxInstanceNum());
		jobInfo.setConcurrency(jobInfoDTO.getConcurrency());
		jobInfo.setInstanceTimeLimit(jobInfoDTO.getInstanceTimeLimit());
		jobInfo.setInstanceRetryNum(jobInfoDTO.getInstanceRetryNum());
		jobInfo.setTaskRetryNum(jobInfoDTO.getTaskRetryNum());
		jobInfo.setMinCpuCores(ConvertUtil.convert(jobInfoDTO.getMinCpuCores(), BigDecimal.class));
		jobInfo.setMinMemorySpace(ConvertUtil.convert(jobInfoDTO.getMinMemorySpace(), BigDecimal.class));
		jobInfo.setMinDiskSpace(ConvertUtil.convert(jobInfoDTO.getMinDiskSpace(), BigDecimal.class));
		jobInfo.setDesignatedWorkers(jobInfoDTO.getDesignatedWorkers());
		jobInfo.setMaxWorkerCount(jobInfoDTO.getMaxWorkerCount());
		jobInfo.setNotifyUserIds(jobInfoDTO.getNotifyUserIds());
		jobInfo.setEnable(jobInfoDTO.getStatus());
		jobInfo.setDispatchStrategy(jobInfoDTO.getDispatchStrategy());
		if (Func.isNotEmpty(jobInfoDTO.getLifecycle()) && !Func.equalsSafe(jobInfoDTO.getLifecycle(), StringPool.EMPTY_JSON)) {
			LifeCycle lifeCycle = JsonUtil.parse(jobInfoDTO.getLifecycle(), LifeCycle.class);
			String start = DateUtil.format(new Date(lifeCycle.getStart()), DateUtil.PATTERN_DATETIME);
			String end = DateUtil.format(new Date(lifeCycle.getEnd()), DateUtil.PATTERN_DATETIME);
			jobInfo.setLifecycle(start + StringPool.COMMA + end);
		}
		if (Func.isNotEmpty(jobInfoDTO.getAlarmConfig())) {
			jobInfo.setAlertThreshold(jobInfoDTO.getAlarmConfig().getAlertThreshold());
			jobInfo.setStatisticWindowLen(jobInfoDTO.getAlarmConfig().getStatisticWindowLen());
			jobInfo.setSilenceWindowLen(jobInfoDTO.getAlarmConfig().getSilenceWindowLen());
		}
		if (Func.isNotEmpty(jobInfoDTO.getLogConfig())) {
			jobInfo.setLogType(jobInfoDTO.getLogConfig().getType());
			jobInfo.setLogLevel(jobInfoDTO.getLogConfig().getLevel());
		}
		jobInfo.setExtra(jobInfoDTO.getExtra());
		return jobInfo;
	}

}
