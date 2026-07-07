package org.springblade.vlstream.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.vlstream.pojo.dto.CameraApplyQueryDTO;
import org.springblade.vlstream.pojo.entity.CameraApplyRecord;
import org.springblade.vlstream.pojo.vo.CameraApplyRecordVO;

import java.util.List;

/**
 * 摄像头申请审批记录 Mapper 接口
 */
public interface VlsCameraApplyRecordMapper extends BaseMapper<CameraApplyRecord> {

	List<CameraApplyRecordVO> selectCameraApplyRecordPage(IPage<CameraApplyRecordVO> page, @Param("query") CameraApplyQueryDTO query);
}
