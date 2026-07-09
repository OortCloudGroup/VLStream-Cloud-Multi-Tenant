package org.springblade.modules.system.wrapper;

import org.springblade.common.cache.DictCache;
import org.springblade.common.enums.DictEnum;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.pojo.entity.DataScope;
import org.springblade.modules.system.pojo.vo.DataScopeVO;

import java.util.Objects;


/**
 * Packaging,Returns the fields required by the view layer
 *
 * @author Chill
 */
public class DataScopeWrapper extends BaseEntityWrapper<DataScope, DataScopeVO> {

	public static DataScopeWrapper build() {
		return new DataScopeWrapper();
	}

	@Override
	public DataScopeVO entityVO(DataScope dataScope) {
		DataScopeVO dataScopeVO = Objects.requireNonNull(BeanUtil.copyProperties(dataScope, DataScopeVO.class));
		String scopeTypeName = DictCache.getValue(DictEnum.DATA_SCOPE_TYPE, dataScope.getScopeType());
		dataScopeVO.setScopeTypeName(scopeTypeName);
		return dataScopeVO;
	}

}
