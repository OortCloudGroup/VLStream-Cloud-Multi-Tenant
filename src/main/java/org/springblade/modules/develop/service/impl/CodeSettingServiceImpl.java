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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.modules.develop.mapper.CodeSettingMapper;
import org.springblade.modules.develop.pojo.entity.CodeSetting;
import org.springblade.modules.develop.service.ICodeSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Code generator configuration table Service implementation class
 *
 * @author Oort
 */
//@Master
@Service
public class CodeSettingServiceImpl extends ServiceImpl<CodeSettingMapper, CodeSetting> implements ICodeSettingService {
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean enable(Long id) {
		// Disable first
		boolean temp1 = this.update(Wrappers.<CodeSetting>update().lambda().set(CodeSetting::getStatus, BladeConstant.DB_STATUS_1));
		// Enable
		boolean temp2 = this.update(Wrappers.<CodeSetting>update().lambda().set(CodeSetting::getStatus, BladeConstant.DB_STATUS_2).eq(CodeSetting::getId, id));
		return temp1 && temp2;
	}
}
