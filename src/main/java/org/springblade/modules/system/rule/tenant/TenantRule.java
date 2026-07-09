package org.springblade.modules.system.rule.tenant;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.common.cache.ParamCache;
import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.core.tenant.TenantId;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.rule.context.TenantContext;
import org.springblade.modules.system.service.ITenantService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springblade.common.constant.TenantConstant.ACCOUNT_NUMBER_KEY;
import static org.springblade.common.constant.TenantConstant.DEFAULT_ACCOUNT_NUMBER;
import static org.springblade.modules.system.rule.constant.TenantRuleConstant.TENANT_RULE;

/**
 * Tenant build
 *
 * @author Chill
 */
@LiteRuleComponent(id = TENANT_RULE, name = "Tenant build")
public class TenantRule extends RuleComponent {
	@Override
	public void process() {
		// Get context
		TenantContext contextBean = this.getContextBean(TenantContext.class);
		Tenant tenant = contextBean.getTenant();
		TenantId tenantIdGenerator = contextBean.getTenantIdGenerator();
		ITenantService tenantService = contextBean.getTenantService();

		// Get tenantsID
		List<Tenant> tenants = tenantService.list(Wrappers.<Tenant>query().lambda().eq(Tenant::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		List<String> codes = tenants.stream().map(Tenant::getTenantId).collect(Collectors.toList());
		String tenantId = getTenantId(tenantIdGenerator, codes);
		tenant.setTenantId(tenantId);
		// Get the account quota configured by parameters
		int accountNumber = Func.toInt(ParamCache.getValue(ACCOUNT_NUMBER_KEY), DEFAULT_ACCOUNT_NUMBER);
		tenant.setAccountNumber(accountNumber);

		// Set context
		contextBean.setTenant(tenant);

	}

	private String getTenantId(TenantId tenantIdGenerator, List<String> codes) {
		String code = tenantIdGenerator.generate();
		if (codes.contains(code)) {
			return getTenantId(tenantIdGenerator, codes);
		}
		return code;
	}
}
