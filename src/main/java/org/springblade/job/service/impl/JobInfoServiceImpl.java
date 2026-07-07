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
 * 任务信息表 服务实现类
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
		//获取应用分组服务端信息
		JobServer jobServer = jobServerService.getById(jobInfo.getJobServerId());
		//构建Job客户端
		PowerJobClient client = new PowerJobClient(jobServer.getJobServerUrl(), jobServer.getJobAppName(), jobServer.getJobAppPassword());
		SaveJobInfoRequest request = convertToServer(jobInfo);
		//获取上传结果
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
				//删除服务数据
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
			//更换服务端状态
			ResultDTO<Void> result = (enable == PowerJobConstant.JOB_ENABLED) ?
				powerJobClient.enableJob(jobInfo.getJobId()) :
				powerJobClient.disableJob(jobInfo.getJobId());
			//删除客户端数据
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
			orchestrator.stopAllForDeviceIds(cameraIds, "场景治理任务已停用");
		} catch (Exception exception) {
			// 启停任务接口需要保证主流程可用，停止失败不影响 Job 状态变更
			log.warn("停止场景治理检测会话失败: jobInfoId={}, businessId={}, cameras={}", jobInfo.getId(), jobInfo.getBusinessId(), cameraIds, exception);
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
		//任务信息列表
		List<JobInfo> jobInfos = this.list();
		//任务服务列表
		List<JobServer> jobServers = jobServerService.list();
		//按应用分组
		Map<Long, List<JobInfo>> jobGroups = jobInfos.stream().collect(Collectors.groupingBy(JobInfo::getJobServerId));
		//处理服务端数据下载
		jobServers.forEach(jobServer -> {
			//构建Job客户端
			PowerJobClient client = new PowerJobClient(jobServer.getJobServerUrl(), jobServer.getJobAppName(), jobServer.getJobAppPassword());
			//从服务端获取数据
			List<JobInfoDTO> serverInfoList = Optional.ofNullable(client.fetchAllJob())
				.filter(ResultDTO::isSuccess)
				.map(ResultDTO::getData)
				.orElseGet(ArrayList::new);
			//获取客户端数据
			List<JobInfo> localInfoList = jobGroups.get(jobServer.getId());
			//处理需要从服务端下载的数据
			List<JobInfoDTO> jobInfoDTOList = serverInfoList.stream()
				.filter(serverData -> serverData.getStatus() != PowerJobConstant.JOB_DELETED)
				.filter(serverData -> Func.isEmpty(localInfoList) || localInfoList.stream().noneMatch(localData -> Func.equalsSafe(localData.getJobId(), serverData.getId())))
				.collect(Collectors.toList());
			List<JobInfo> dataToDownload = convertToLocalList(jobInfoDTOList, jobServer.getId());
			//调用本地Service保存数据
			this.saveBatch(dataToDownload);
		});
		//处理客户端数据上传
		jobGroups.forEach((jobServerId, localInfoList) -> {
			//获取应用分组服务端信息
			JobServer jobServer = jobServers.stream().filter(js -> Func.equalsSafe(js.getId(), jobServerId))
				.findFirst().orElseThrow(() -> new ServiceException(PowerJobConstant.JOB_SYNC_ALERT));
			//构建Job客户端
			PowerJobClient client = new PowerJobClient(jobServer.getJobServerUrl(), jobServer.getJobAppName(), jobServer.getJobAppPassword());
			//处理需要上传到服务端的数据
			localInfoList.forEach(localData -> {
				//转换数据格式
				SaveJobInfoRequest data = convertToServer(localData);
				//调用OpenAPI接口上传数据
				ResultDTO<Long> saveResult = client.saveJob(data);
				if (saveResult.isSuccess()) {
					//更新服务端JobId至客户端
					this.update(Wrappers.<JobInfo>update().lambda().set(JobInfo::getJobId, saveResult.getData()).eq(JobInfo::getId, localData.getId()));
				} else {
					throw new ServiceException(saveResult.getMessage());
				}
			});
		});
		return true;
	}

	/**
	 * 获取Job数据集合
	 *
	 * @param jobInfoId 服务信息ID
	 * @return PowerJobClient
	 */
	public JobDTO JobData(Long jobInfoId) {
		//构建DTO类
		JobDTO jobDTO = new JobDTO();
		//获取任务信息
		JobInfo jobInfo = this.getById(jobInfoId);
		jobDTO.setJobInfo(jobInfo);
		if (Func.isEmpty(jobInfo.getJobId())) {
			throw new ServiceException(PowerJobConstant.JOB_SYNC_ALERT);
		}
		if (Func.isNotEmpty(jobInfo.getJobServerId())) {
			//获取应用分组服务端信息
			JobServer jobServer = jobServerService.getById(jobInfo.getJobServerId());
			jobDTO.setJobServer(jobServer);
			//构建Job客户端
			PowerJobClient powerJobClient = new PowerJobClient(jobServer.getJobServerUrl(), jobServer.getJobAppName(), jobServer.getJobAppPassword());
			jobDTO.setPowerJobClient(powerJobClient);
			return jobDTO;
		}
		return null;
	}

	/**
	 * 服务端Job列表转换
	 *
	 * @param jobInfoList 本地任务信息列表
	 * @return List<SaveJobInfoRequest>
	 */
	public List<SaveJobInfoRequest> convertToServerList(List<JobInfo> jobInfoList) {
		return jobInfoList.stream().map(this::convertToServer).collect(Collectors.toList());
	}

	/**
	 * 本地Job列表转换
	 *
	 * @param jobInfoDTOList 服务端任务信息列表
	 * @return List<JobInfo>
	 */
	public List<JobInfo> convertToLocalList(List<JobInfoDTO> jobInfoDTOList, Long jobServerId) {
		return jobInfoDTOList.stream().map(jobInfoDTO -> convertToLocal(jobInfoDTO, jobServerId)).collect(Collectors.toList());
	}

	/**
	 * 服务端Job单个转换
	 *
	 * @param jobInfo 本地任务信息
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
	 * 本地Job单个转换
	 *
	 * @param jobInfoDTO 服务端任务信息
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
