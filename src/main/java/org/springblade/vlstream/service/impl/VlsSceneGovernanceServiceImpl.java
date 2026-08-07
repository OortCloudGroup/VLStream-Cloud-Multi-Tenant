package org.springblade.vlstream.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.powerjob.constant.PowerJobConstant;
import org.springblade.job.pojo.entity.JobInfo;
import org.springblade.job.pojo.entity.JobServer;
import org.springblade.job.service.IJobInfoService;
import org.springblade.job.service.IJobServerService;
import org.springblade.vlstream.excel.VlsSceneGovernanceExcel;
import org.springblade.vlstream.mapper.VlsSceneGovernanceMapper;
import org.springblade.vlstream.pojo.entity.SceneGovernance;
import org.springblade.vlstream.pojo.vo.SceneGovernanceVO;
import org.springblade.vlstream.service.IVlsSceneGovernanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.powerjob.common.enums.DispatchStrategy;
import tech.powerjob.common.enums.ExecuteType;
import tech.powerjob.common.enums.ProcessorType;
import tech.powerjob.common.enums.TimeExpressionType;

import java.math.BigDecimal;
import java.util.List;

/**
 * 场景治理表 服务实现类
 *
 * @author Oort
 * @since 2025-12-23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VlsSceneGovernanceServiceImpl extends BaseServiceImpl<VlsSceneGovernanceMapper, SceneGovernance> implements IVlsSceneGovernanceService {

	private static final String DEFAULT_PROCESSOR_INFO = "org.springblade.job.processor.ProcessorDeviceObjectDetectModelCheck";
	private static final String DEFAULT_JOB_PARAMS = "";
	private static final int DEFAULT_MAX_INSTANCE_NUM = 1;
	private static final int DEFAULT_CONCURRENCY = 1;
	private static final int DEFAULT_RETRY_COUNT = 0;
	private static final int DEFAULT_MAX_WORKER_COUNT = 0;
	private static final long DEFAULT_INSTANCE_TIME_LIMIT = 0L;
	private static final int DEFAULT_ALARM_VALUE = 0;
	private static final int DEFAULT_LOG_VALUE = 0;
	private static final BigDecimal DEFAULT_RESOURCE = BigDecimal.ZERO;

	private final IJobInfoService jobInfoService;
	private final IJobServerService jobServerService;

	@Override
	public IPage<SceneGovernanceVO> selectVlsSceneGovernancePage(IPage<SceneGovernanceVO> page, SceneGovernanceVO vlsSceneGovernance) {
		return page.setRecords(baseMapper.selectVlsSceneGovernancePage(page, vlsSceneGovernance));
	}

	@Override
	public List<VlsSceneGovernanceExcel> exportVlsSceneGovernance(Wrapper<SceneGovernance> queryWrapper) {
		List<VlsSceneGovernanceExcel> vlsSceneGovernanceList = baseMapper.exportVlsSceneGovernance(queryWrapper);
		//vlsSceneGovernanceList.forEach(vlsSceneGovernance -> {
		//	vlsSceneGovernance.setTypeName(DictCache.getValue(DictEnum.YES_NO, VlsSceneGovernanceEntity.getType()));
		//});
		return vlsSceneGovernanceList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveAndSchedule(SceneGovernance sceneGovernance) {
		boolean saved = save(sceneGovernance);
		if (!saved) {
			return false;
		}
		return syncJob(sceneGovernance);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateAndSchedule(SceneGovernance sceneGovernance) {
		boolean updated = updateById(sceneGovernance);
		if (!updated) {
			return false;
		}
		return syncJob(sceneGovernance);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submitAndSchedule(SceneGovernance sceneGovernance) {
		boolean submitted = saveOrUpdate(sceneGovernance);
		if (!submitted) {
			return false;
		}
		return syncJob(sceneGovernance);
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean syncJob(SceneGovernance sceneGovernance) {
		Long sceneId = sceneGovernance.getId();
		if (sceneId == null) {
			throw new ServiceException("Scene governance id is required.");
		}
		String cronExpression = StringUtils.trimToEmpty(sceneGovernance.getCronExpression());
		if (StringUtils.isBlank(cronExpression)) {
			throw new ServiceException("Cron expression is required.");
		}

		JobServer jobServer = findDefaultJobServer();
		JobInfo existingJob = findJobInfo(sceneId);
		JobInfo jobInfo = buildJobInfo(sceneGovernance, jobServer, existingJob, cronExpression);

		jobInfoService.submitAndSync(jobInfo);

		Long jobInfoId = jobInfo.getId();
		if (jobInfoId == null) {
			jobInfoId = resolveJobInfoId(sceneId);
		}
		if (jobInfoId == null) {
			log.warn("Job info not found after sync, sceneId={}", sceneId);
			return true;
		}
		boolean runResult = jobInfoService.runServerJob(jobInfoId);
		if (!runResult) {
			log.warn("Run job failed, sceneId={}, jobInfoId={}", sceneId, jobInfoId);
		}
		return true;
	}

	private JobServer findDefaultJobServer() {
		List<JobServer> jobServers = jobServerService.list(Wrappers.<JobServer>lambdaQuery()
			.orderByAsc(JobServer::getId)
			.last("limit 1"));
		return jobServers.stream()
			.findFirst()
			.orElseThrow(() -> new ServiceException("Job server not configured."));
	}

	private JobInfo findJobInfo(Long sceneId) {
		List<JobInfo> jobInfos = jobInfoService.list(Wrappers.<JobInfo>lambdaQuery()
			.eq(JobInfo::getBusinessId, sceneId)
			.orderByDesc(JobInfo::getId)
			.last("limit 1"));
		return jobInfos.stream().findFirst().orElse(null);
	}

	private Long resolveJobInfoId(Long sceneId) {
		JobInfo jobInfo = findJobInfo(sceneId);
		return jobInfo == null ? null : jobInfo.getId();
	}

	private JobInfo buildJobInfo(SceneGovernance sceneGovernance, JobServer jobServer, JobInfo existingJob, String cronExpression) {
		JobInfo jobInfo = existingJob == null ? new JobInfo() : existingJob;
		jobInfo.setBusinessId(sceneGovernance.getId());
		jobInfo.setJobServerId(jobServer.getId());
		jobInfo.setJobName(resolveJobName(sceneGovernance));
		jobInfo.setJobDescription(StringUtils.trimToEmpty(sceneGovernance.getDescription()));
		jobInfo.setJobParams(StringUtils.trimToEmpty(sceneGovernance.getCameras()));
		jobInfo.setTimeExpressionType(TimeExpressionType.CRON.getV());
		jobInfo.setTimeExpression(cronExpression);
		jobInfo.setExecuteType(ExecuteType.STANDALONE.getV());
		jobInfo.setProcessorType(ProcessorType.BUILT_IN.getV());
		jobInfo.setProcessorInfo(DEFAULT_PROCESSOR_INFO);
		applyDefaultJobSettings(jobInfo);
		return jobInfo;
	}

	private String resolveJobName(SceneGovernance sceneGovernance) {
		String name = StringUtils.trimToEmpty(sceneGovernance.getName());
		if (StringUtils.isNotBlank(name)) {
			return name;
		}
		Long sceneId = sceneGovernance.getId();
		return sceneId == null ? "scene-governance" : "scene-governance-" + sceneId;
	}

	private void applyDefaultJobSettings(JobInfo jobInfo) {
		if (jobInfo.getEnable() == null) {
			jobInfo.setEnable(PowerJobConstant.JOB_ENABLED);
		}
		if (jobInfo.getDispatchStrategy() == null) {
			jobInfo.setDispatchStrategy(DispatchStrategy.HEALTH_FIRST.getV());
		}
		if (jobInfo.getMaxInstanceNum() == null) {
			jobInfo.setMaxInstanceNum(DEFAULT_MAX_INSTANCE_NUM);
		}
		if (jobInfo.getConcurrency() == null) {
			jobInfo.setConcurrency(DEFAULT_CONCURRENCY);
		}
		if (jobInfo.getInstanceTimeLimit() == null) {
			jobInfo.setInstanceTimeLimit(DEFAULT_INSTANCE_TIME_LIMIT);
		}
		if (jobInfo.getInstanceRetryNum() == null) {
			jobInfo.setInstanceRetryNum(DEFAULT_RETRY_COUNT);
		}
		if (jobInfo.getTaskRetryNum() == null) {
			jobInfo.setTaskRetryNum(DEFAULT_RETRY_COUNT);
		}
		if (jobInfo.getMinCpuCores() == null) {
			jobInfo.setMinCpuCores(DEFAULT_RESOURCE);
		}
		if (jobInfo.getMinMemorySpace() == null) {
			jobInfo.setMinMemorySpace(DEFAULT_RESOURCE);
		}
		if (jobInfo.getMinDiskSpace() == null) {
			jobInfo.setMinDiskSpace(DEFAULT_RESOURCE);
		}
		if (jobInfo.getMaxWorkerCount() == null) {
			jobInfo.setMaxWorkerCount(DEFAULT_MAX_WORKER_COUNT);
		}
		if (jobInfo.getNotifyUserIds() == null) {
			jobInfo.setNotifyUserIds(StringUtils.EMPTY);
		}
		if (jobInfo.getAlertThreshold() == null) {
			jobInfo.setAlertThreshold(DEFAULT_ALARM_VALUE);
		}
		if (jobInfo.getStatisticWindowLen() == null) {
			jobInfo.setStatisticWindowLen(DEFAULT_ALARM_VALUE);
		}
		if (jobInfo.getSilenceWindowLen() == null) {
			jobInfo.setSilenceWindowLen(DEFAULT_ALARM_VALUE);
		}
		if (jobInfo.getLogType() == null) {
			jobInfo.setLogType(DEFAULT_LOG_VALUE);
		}
		if (jobInfo.getLogLevel() == null) {
			jobInfo.setLogLevel(DEFAULT_LOG_VALUE);
		}
		if (jobInfo.getExtra() == null) {
			jobInfo.setExtra(StringUtils.EMPTY);
		}
	}

}
