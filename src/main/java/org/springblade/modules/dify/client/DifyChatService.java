package org.springblade.modules.dify.client;

import io.github.imfangs.dify.client.DifyChatClient;
import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.callback.ChatStreamCallback;
import io.github.imfangs.dify.client.enums.ResponseMode;
import io.github.imfangs.dify.client.event.AgentMessageEvent;
import io.github.imfangs.dify.client.event.AgentThoughtEvent;
import io.github.imfangs.dify.client.event.ErrorEvent;
import io.github.imfangs.dify.client.event.MessageEndEvent;
import io.github.imfangs.dify.client.event.MessageEvent;
import io.github.imfangs.dify.client.event.MessageFileEvent;
import io.github.imfangs.dify.client.event.MessageReplaceEvent;
import io.github.imfangs.dify.client.event.PingEvent;
import io.github.imfangs.dify.client.event.TtsMessageEndEvent;
import io.github.imfangs.dify.client.event.TtsMessageEvent;
import io.github.imfangs.dify.client.exception.DifyApiException;
import io.github.imfangs.dify.client.model.chat.AppInfoResponse;
import io.github.imfangs.dify.client.model.chat.AppParametersResponse;
import io.github.imfangs.dify.client.model.chat.AudioToTextResponse;
import io.github.imfangs.dify.client.model.chat.ChatMessage;
import io.github.imfangs.dify.client.model.chat.ChatMessageResponse;
import io.github.imfangs.dify.client.model.chat.Conversation;
import io.github.imfangs.dify.client.model.chat.ConversationListResponse;
import io.github.imfangs.dify.client.model.chat.MessageListResponse;
import io.github.imfangs.dify.client.model.chat.SuggestedQuestionsResponse;
import io.github.imfangs.dify.client.model.chat.VariableResponse;
import io.github.imfangs.dify.client.model.common.Metadata;
import io.github.imfangs.dify.client.model.common.RetrieverResource;
import io.github.imfangs.dify.client.model.common.SimpleResponse;
import io.github.imfangs.dify.client.model.common.Usage;
import io.github.imfangs.dify.client.model.file.FileInfo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springblade.modules.dify.config.DifyProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Dify chat client facade that exposes the required parameters instead of hard coded test data.
 */
@Slf4j
@Component
public class DifyChatService {

	private static final String DEFAULT_TEST_USER_ID = "test-user-" + System.currentTimeMillis();

	@Resource
	private DifyProperties difyProperties;

	private DifyChatClient chatClient;

	@PostConstruct
	public void initClient() {
		this.chatClient = DifyClientFactory.createChatClient(difyProperties.getBaseUrl(), difyProperties.getChatApi());
	}

	public ChatMessageResponse sendChatMessage(String userId, String query, ResponseMode responseMode, List<FileInfo> attachments) throws Exception {
		Assert.hasText(query, "Chat query must not be blank");
		ChatMessage message = new ChatMessage();
		message.setQuery(query);
		message.setUser(resolveUserId(userId));
		message.setResponseMode(responseMode == null ? ResponseMode.BLOCKING : responseMode);
		if (attachments != null && !attachments.isEmpty()) {
			message.setFiles(attachments);
		}
		ChatMessageResponse response = chatClient.sendChatMessage(message);
		logChatResponse(response);
		return response;
	}

	public void sendChatMessageStream(String userId, String query, long awaitSeconds) throws Exception {
		Assert.hasText(query, "Chat query must not be blank");
		long waitSeconds = awaitSeconds > 0 ? awaitSeconds : 30L;
		ChatMessage message = new ChatMessage();
		message.setQuery(query);
		message.setUser(resolveUserId(userId));
		message.setResponseMode(ResponseMode.STREAMING);

		CountDownLatch latch = new CountDownLatch(1);
		StringBuilder responseBuilder = new StringBuilder();
		AtomicReference<String> messageIdHolder = new AtomicReference<>();

		chatClient.sendChatMessageStream(message, new ChatStreamCallback() {
			@Override
			public void onMessage(MessageEvent event) {
				log.info("Streaming chunk: {}", event.getAnswer());
				responseBuilder.append(event.getAnswer());
			}

			@Override
			public void onMessageEnd(MessageEndEvent event) {
				log.info("Streaming ended. messageId={}, conversationId={}", event.getMessageId(), event.getConversationId());
				messageIdHolder.set(event.getMessageId());
				latch.countDown();
			}

			@Override
			public void onMessageFile(MessageFileEvent event) {
				log.info("Streaming file event: {}", event);
			}

			@Override
			public void onTTSMessage(TtsMessageEvent event) {
				log.info("Streaming TTS chunk: {}", event);
			}

			@Override
			public void onTTSMessageEnd(TtsMessageEndEvent event) {
				log.info("Streaming TTS finished: {}", event);
			}

			@Override
			public void onMessageReplace(MessageReplaceEvent event) {
				log.info("Streaming message replaced: {}", event);
			}

			@Override
			public void onAgentMessage(AgentMessageEvent event) {
				log.info("Agent message: {}", event);
			}

			@Override
			public void onAgentThought(AgentThoughtEvent event) {
				log.info("Agent thought: {}", event);
			}

			@Override
			public void onError(ErrorEvent event) {
				log.error("Streaming error: {}", event.getMessage());
				latch.countDown();
			}

			@Override
			public void onException(Throwable throwable) {
				log.error("Streaming exception: {}", throwable.getMessage(), throwable);
				latch.countDown();
			}

			@Override
			public void onPing(PingEvent event) {
				log.debug("Streaming ping: {}", event);
			}
		});

		boolean completed = latch.await(waitSeconds, TimeUnit.SECONDS);
		if (!completed) {
			log.error("Streaming chat message did not finish within {} seconds", waitSeconds);
		}
		if (responseBuilder.isEmpty()) {
			log.warn("Streaming chat message returned empty content");
		} else {
			log.info("Streaming response: {}", responseBuilder);
		}
		log.info("Streaming message id: {}", messageIdHolder.get());
	}

