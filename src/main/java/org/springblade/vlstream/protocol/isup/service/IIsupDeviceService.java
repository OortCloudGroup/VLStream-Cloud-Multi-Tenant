package org.springblade.vlstream.protocol.isup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.vlstream.protocol.isup.entity.IsupDeviceEntity;

public interface IIsupDeviceService extends IService<IsupDeviceEntity> {

	IsupDeviceEntity selectByLuserIdAndIp(Integer luserId, String ipAddress);

	boolean updateStatusByLuserId(Integer luserId, String ipAddress, String status);

	boolean saveWithUrl(IsupDeviceEntity entity);

	boolean updateWithUrl(IsupDeviceEntity entity);
}
