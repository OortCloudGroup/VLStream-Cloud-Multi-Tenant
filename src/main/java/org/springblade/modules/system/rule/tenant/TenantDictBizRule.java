package org.springblade.modules.system.rule.tenant;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.core.literule.annotation.LiteRuleComponent;
import org.springblade.core.literule.core.RuleComponent;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.modules.system.pojo.entity.DictBiz;
import org.springblade.modules.system.pojo.entity.Tenant;
import org.springblade.modules.system.rule.context.TenantContext;
import org.springblade.modules.system.service.IDictBizService;

import java.util.LinkedList;
import java.util.List;

import static org.springblade.modules.system.rule.constant.TenantRuleConstant.TENANT_DICT_BIZ_RULE;

/**
 * Tenant business dictionary construction
 *
 * @author Chill
 */
@LiteRuleComponent(id = TENANT_DICT_BIZ_RULE, name = "Tenant business dictionary construction")
public class TenantDictBizRule extends RuleComponent {
	@Override
	public void process() {
		// Get context
		TenantContext contextBean = this.getContextBean(TenantContext.class);
		Tenant tenant = contextBean.getTenant();
		IDictBizService dictBizService = contextBean.getDictBizService();

		// Default business dictionary corresponding to the new tenant
		LinkedList<DictBiz> dictBizs = new LinkedList<>();
		List<DictBiz> dictBizList = getDictBizs(dictBizService, tenant.getTenantId(), dictBizs);

		// Set context
		contextBean.setDictBizList(dictBizList);

	}


	private List<DictBiz> getDictBizs(IDictBizService dictBizService, String tenantId, LinkedList<DictBiz> dictBizs) {
		List<DictBiz> dictBizList = dictBizService.list(Wrappers.<DictBiz>query().lambda().eq(DictBiz::getParentId, BladeConstant.TOP_PARENT_ID).eq(DictBiz::getTenantId, BladeConstant.ADMIN_TENANT_ID).eq(DictBiz::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		dictBizList.forEach(dictBiz -> {
			Long oldParentId = dictBiz.getId();
			Long newParentId = IdWorker.getId();
			dictBiz.setId(newParentId);
			dictBiz.setTenantId(tenantId);
			dictBizs.add(dictBiz);
			recursionDictBiz(dictBizService, tenantId, oldParentId, newParentId, dictBizs);
		});
		return dictBizs;
	}

	private void recursionDictBiz(IDictBizService dictBizService, String tenantId, Long oldParentId, Long newParentId, LinkedList<DictBiz> dictBizs) {
		List<DictBiz> dictBizList = dictBizService.list(Wrappers.<DictBiz>query().lambda().eq(DictBiz::getParentId, oldParentId).eq(DictBiz::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		dictBizList.forEach(dictBiz -> {
			Long oldSubParentId = dictBiz.getId();
			Long newSubParentId = IdWorker.getId();
			dictBiz.setId(newSubParentId);
			dictBiz.setTenantId(tenantId);
			dictBiz.setParentId(newParentId);
			dictBizs.add(dictBiz);
			recursionDictBiz(dictBizService, tenantId, oldSubParentId, newSubParentId, dictBizs);
		});
	}

}
