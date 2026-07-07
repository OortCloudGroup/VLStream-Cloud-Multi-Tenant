package org.springblade.modules.dify.client;

import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.DifyWorkflowClient;
import io.github.imfangs.dify.client.callback.WorkflowStreamCallback;
import io.github.imfangs.dify.client.enums.FileTransferMethod;
import io.github.imfangs.dify.client.enums.FileType;
import io.github.imfangs.dify.client.enums.ResponseMode;
import io.github.imfangs.dify.client.event.ErrorEvent;
import io.github.imfangs.dify.client.event.NodeFinishedEvent;
import io.github.imfangs.dify.client.event.NodeStartedEvent;
import io.github.imfangs.dify.client.event.PingEvent;
import io.github.imfangs.dify.client.event.TtsMessageEndEvent;
import io.github.imfangs.dify.client.event.TtsMessageEvent;
import io.github.imfangs.dify.client.event.WorkflowFinishedEvent;
import io.github.imfangs.dify.client.event.WorkflowStartedEvent;
import io.github.imfangs.dify.client.event.WorkflowTextChunkEvent;
import io.github.imfangs.dify.client.exception.DifyApiException;
import io.github.imfangs.dify.client.model.file.FileInfo;
import io.github.imfangs.dify.client.model.file.FileUploadResponse;
import io.github.imfangs.dify.client.model.workflow.WorkflowLogsResponse;
import io.github.imfangs.dify.client.model.workflow.WorkflowRunRequest;
import io.github.imfangs.dify.client.model.workflow.WorkflowRunResponse;
import io.github.imfangs.dify.client.model.workflow.WorkflowRunStatusResponse;
import io.github.imfangs.dify.client.model.workflow.WorkflowStopResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springblade.modules.dify.config.DifyProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Dify workflow facade with parameterized operations instead of hard coded demo data.
 */
@Slf4j
@Component
public class DifyWorkflowService {

	private static final String DEFAULT_TEST_USER_ID = "test-user-" + System.currentTimeMillis();
	private static final long DEFAULT_STREAM_TIMEOUT_SECONDS = 60L;

	@Resource
	private DifyProperties difyProperties;

	private DifyWorkflowClient workflowClient;

	@PostConstruct
	public void initClient() {
		this.workflowClient = DifyClientFactory.createWorkflowClient(difyProperties.getBaseUrl(), difyProperties.getWorkflowApi());
	}

	/**
	 * Execute a workflow with the provided inputs.
	 *
	 * @param userId       dify user id, optional
	 * @param inputs       workflow inputs
	 * @param responseMode response mode, defaults to {@link ResponseMode#BLOCKING}
	 * @return workflow response
	 */
	public WorkflowRunResponse runWorkflow(String userId, Map<String, Object> inputs, ResponseMode responseMode) throws DifyApiException, IOException {
		Assert.notEmpty(inputs, "Workflow inputs must not be empty");
		WorkflowRunRequest request = buildRequest(userId, inputs, responseMode == null ? ResponseMode.BLOCKING : responseMode);
		WorkflowRunResponse response = workflowClient.runWorkflow(request);
		logWorkflowOutputs(response);
		return response;
	}

	/**
	 * Stream workflow execution with an optional timeout and collect the textual output using the built-in logger.
	 *
	 * @param userId      dify user id, optional
	 * @param inputs      workflow inputs
	 * @param awaitSecond timeout seconds, defaults to 60 seconds
	 * @return collected textual output
	 * @throws InterruptedException if waiting for stream completion is interrupted
	 */
	public String runWorkflowStream(String userId, Map<String, Object> inputs, long awaitSecond) throws InterruptedException, DifyApiException, IOException {
		Assert.notEmpty(inputs, "Workflow inputs must not be empty");
		long timeoutSeconds = awaitSecond > 0 ? awaitSecond : DEFAULT_STREAM_TIMEOUT_SECONDS;
		CountDownLatch latch = new CountDownLatch(1);
		StringBuilder outputBuilder = new StringBuilder();
		WorkflowStreamCallback callback = createLoggingCallback(latch, outputBuilder);
		WorkflowRunRequest request = buildRequest(userId, inputs, ResponseMode.STREAMING);
		workflowClient.runWorkflowStream(request, callback);
		boolean completed = latch.await(timeoutSeconds, TimeUnit.SECONDS);
		if (!completed) {
			log.warn("Streaming workflow did not finish within {} seconds", timeoutSeconds);
		}
		return outputBuilder.toString();
	}

	/**
	 * Stream workflow execution with a caller supplied callback.
	 *
	 * @param userId   dify user id, optional
	 * @param inputs   workflow inputs
	 * @param callback user provided callback
	 */
	public void runWorkflowStream(String userId, Map<String, Object> inputs, WorkflowStreamCallback callback) throws DifyApiException, IOException {
		Assert.notEmpty(inputs, "Workflow inputs must not be empty");
		Assert.notNull(callback, "WorkflowStreamCallback must not be null");
		WorkflowRunRequest request = buildRequest(userId, inputs, ResponseMode.STREAMING);
		workflowClient.runWorkflowStream(request, callback);
	}

	/**
	 * Stop a workflow execution using task id.
	 *
	 * @param taskId workflow task id
	 * @param userId dify user id, optional
	 * @return stop result
	 */
	public WorkflowStopResponse stopWorkflow(String taskId, String userId) throws DifyApiException, IOException {
		Assert.hasText(taskId, "Task id must not be blank");
		WorkflowStopResponse response = workflowClient.stopWorkflow(taskId, resolveUserId(userId));
		log.info("Workflow stop result: {}", response.getResult());
		return response;
	}

