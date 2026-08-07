package org.springblade.vlstream.mapper;

import org.springblade.vlstream.pojo.entity.AlgorithmOrchestration;
import org.springblade.vlstream.pojo.vo.AlgorithmOrchestrationVO;
import org.springblade.vlstream.excel.VlsAlgorithmOrchestrationExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 算法编排表 Mapper 接口
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsAlgorithmOrchestrationMapper extends BaseMapper<AlgorithmOrchestration> {

	/**
	 * 自定义分页
	 *
	 * @param page 分页参数
	 * @param vlsAlgorithmOrchestration 查询参数
	 * @return List<VlsAlgorithmOrchestrationVO>
	 */
	List<AlgorithmOrchestrationVO> selectVlsAlgorithmOrchestrationPage(IPage page, AlgorithmOrchestrationVO vlsAlgorithmOrchestration);

	/**
	 * 获取导出数据
	 *
	 * @param queryWrapper 查询条件
	 * @return List<VlsAlgorithmOrchestrationExcel>
	 */
	List<VlsAlgorithmOrchestrationExcel> exportVlsAlgorithmOrchestration(@Param("ew") Wrapper<AlgorithmOrchestration> queryWrapper);

}
