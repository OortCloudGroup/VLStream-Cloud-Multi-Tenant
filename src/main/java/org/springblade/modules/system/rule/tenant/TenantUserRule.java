package org.springblade.modules.system.rule.tenant;

import org.springblade.common.cache.ParamCache;
import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.auth.provider.UserType;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.pojo.entity.User;
import org.springblade.modules.system.rule.context.TenantContext;

import java.util.Date;

import static org.springblade.common.constant.TenantConstant.DEFAULT_PASSWORD;
import static org.springblade.common.constant.TenantConstant.PASSWORD_KEY;
import static org.springblade.modules.system.rule.constant.TenantRuleConstant.TENANT_USER_RULE;

/**
 * Tenant user build
 *
 * @author Chill
 */
@LiteRuleComponent(id = TENANT_USER_RULE, name = "Tenant user build")
public class TenantUserRule extends RuleComponent {
	@Override
	public void process() {
		// Get context
		TenantContext contextBean = this.getContextBean(TenantContext.class);
		Tenant tenant = contextBean.getTenant();

		// Default management user corresponding to the new tenant
		User user = new User();
		user.setTenantId(tenant.getTenantId());
		user.setName("admin");
		user.setRealName("admin");
		user.setAccount("admin");
		// Get the password for parameter configuration
		String password = Func.toStr(ParamCache.getValue(PASSWORD_KEY), DEFAULT_PASSWORD);
		user.setPassword(password);
		user.setBirthday(new Date());
		user.setSex(1);
		user.setUserType(UserType.WEB.getCategory());
		user.setIsDeleted(BladeConstant.DB_NOT_DELETED);

		// Set context
		contextBean.setUser(user);
	}
}
