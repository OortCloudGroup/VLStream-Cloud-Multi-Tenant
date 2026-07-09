package org.springblade.vlstream.service.impl;

import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.vlstream.mapper.VlsResourceSpecMapper;
import org.springblade.vlstream.pojo.entity.ResourceSpec;
import org.springblade.vlstream.service.IVlsResourceSpecService;
import org.springframework.stereotype.Service;

/**
 * Resource specification configuration table Service implementation class
 */
@Service
public class VlsResourceSpecServiceImpl extends BaseServiceImpl<VlsResourceSpecMapper, ResourceSpec> implements IVlsResourceSpecService {
}
