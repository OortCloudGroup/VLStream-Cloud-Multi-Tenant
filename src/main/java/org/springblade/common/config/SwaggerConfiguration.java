package org.springblade.common.config;

import lombok.AllArgsConstructor;
import org.springblade.core.launch.constant.AppConstant;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import org.springblade.vlstream.pojo.dto.DeviceTagRelationDTO;
import org.springblade.vlstream.pojo.dto.TagManagementDTO;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SwaggerConfiguration class
 *
 * @author Chill
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@ConditionalOnProperty(value = "swagger.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfiguration {

	private static final Map<Class<?>, Boolean> SELF_REFERENCE_CACHE = new ConcurrentHashMap<>();

	private final SpringDocConfigProperties springDocConfigProperties;

	@PostConstruct
	public void customizeSpringDoc() {
		springDocConfigProperties.setDefaultFlatParamObject(false);
		SpringDocUtils.getConfig()
			.addSimpleTypesForParameterObject(TagManagementDTO.class, DeviceTagRelationDTO.class)
			.addSimpleTypePredicateForParameterObject(SwaggerConfiguration::isSelfReferencingClass);
	}

	@Bean
	public GroupedOpenApi internetServerApi() {
		// Create and returnGroupedOpenApiobject - only ininternet profileeffective next
		return GroupedOpenApi.builder()
			.group("vlstream")
			.packagesToScan(AppConstant.BASE_PACKAGES + ".vlstream")
			.build();
	}

	private static boolean isSelfReferencingClass(Class<?> targetClass) {
		if (targetClass == null) {
			return false;
		}
		return SELF_REFERENCE_CACHE.computeIfAbsent(targetClass, SwaggerConfiguration::containsSelfReference);
	}

	private static boolean containsSelfReference(Class<?> targetClass) {
		Field[] declaredFields = targetClass.getDeclaredFields();
		for (Field declaredField : declaredFields) {
			if (declaredField.getType() == targetClass) {
				return true;
			}
			if (hasSelfReferenceInGenericType(declaredField.getGenericType(), targetClass)) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasSelfReferenceInGenericType(Type genericType, Class<?> targetClass) {
		if (!(genericType instanceof ParameterizedType parameterizedType)) {
			return false;
		}
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		for (Type actualTypeArgument : actualTypeArguments) {
			if (actualTypeArgument == targetClass) {
				return true;
			}
			if (actualTypeArgument instanceof ParameterizedType nestedParameterizedType) {
				Type rawType = nestedParameterizedType.getRawType();
				if (rawType == targetClass) {
					return true;
				}
				if (hasSelfReferenceInGenericType(nestedParameterizedType, targetClass)) {
					return true;
				}
			}
		}
		return false;
	}

}
