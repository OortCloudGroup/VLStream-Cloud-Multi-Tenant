package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.modules.system.pojo.entity.Param;
import org.springblade.modules.system.mapper.ParamMapper;
import org.springblade.modules.system.service.IParamService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Chill
 */
//@Master
@Service
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements IParamService {

	@Override
	public String getValue(String paramKey) {
		Param param = this.getOne(Wrappers.<Param>query().lambda().eq(Param::getParamKey, paramKey));
		return param.getParamValue();
	}

}
