package org.springblade.common.cache;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.db.dynamic.utils.DataSourceUtil;
import org.springblade.core.jwt.enums.TenantType;
import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.mp.enums.StatusType;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.system.pojo.entity.*;
import org.springblade.modules.system.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * System cache
 *
 * @author Chill
 */
public class SysCache {
	private static final String MENU_ID = "menu:id:";
	private static final String DEPT_ID = "dept:id:";
	private static final String DEPT_NAME = "dept:name:";
	private static final String DEPT_NAME_FUZZY = "dept:nameFuzzy:";
	private static final String DEPT_NAME_ID = "deptName:id:";
	private static final String DEPT_NAMES_ID = "deptNames:id:";
	private static final String DEPT_CHILD_ID = "deptChild:id:";
	private static final String DEPT_CHILDIDS_ID = "deptChildIds:id:";
	private static final String POST_ID = "post:id:";
	private static final String POST_NAME = "post:name:";
	private static final String POST_NAME_FUZZY = "post:nameFuzzy:";
	private static final String POST_NAME_ID = "postName:id:";
	private static final String POST_NAMES_ID = "postNames:id:";
	private static final String ROLE_ID = "role:id:";
	private static final String ROLE_NAME = "role:name:";
	private static final String ROLE_NAME_ID = "roleName:id:";
	private static final String ROLE_NAMES_ID = "roleNames:id:";
	private static final String ROLE_ALIAS_ID = "roleAlias:id:";
	private static final String ROLE_ALIASES_ID = "roleAliases:id:";
	public static final String TENANT_ID = "tenant:id:";
	public static final String TENANT_TENANT_ID = "tenant:tenantId:";
	public static final String TENANT_PACKAGE_ID = "tenant:packageId:";
    private static final String DS_NAME = "apaasUser";
	private static final IMenuService menuService;
	private static final IApDeptService apDeptService;
	private static final IOortDeptService oortDeptService;
	private static final IPostService postService;
	private static final IRoleService roleService;
	private static final ITenantService tenantService;
	private static final ITenantPackageService tenantPackageService;
	private static JwtProperties jwtProperties;

	static {
		menuService = SpringUtil.getBean(IMenuService.class);
		apDeptService = SpringUtil.getBean(IApDeptService.class);
		oortDeptService = SpringUtil.getBean(IOortDeptService.class);
		postService = SpringUtil.getBean(IPostService.class);
		roleService = SpringUtil.getBean(IRoleService.class);
		tenantService = SpringUtil.getBean(ITenantService.class);
		tenantPackageService = SpringUtil.getBean(ITenantPackageService.class);
	}

	/**
	 * Get configuration class
	 *
	 * @return jwtProperties
	 */
	private static JwtProperties getJwtProperties() {
		if (jwtProperties == null) {
			jwtProperties = SpringUtil.getBean(JwtProperties.class);
		}
		return jwtProperties;
	}

	/**
	 * Get menu
	 *
	 * @param id primary key
	 * @return menu
	 */
	public static Menu getMenu(Long id) {
		return CacheUtil.get(SYS_CACHE, MENU_ID, id, () -> menuService.getById(id));
	}

	/**
	 * Get departmentid
	 *
	 * @param tenantId  tenantid
	 * @param deptNames Department name
	 * @return departmentid
	 */
	public static String getDeptIds(String tenantId, String deptNames) {
		if (getJwtProperties().getTenantType().equals(TenantType.MULTI_TENANT.getType())) {
			return CacheUtil.get(SYS_CACHE, DEPT_NAME, tenantId + StringPool.DASH + deptNames, () ->
				DataSourceUtil.use(DS_NAME, () ->
					apDeptService.getDeptIds(tenantId, deptNames)
				));
		} else {
			return CacheUtil.get(SYS_CACHE, DEPT_NAME, tenantId + StringPool.DASH + deptNames, () -> oortDeptService.getDeptIds(tenantId, deptNames));
		}
	}

