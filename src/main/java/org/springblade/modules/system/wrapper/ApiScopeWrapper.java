package org.springblade.modules.system.wrapper;

import org.springblade.common.cache.DictCache;
import org.springblade.common.enums.DictEnum;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.pojo.entity.ApiScope;
import org.springblade.modules.system.pojo.vo.ApiScopeVO;

import java.util.Objects;


/**
 * Packaging,Returns the fields required by the view layer
 *
 * @author Chill
 */
public class ApiScopeWrapper extends BaseEntityWrapper<ApiScope, ApiScopeVO> {

	public static ApiScopeWrapper build() {
		return new ApiScopeWrapper();
	}

	@Override
	public ApiScopeVO entityVO(ApiScope dataScope) {
		ApiScopeVO apiScopeVO = Objects.requireNonNull(BeanUtil.copyProperties(dataScope, ApiScopeVO.class));
		String scopeTypeName = DictCache.getValue(DictEnum.API_SCOPE_TYPE, dataScope.getScopeType());
		apiScopeVO.setScopeTypeName(scopeTypeName);
		return apiScopeVO;
	}

}
