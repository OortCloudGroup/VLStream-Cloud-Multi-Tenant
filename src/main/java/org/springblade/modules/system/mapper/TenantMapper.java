package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.system.pojo.entity.Tenant;

import java.util.List;

/**
 *  Mapper interface
 *
 * @author Chill
 */
public interface TenantMapper extends BaseMapper<Tenant> {

	/**
	 * Custom paging
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	List<Tenant> selectTenantPage(IPage page, Tenant tenant);

}
