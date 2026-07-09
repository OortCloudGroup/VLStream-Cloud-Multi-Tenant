package org.springblade.vlstream.mapper;

import org.apache.ibatis.annotations.Select;
import org.springblade.vlstream.pojo.entity.SceneGovernance;
import org.springblade.vlstream.pojo.vo.SceneGovernanceVO;
import org.springblade.vlstream.excel.VlsSceneGovernanceExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Scenario management table Mapper interface
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsSceneGovernanceMapper extends BaseMapper<SceneGovernance> {

	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsSceneGovernance query parameters
	 * @return List<VlsSceneGovernanceVO>
	 */
	List<SceneGovernanceVO> selectVlsSceneGovernancePage(IPage page, SceneGovernanceVO vlsSceneGovernance);

	/**
	 * Get export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsSceneGovernanceExcel>
	 */
	List<VlsSceneGovernanceExcel> exportVlsSceneGovernance(@Param("ew") Wrapper<SceneGovernance> queryWrapper);

	/**
	 * Query scene management information based on name
	 *
	 * @param name scene name
	 * @return Scene management information
	 */
	@Select("SELECT * FROM vls_scene_governance WHERE name = #{name} AND is_deleted = 0")
	SceneGovernance selectByName(@Param("name") String name);

	/**
	 * Query the scene governance list based on status
	 *
	 * @param status Scene status
	 * @return Scenario governance list
	 */
	@Select("SELECT * FROM vls_scene_governance WHERE status = #{status} AND is_deleted = 0 ORDER BY created_at DESC")
	List<SceneGovernance> selectByStatus(@Param("status") String status);

	/**
	 * Get the total number of scene management
	 *
	 * @return total
	 */
	@Select("SELECT COUNT(*) FROM vls_scene_governance WHERE is_deleted = 0")
	Long getTotalCount();

	/**
	 * Get the number of enabled scene governance
	 *
	 * @return Enable quantity
	 */
	@Select("SELECT COUNT(*) FROM vls_scene_governance WHERE status = 'enabled' AND is_deleted = 0")
	Long getEnabledCount();

	/**
	 * Get the number of disabled scene governance
	 *
	 * @return Banned quantity
	 */
	@Select("SELECT COUNT(*) FROM vls_scene_governance WHERE status = 'disabled' AND is_deleted = 0")
	Long getDisabledCount();

}
