package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.vlstream.pojo.dto.CameraApplyApproveDTO;
import org.springblade.vlstream.pojo.dto.CameraApplyCompleteDTO;
import org.springblade.vlstream.pojo.dto.CameraApplyQueryDTO;
import org.springblade.vlstream.pojo.dto.CameraApplyRejectDTO;
import org.springblade.vlstream.pojo.dto.CameraApplySubmitDTO;
import org.springblade.vlstream.pojo.entity.CameraApplyRecord;
import org.springblade.vlstream.pojo.vo.CameraApplyRecordVO;

/**
 * 摄像头申请审批记录 服务类
 */
public interface IVlsCameraApplyRecordService extends BaseService<CameraApplyRecord> {

	Boolean submit(CameraApplySubmitDTO cameraApplySubmitDTO);

	Boolean approve(CameraApplyApproveDTO cameraApplyApproveDTO);

	Boolean reject(CameraApplyRejectDTO cameraApplyRejectDTO);

	Boolean complete(CameraApplyCompleteDTO cameraApplyCompleteDTO);

	IPage<CameraApplyRecordVO> page(IPage<CameraApplyRecordVO> page, CameraApplyQueryDTO cameraApplyQueryDTO);
}
