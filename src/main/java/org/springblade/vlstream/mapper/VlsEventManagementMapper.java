package org.springblade.vlstream.mapper;

import org.springblade.vlstream.pojo.entity.EventManagement;
import org.springblade.vlstream.pojo.vo.EventManagementVO;
import org.springblade.vlstream.excel.VlsEventManagementExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 事件管理表 Mapper 接口
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsEventManagementMapper extends BaseMapper<EventManagement> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param vlsEventManagement 查询参数
	 * @return List<VlsEventManagementVO>
	 */
	List<EventManagementVO> selectVlsEventManagementPage(IPage page, EventManagementVO vlsEventManagement);

	/**
	 * 获取导出数据
	 *
	 * @param queryWrapper 查询条件
	 * @return List<VlsEventManagementExcel>
	 */
	List<VlsEventManagementExcel> exportVlsEventManagement(@Param("ew") Wrapper<EventManagement> queryWrapper);

}
