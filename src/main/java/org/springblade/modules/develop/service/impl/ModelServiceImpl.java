/**
 * BladeX Commercial License Agreement
 * Copyright (c) 2018-2099, https://bladex.cn. All rights reserved.
 * <p>
 * Use of this software is governed by the Commercial License Agreement
 * obtained after purchasing a license from BladeX.
 * <p>
 * 1. This software is for development use only under a valid license
 * from BladeX.
 * <p>
 * 2. Redistribution of this software's source code to any third party
 * without a commercial license is strictly prohibited.
 * <p>
 * 3. Licensees may copyright their own code but cannot use segments
 * from this software for such purposes. Copyright of this software
 * remains with BladeX.
 * <p>
 * Using this software signifies agreement to this License, and the software
 * must not be used for illegal purposes.
 * <p>
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY. The author is
 * not liable for any claims arising from secondary or illegal development.
 * <p>
 * Author: Chill Zhuang (bladejava@qq.com)
 */
package org.springblade.modules.develop.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.modules.develop.mapper.ModelMapper;
import org.springblade.modules.develop.pojo.entity.Code;
import org.springblade.modules.develop.pojo.entity.Model;
import org.springblade.modules.develop.pojo.entity.ModelPrototype;
import org.springblade.modules.develop.service.ICodeService;
import org.springblade.modules.develop.service.IModelPrototypeService;
import org.springblade.modules.develop.service.IModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据模型表 服务实现类
 *
 * @author Chill
 */
//@Master
@Service
@RequiredArgsConstructor
public class ModelServiceImpl extends BaseServiceImpl<ModelMapper, Model> implements IModelService {

	private final IModelPrototypeService modelPrototypeService;
	private final ICodeService codeService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(List<Long> ids) {
		boolean modelTemp = this.deleteLogic(ids);
		if (modelTemp) {
			if (modelPrototypeService.count(Wrappers.<ModelPrototype>lambdaQuery().in(ModelPrototype::getModelId, ids)) > 0) {
				boolean prototypeTemp = modelPrototypeService.remove(Wrappers.<ModelPrototype>lambdaQuery().in(ModelPrototype::getModelId, ids));
				if (!prototypeTemp) {
					throw new ServiceException("删除数据模型成功，关联数据原型删除失败");
				}
			}
			if (codeService.count(Wrappers.<Code>lambdaQuery().in(Code::getModelId, ids)) > 0) {
				boolean codeTemp = codeService.remove(Wrappers.<Code>lambdaQuery().in(Code::getModelId, ids));
				if (!codeTemp) {
					throw new ServiceException("删除数据模型成功，关联代码生成配置删除失败");
				}
			}
		}
		return true;
	}
}
