package org.springblade.modules.system.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.pojo.vo.TenantVO;

import java.util.Map;
import java.util.Objects;

/**
 * Packaging,Returns the fields required by the view layer
 *
 * @author Chill
 */
public class TenantWrapper extends BaseEntityWrapper<Tenant, TenantVO> {

	public static TenantWrapper build() {
		return new TenantWrapper();
	}

	@Override
	public TenantVO entityVO(Tenant tenant) {
		return Objects.requireNonNull(BeanUtil.copyProperties(tenant, TenantVO.class));
	}

	/**
	 * Query condition processing
	 */
	public void entityQuery(Map<String, Object> entity) {
		// This scenario is only available in pgdatabase mapNeed to be processed when type parameters are passed, entityThe passed parameter already contains the data type, no need to care
		// against pgdatabase intExample of processing when type field query requires forced transfer
		String searchKey = "status_equal";
		if (Func.isNotEmpty(entity.get(searchKey))) {
			// The database fields areinttype, set up"="Query, For specific query parameters, please see @org.springblade.core.mp.support.SqlKeyword
			entity.put(searchKey, Func.toInt(entity.get(searchKey)));
		}
	}
}