	/**
	 * Retrieve workflow run status using workflowRunId.
	 *
	 * @param workflowRunId workflow run id
	 * @return workflow run status
	 */
	public WorkflowRunStatusResponse getWorkflowRun(String workflowRunId) throws DifyApiException, IOException {
		Assert.hasText(workflowRunId, "Workflow run id must not be blank");
		WorkflowRunStatusResponse status = workflowClient.getWorkflowRun(workflowRunId);
		log.info("Workflow run status: {}", status.getStatus());
		return status;
	}

	/**
	 * Retrieve workflow logs with optional filters.
	 *
	 * @param appId   dify app id filter, optional
	 * @param userId  dify user id filter, optional
	 * @param page    page index, defaults to 1
	 * @param size    page size, defaults to 10
	 * @return workflow logs
	 */
	public WorkflowLogsResponse getWorkflowLogs(String appId, String userId, Integer page, Integer size) throws DifyApiException, IOException {
		int pageIndex = page == null || page < 1 ? 1 : page;
		int pageSize = size == null || size < 1 ? 10 : size;
		WorkflowLogsResponse logsResponse = workflowClient.getWorkflowLogs(appId, resolveUserId(userId), pageIndex, pageSize);
		log.info("Workflow logs fetched: {}", logsResponse);
		return logsResponse;
	}

	/**
	 * Fetch workflow application metadata.
	 *
	 * @return application info response
	 */
	public Object getAppInfo() throws DifyApiException, IOException {
		Object appInfo = workflowClient.getAppInfo();
		log.info("Workflow app info: {}", appInfo);
		return appInfo;
	}

	/**
	 * Upload a file to dify for subsequent workflow invocations.
	 *
	 * @param file   local file
	 * @param userId dify user id, optional
	 * @return upload response
	 */
	public FileUploadResponse uploadFile(File file, String userId) throws DifyApiException, IOException {
		Assert.notNull(file, "File must not be null");
		Assert.isTrue(file.exists(), "File does not exist");
		FileUploadResponse response = workflowClient.uploadFile(file, resolveUserId(userId));
		log.info("Uploaded file id: {}", response.getId());
		return response;
	}

	/**
	 * Execute a workflow that consumes a file input along with a query.
	 *
	 * @param userId        dify user id, optional
	 * @param query         textual query
	 * @param file          local file to upload
	 * @param fileInputKey  workflow input key for the file list, defaults to {@code file}
	 * @param responseMode  response mode
	 * @return workflow response
	 */
	public WorkflowRunResponse runWorkflowWithFile(String userId, String query, File file, String fileInputKey, ResponseMode responseMode) throws DifyApiException, IOException {
		Assert.hasText(query, "Query must not be blank");
		FileUploadResponse uploadResponse = uploadFile(file, userId);
		FileInfo fileInfo = FileInfo.builder()
			.type(FileType.DOCUMENT)
			.transferMethod(FileTransferMethod.LOCAL_FILE)
			.uploadFileId(uploadResponse.getId())
			.build();
		Map<String, Object> inputs = new HashMap<>();
		inputs.put("query", query);
		String inputKey = (fileInputKey == null || fileInputKey.isBlank()) ? "file" : fileInputKey;
		inputs.put(inputKey, Collections.singletonList(fileInfo));
		return runWorkflow(userId, inputs, responseMode);
	}

	private WorkflowRunRequest buildRequest(String userId, Map<String, Object> inputs, ResponseMode responseMode) {
		return WorkflowRunRequest.builder()
			.inputs(inputs)
			.responseMode(responseMode)
			.user(resolveUserId(userId))
			.build();
	}

	private WorkflowStreamCallback createLoggingCallback(CountDownLatch latch, StringBuilder outputBuilder) {
		return new WorkflowStreamCallback() {
			@Override
			public void onWorkflowStarted(WorkflowStartedEvent event) {
				log.info("Workflow started: {}", event);
			}

			@Override
			public void onNodeStarted(NodeStartedEvent event) {
				log.info("Node started: {}", event);
			}

			@Override
			public void onNodeFinished(NodeFinishedEvent event) {
				log.info("Node finished: {}", event);
				String eventText = event.toString();
				if (eventText.contains("output")) {
					outputBuilder.append(eventText).append('\n');
				}
			}

			@Override
			public void onWorkflowFinished(WorkflowFinishedEvent event) {
				log.info("Workflow finished: {}", event);
				latch.countDown();
			}

			@Override
			public void onWorkflowTextChunk(WorkflowTextChunkEvent event) {
				log.info("Workflow text chunk: {}", event);
			}

			@Override
			public void onTtsMessage(TtsMessageEvent event) {
				log.info("Workflow TTS chunk: {}", event);
			}

			@Override
			public void onTtsMessageEnd(TtsMessageEndEvent event) {
				log.info("Workflow TTS finished: {}", event);
			}

			@Override
			public void onError(ErrorEvent event) {
				log.error("Workflow stream error: {}", event);
			}

			@Override
			public void onPing(PingEvent event) {
				log.debug("Workflow stream ping: {}", event);
			}

			@Override
			public void onException(Throwable throwable) {
				log.error("Workflow stream exception: {}", throwable.getMessage(), throwable);
				latch.countDown();
			}
		};
	}

	private void logWorkflowOutputs(WorkflowRunResponse response) {
		if (response == null || response.getData() == null || response.getData().getOutputs() == null) {
			return;
		}
		for (Map.Entry<String, Object> entry : response.getData().getOutputs().entrySet()) {
			log.info("Workflow output {}: {}", entry.getKey(), entry.getValue());
		}
	}

	private String resolveUserId(String userId) {
		if (userId == null || userId.isBlank()) {
			return DEFAULT_TEST_USER_ID;
		}
		return userId;
	}
}
