package org.springblade.common.handler;

import lombok.RequiredArgsConstructor;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.datascope.constant.DataScopeConstant;
import org.springblade.core.datascope.handler.ScopeModelHandler;
import org.springblade.core.datascope.model.DataScopeModel;
import org.springblade.core.tool.utils.CollectionUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * BladeScopeModelHandler
 *
 * @author Chill
 */
//If the dynamic data source function is enabled, then add@MasterAnnotate the specified permission database as the main database
//@Master
@RequiredArgsConstructor
public class BladeScopeModelHandler implements ScopeModelHandler {

	private static final String SCOPE_CACHE_CODE = "dataScope:code:";
	private static final String SCOPE_CACHE_CLASS = "dataScope:class:";
	private static final String DEPT_CACHE_ANCESTORS = "dept:ancestors:";
	private static final DataScopeModel SEARCHED_DATA_SCOPE_MODEL = new DataScopeModel(Boolean.TRUE);

	private final JdbcTemplate jdbcTemplate;

	/**
	 * Get data permissions
	 *
	 * @param mapperId Data permissionsmapperId
	 * @param roleId   User role collection
	 * @return DataScopeModel
	 */
	@Override
	public DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
		List<Object> args = new ArrayList<>(Collections.singletonList(mapperId));
		List<Long> roleIds = Func.toLongList(roleId);
		args.addAll(roleIds);
		// IncreasesearchedThe field prevents unconfigured parameters from being read from the library repeatedly and causing cache breakdown.
		// If there are new configurations in the future, the cache will be cleared and reloaded.
		DataScopeModel dataScope = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CLASS, mapperId + StringPool.COLON + roleId, DataScopeModel.class, Boolean.FALSE);
		if (dataScope == null || !dataScope.getSearched()) {
			List<DataScopeModel> list = jdbcTemplate.query(DataScopeConstant.dataByMapper(roleIds.size()), new BeanPropertyRowMapper<>(DataScopeModel.class), args.toArray());
			if (CollectionUtil.isNotEmpty(list)) {
				dataScope = list.iterator().next();
				dataScope.setSearched(Boolean.TRUE);
			} else {
				dataScope = SEARCHED_DATA_SCOPE_MODEL;
			}
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CLASS, mapperId + StringPool.COLON + roleId, dataScope, Boolean.FALSE);
		}
		return StringUtil.isNotBlank(dataScope.getResourceCode()) ? dataScope : null;
	}

	/**
	 * Get data permissions
	 *
	 * @param code Data permission resource number
	 * @return DataScopeModel
	 */
	@Override
	public DataScopeModel getDataScopeByCode(String code) {
		DataScopeModel dataScope = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CODE, code, DataScopeModel.class, Boolean.FALSE);
		// IncreasesearchedThe field prevents unconfigured parameters from being read from the library repeatedly and causing cache breakdown.
		// If there are new configurations in the future, the cache will be cleared and reloaded.
		if (dataScope == null || !dataScope.getSearched()) {
			List<DataScopeModel> list = jdbcTemplate.query(DataScopeConstant.DATA_BY_CODE, new BeanPropertyRowMapper<>(DataScopeModel.class), code);
			if (CollectionUtil.isNotEmpty(list)) {
				dataScope = list.iterator().next();
				dataScope.setSearched(Boolean.TRUE);
			} else {
				dataScope = SEARCHED_DATA_SCOPE_MODEL;
			}
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CODE, code, dataScope, Boolean.FALSE);
		}
		return StringUtil.isNotBlank(dataScope.getResourceCode()) ? dataScope : null;
	}

	/**
	 * Get department children
	 *
	 * @param deptId departmentid
	 * @return deptIds
	 */
	@Override
	public List<Long> getDeptAncestors(Long deptId) {
		List ancestors = CacheUtil.get(SYS_CACHE, DEPT_CACHE_ANCESTORS, deptId, List.class);
		if (CollectionUtil.isEmpty(ancestors)) {
			ancestors = jdbcTemplate.queryForList(DataScopeConstant.DATA_BY_DEPT, Long.class, deptId);
			CacheUtil.put(SYS_CACHE, DEPT_CACHE_ANCESTORS, deptId, ancestors);
		}
		return ancestors;
	}
}