	/**
	 * Get departmentid
	 *
	 * @param tenantId  tenantid
	 * @param deptNames Department name fuzzy query
	 * @return departmentid
	 */
	public static String getDeptIdsByFuzzy(String tenantId, String deptNames) {
		if (getJwtProperties().getTenantType().equals(TenantType.MULTI_TENANT.getType())) {
			return CacheUtil.get(SYS_CACHE, DEPT_NAME_FUZZY, tenantId + StringPool.DASH + deptNames, () -> apDeptService.getDeptIdsByFuzzy(tenantId, deptNames));
		} else {
			return CacheUtil.get(SYS_CACHE, DEPT_NAME_FUZZY, tenantId + StringPool.DASH + deptNames, () -> oortDeptService.getDeptIdsByFuzzy(tenantId, deptNames));
		}
	}

	/**
	 * Get department name
	 *
	 * @param id primary key
	 * @return Department name
	 */
	public static String getDeptName(String id) {
		if (getJwtProperties().getTenantType().equals(TenantType.MULTI_TENANT.getType())) {
			return CacheUtil.get(SYS_CACHE, DEPT_NAME_ID, id, () ->
				DataSourceUtil.use(DS_NAME, () ->
					apDeptService.getById(id).getDeptName()
				));
		} else {
			return CacheUtil.get(SYS_CACHE, DEPT_NAME_ID, id, () ->
				DataSourceUtil.use(DS_NAME, () ->
					oortDeptService.getById(id).getOortDname()
				));
		}
	}


	/**
	 * Get the department name collection
	 *
	 * @param deptIds primary key set
	 * @return Department name
	 */
	public static List<String> getDeptNames(String deptIds) {
		if (getJwtProperties().getTenantType().equals(TenantType.MULTI_TENANT.getType())) {
			return CacheUtil.get(SYS_CACHE, DEPT_NAMES_ID, deptIds, () ->
				DataSourceUtil.use(DS_NAME, () ->
					apDeptService.getDeptNames(deptIds)
				));
		} else {
			return CacheUtil.get(SYS_CACHE, DEPT_NAMES_ID, deptIds, () ->
				DataSourceUtil.use(DS_NAME, () ->
					oortDeptService.getDeptNames(deptIds)
				));
		}
	}

	/**
	 * Get subdepartmentIDgather
	 *
	 * @param deptId primary key
	 * @return subdepartmentID
	 */
	public static List<String> getDeptChildIds(String deptId) {
		if (deptId == null) {
			return null;
		}
		List<String> deptIdList = CacheUtil.get(SYS_CACHE, DEPT_CHILDIDS_ID, deptId, List.class);
		if (deptIdList == null) {
			deptIdList = new ArrayList<>();

			if (getJwtProperties().getTenantType().equals(TenantType.MULTI_TENANT.getType())) {
				List<ApDeptEntity> deptChild = DataSourceUtil.use(DS_NAME, () ->
					apDeptService.getDeptChild(deptId)
				);
				if (deptChild != null) {
					List<String> collect = deptChild.stream().map(ApDeptEntity::getDeptId).toList();
					deptIdList.addAll(collect);
				}
				deptIdList.add(deptId);
			} else {
				List<OortDeptEntity> deptChild = DataSourceUtil.use(DS_NAME, () ->
					oortDeptService.getDeptChild(deptId)
				);
				if (deptChild != null) {
					List<String> collect = deptChild.stream().map(OortDeptEntity::getOortUdid).toList();
					deptIdList.addAll(collect);
				}
				deptIdList.add(deptId);
			}
			CacheUtil.put(SYS_CACHE, DEPT_CHILDIDS_ID, deptId, deptIdList);
		}
		return deptIdList;
	}

	/**
	 * Get a job
	 *
	 * @param id primary key
	 * @return
	 */
	public static Post getPost(Long id) {
		return CacheUtil.get(SYS_CACHE, POST_ID, id, () -> postService.getById(id));
	}

	/**
	 * Get a jobid
	 *
	 * @param tenantId  tenantid
	 * @param postNames Position name
	 * @return
	 */
	public static String getPostIds(String tenantId, String postNames) {
		return CacheUtil.get(SYS_CACHE, POST_NAME, tenantId + StringPool.DASH + postNames, () -> postService.getPostIds(tenantId, postNames));
	}