	public MessageListResponse getMessages(String conversationId, String userId, String lastMessageId, Integer limit) throws DifyApiException, IOException {
		Assert.hasText(conversationId, "Conversation id must not be blank");
		int pageSize = limit == null ? 10 : limit;
		MessageListResponse messages = chatClient.getMessages(conversationId, resolveUserId(userId), lastMessageId, pageSize);
		log.info("Conversation {} returned {} messages", conversationId, messages.getData().size());
		return messages;
	}

	public ConversationListResponse getConversations(String userId, String lastConversationId, Integer limit, String orderBy) throws DifyApiException, IOException {
		ConversationListResponse conversations = chatClient.getConversations(resolveUserId(userId), lastConversationId, limit, orderBy);
		log.info("User {} returned {} conversations", resolveUserId(userId), conversations.getData().size());
		return conversations;
	}

	public Conversation renameConversation(String conversationId, String newName, boolean autoGenerateName, String userId) throws DifyApiException, IOException {
		Assert.hasText(conversationId, "Conversation id must not be blank");
		Assert.hasText(newName, "Conversation name must not be blank");
		Conversation renamedConversation = chatClient.renameConversation(conversationId, newName, autoGenerateName, resolveUserId(userId));
		log.info("Conversation {} renamed to {}", conversationId, renamedConversation.getName());
		return renamedConversation;
	}

	public SimpleResponse deleteConversation(String conversationId, String userId) throws DifyApiException, IOException {
		Assert.hasText(conversationId, "Conversation id must not be blank");
		SimpleResponse response = chatClient.deleteConversation(conversationId, resolveUserId(userId));
		log.info("Conversation {} delete result: {}", conversationId, response.getResult());
		return response;
	}

	public SimpleResponse feedbackMessage(String messageId, String rating, String userId, String comment) throws DifyApiException, IOException {
		Assert.hasText(messageId, "Message id must not be blank");
		Assert.hasText(rating, "Rating must not be blank");
		SimpleResponse response = chatClient.feedbackMessage(messageId, rating, resolveUserId(userId), comment);
		log.info("Message {} feedback result: {}", messageId, response.getResult());
		return response;
	}

	public SuggestedQuestionsResponse getSuggestedQuestions(String messageId, String userId) throws DifyApiException, IOException {
		Assert.hasText(messageId, "Message id must not be blank");
		SuggestedQuestionsResponse questions = chatClient.getSuggestedQuestions(messageId, resolveUserId(userId));
		log.info("Message {} suggested {} follow-up questions", messageId, questions.getData().size());
		return questions;
	}

	public AudioToTextResponse audioToText(File audioFile, String userId) throws DifyApiException, IOException {
		Assert.notNull(audioFile, "Audio file must not be null");
		Assert.isTrue(audioFile.exists(), "Audio file does not exist");
		AudioToTextResponse response = chatClient.audioToText(audioFile, resolveUserId(userId));
		log.info("Audio to text result: {}", response.getText());
		return response;
	}

	public byte[] textToAudio(String voice, String text, String userId) throws DifyApiException, IOException {
		Assert.hasText(text, "Text must not be blank");
		byte[] audioData = chatClient.textToAudio(voice, text, resolveUserId(userId));
		log.info("Generated audio bytes: {}", audioData.length);
		return audioData;
	}

	public AppInfoResponse getAppInfo() throws DifyApiException, IOException {
		AppInfoResponse appInfo = chatClient.getAppInfo();
		log.info("App name: {}, description: {}", appInfo.getName(), appInfo.getDescription());
		return appInfo;
	}

	public AppParametersResponse getAppParameters() throws DifyApiException, IOException {
		AppParametersResponse parameters = chatClient.getAppParameters();
		log.info("App opening statement: {}", parameters.getOpeningStatement());
		if (parameters.getSuggestedQuestions() != null) {
			log.info("Suggested question count: {}", parameters.getSuggestedQuestions().size());
		}
		return parameters;
	}

	public VariableResponse getConversationVariables(String conversationId, String userId, String lastId, Integer limit, String firstId) throws DifyApiException, IOException {
		Assert.hasText(conversationId, "Conversation id must not be blank");
		return chatClient.getConversationVariables(conversationId, resolveUserId(userId), lastId, limit, firstId);
	}

	private void logChatResponse(ChatMessageResponse response) {
		log.info("Chat answer: {}", response.getAnswer());
		Metadata metadata = response.getMetadata();
		if (metadata == null) {
			return;
		}
		Usage usage = metadata.getUsage();
		if (usage != null) {
			log.info("Total tokens: {}", usage.getTotalTokens());
			log.info("Total price: {}", usage.getTotalPrice());
		}
		List<RetrieverResource> resources = metadata.getRetrieverResources();
		if (resources == null || resources.isEmpty()) {
			return;
		}
		for (RetrieverResource resource : resources) {
			log.info("Dataset: {}", resource.getDatasetName());
			log.info("Document: {}", resource.getDocumentName());
			log.info("Content: {}", resource.getContent());
		}
	}

	private String resolveUserId(String userId) {
		if (userId == null || userId.isBlank()) {
			return DEFAULT_TEST_USER_ID;
		}
		return userId;
	}
}
