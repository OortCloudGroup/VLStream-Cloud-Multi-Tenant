package org.springblade.modules.system.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.converter.SwaggerConverter;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.webmvc.api.MultipleOpenApiWebMvcResource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/swagger")
@RequiredArgsConstructor
public class SwaggerCompatibilityController {

	private static final String AI_PACKAGE_PREFIX = "org.springblade.ai";

	private final ObjectProvider<MultipleOpenApiWebMvcResource> multipleOpenApiWebMvcResourceProvider;
	private final List<GroupedOpenApi> groupedOpenApis;
	private final SwaggerConverter swaggerConverter = new SwaggerConverter();

	@GetMapping(value = "/api-docs.json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> swagger20(HttpServletRequest request) throws JsonProcessingException {
		Locale locale = LocaleContextHolder.getLocale();
		byte[] openApiBytes = readAiGroupOpenApi(request, locale);
		String openApiJson = new String(openApiBytes, StandardCharsets.UTF_8);
		SwaggerParseResult result = swaggerConverter.readContents(openApiJson, null, null);
		OpenAPI swagger20 = result.getOpenAPI();
		if (swagger20 == null) {
			throw new IllegalStateException("Swagger 2.0 document generation failed");
		}
		return ResponseEntity.ok(Json.pretty(swagger20));
	}

	private byte[] readAiGroupOpenApi(HttpServletRequest request, Locale locale) throws JsonProcessingException {
		String aiGroupName = resolveAiGroupName();
		if (aiGroupName == null) {
			throw new IllegalStateException("OpenAPI group for package org.springblade.ai is missing");
		}
		MultipleOpenApiWebMvcResource multipleResource = multipleOpenApiWebMvcResourceProvider.getIfAvailable();
		if (multipleResource == null) {
			throw new IllegalStateException("Springdoc multi-group resource is not available");
		}
		String apiDocsPath = request != null ? request.getRequestURI() : "/swagger/api-docs.json";
		return multipleResource.openapiJson(request, apiDocsPath, aiGroupName, locale);
	}

	private String resolveAiGroupName() {
		return groupedOpenApis.stream()
			.filter(grouped -> grouped.getPackagesToScan() != null)
			.filter(grouped -> grouped.getPackagesToScan().stream()
				.anyMatch(packageName -> packageName != null && packageName.startsWith(AI_PACKAGE_PREFIX)))
			.map(GroupedOpenApi::getGroup)
			.findFirst()
			.orElse(null);
	}

}
