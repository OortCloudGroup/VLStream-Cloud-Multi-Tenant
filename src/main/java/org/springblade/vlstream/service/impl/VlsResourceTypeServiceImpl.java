package org.springblade.vlstream.service.impl;

import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.vlstream.mapper.VlsResourceTypeMapper;
import org.springblade.vlstream.pojo.entity.ResourceType;
import org.springblade.vlstream.service.IVlsResourceTypeService;
import org.springframework.stereotype.Service;

/**
 * 资源类型配置表 服务实现类
 */
@Service
public class VlsResourceTypeServiceImpl extends BaseServiceImpl<VlsResourceTypeMapper, ResourceType> implements IVlsResourceTypeService {
}
