package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.system.mapper.RecordDataMapper;
import org.springblade.modules.system.pojo.entity.RecordData;
import org.springblade.modules.system.service.IRecordDataService;
import org.springframework.stereotype.Service;

/**
 * Data audit table Service implementation class
 *
 * @author Oort
 */
@Service
public class RecordDataServiceImpl extends ServiceImpl<RecordDataMapper, RecordData> implements IRecordDataService {

}
