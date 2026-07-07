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
 * 租户用户构建
 *
 * @author Chill
 */
@LiteRuleComponent(id = TENANT_USER_RULE, name = "租户用户构建")
public class TenantUserRule extends RuleComponent {
	@Override
	public void process() {
		// 获取上下文
		TenantContext contextBean = this.getContextBean(TenantContext.class);
		Tenant tenant = contextBean.getTenant();

		// 新建租户对应的默认管理用户
		User user = new User();
		user.setTenantId(tenant.getTenantId());
		user.setName("admin");
		user.setRealName("admin");
		user.setAccount("admin");
		// 获取参数配置的密码
		String password = Func.toStr(ParamCache.getValue(PASSWORD_KEY), DEFAULT_PASSWORD);
		user.setPassword(password);
		user.setBirthday(new Date());
		user.setSex(1);
		user.setUserType(UserType.WEB.getCategory());
		user.setIsDeleted(BladeConstant.DB_NOT_DELETED);

		// 设置上下文
		contextBean.setUser(user);
	}
}
