package org.springblade.modules.system.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.pojo.vo.TenantVO;

import java.util.Map;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
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
	 * 查询条件处理
	 */
	public void entityQuery(Map<String, Object> entity) {
		// 此场景仅在 pg数据库 map类型传参的情况下需要处理，entity传参已经包含数据类型，则无需关心
		// 针对 pg数据库 int类型字段查询需要强转的处理示例
		String searchKey = "status_equal";
		if (Func.isNotEmpty(entity.get(searchKey))) {
			// 数据库字段为int类型，设置"="查询，具体查询参数请见 @org.springblade.core.mp.support.SqlKeyword
			entity.put(searchKey, Func.toInt(entity.get(searchKey)));
		}
	}
}