	/**
	 * Get a jobid
	 *
	 * @param tenantId  tenantid
	 * @param postNames Position name fuzzy query
	 * @return
	 */
	public static String getPostIdsByFuzzy(String tenantId, String postNames) {
		return CacheUtil.get(SYS_CACHE, POST_NAME_FUZZY, tenantId + StringPool.DASH + postNames, () -> postService.getPostIdsByFuzzy(tenantId, postNames));
	}

	/**
	 * Get job title
	 *
	 * @param id primary key
	 * @return Position name
	 */
	public static String getPostName(Long id) {
		return CacheUtil.get(SYS_CACHE, POST_NAME_ID, id, () -> postService.getById(id).getPostName());
	}

	/**
	 * Get a collection of job titles
	 *
	 * @param postIds primary key set
	 * @return Position name
	 */
	public static List<String> getPostNames(String postIds) {
		return CacheUtil.get(SYS_CACHE, POST_NAMES_ID, postIds, () -> postService.getPostNames(postIds));
	}

	/**
	 * Get role
	 *
	 * @param id primary key
	 * @return Role
	 */
	public static Role getRole(Long id) {
		return CacheUtil.get(SYS_CACHE, ROLE_ID, id, () -> roleService.getById(id));
	}

	/**
	 * Get roleid
	 *
	 * @param tenantId  tenantid
	 * @param roleNames Character name
	 * @return
	 */
	public static String getRoleIds(String tenantId, String roleNames) {
		return CacheUtil.get(SYS_CACHE, ROLE_NAME, tenantId + StringPool.DASH + roleNames, () -> roleService.getRoleIds(tenantId, roleNames));
	}

	/**
	 * Get character name
	 *
	 * @param id primary key
	 * @return Character name
	 */
	public static String getRoleName(Long id) {
		return CacheUtil.get(SYS_CACHE, ROLE_NAME_ID, id, () -> roleService.getById(id).getRoleName());
	}

	/**
	 * Get the character name collection
	 *
	 * @param roleIds primary key set
	 * @return Character name
	 */
	public static List<String> getRoleNames(String roleIds) {
		return CacheUtil.get(SYS_CACHE, ROLE_NAMES_ID, roleIds, () -> roleService.getRoleNames(roleIds));
	}

	/**
	 * Get role alias
	 *
	 * @param id primary key
	 * @return role alias
	 */
	public static String getRoleAlias(Long id) {
		return CacheUtil.get(SYS_CACHE, ROLE_ALIAS_ID, id, () -> roleService.getById(id).getRoleAlias());
	}

	/**
	 * Get the role alias collection
	 *
	 * @param roleIds primary key set
	 * @return role alias
	 */
	public static List<String> getRoleAliases(String roleIds) {
		return CacheUtil.get(SYS_CACHE, ROLE_ALIASES_ID, roleIds, () -> roleService.getRoleAliases(roleIds));
	}

	/**
	 * Get tenants
	 *
	 * @param id primary key
	 * @return Tenant
	 */
	public static Tenant getTenant(Long id) {
		return CacheUtil.get(SYS_CACHE, TENANT_ID, id, () -> Optional.ofNullable(tenantService.getById(id))
			.filter(tenant -> tenant.getStatus() != null)
			.filter(tenant -> tenant.getStatus() == StatusType.ACTIVE.getType())
			.orElse(null), Boolean.FALSE);
	}

	/**
	 * Get tenants
	 *
	 * @param tenantId tenantid
	 * @return Tenant
	 */
	public static Tenant getTenant(String tenantId) {
		return CacheUtil.get(SYS_CACHE, TENANT_TENANT_ID, tenantId, () -> Optional.ofNullable(tenantService.getByTenantId(tenantId))
			.filter(tenant -> tenant.getStatus() != null)
			.filter(tenant -> tenant.getStatus() == StatusType.ACTIVE.getType())
			.orElse(null), Boolean.FALSE);
	}

	/**
	 * Get the tenant product package
	 *
	 * @param tenantId tenantid
	 * @return Tenant
	 */
	public static TenantPackage getTenantPackage(String tenantId) {
		Tenant tenant = getTenant(tenantId);
		return CacheUtil.get(SYS_CACHE, TENANT_PACKAGE_ID, tenantId, () -> tenantPackageService.getById(tenant.getPackageId()), Boolean.FALSE);
	}

}
