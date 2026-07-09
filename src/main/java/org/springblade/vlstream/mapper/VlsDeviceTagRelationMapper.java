package org.springblade.vlstream.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springblade.vlstream.pojo.dto.DeviceTagRelationDTO;
import org.springblade.vlstream.pojo.entity.DeviceTagRelation;
import org.springblade.vlstream.pojo.vo.DeviceTagRelationVO;
import org.springblade.vlstream.excel.VlsDeviceTagRelationExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * Device tag association table Mapper interface
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsDeviceTagRelationMapper extends BaseMapper<DeviceTagRelation> {

	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsDeviceTagRelation query parameters
	 * @return List<VlsDeviceTagRelationVO>
	 */
	List<DeviceTagRelationVO> selectVlsDeviceTagRelationPage(IPage page, DeviceTagRelationVO vlsDeviceTagRelation);

	/**
	 * Get export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsDeviceTagRelationExcel>
	 */
	List<VlsDeviceTagRelationExcel> exportVlsDeviceTagRelation(@Param("ew") Wrapper<DeviceTagRelation> queryWrapper);

	/**
	 * According to deviceIDGet label information
	 *
	 * @param deviceId equipmentID
	 * @return Tag information list
	 */
	@Select("SELECT " +
		"dtr.id, dtr.device_id, dtr.tag_id, dtr.create_user, dtr.create_time, " +
		"tm.tag_name, tm.category_type, tm.tag_color, tm.tag_icon, tm.level, tm.parent_id " +
		"FROM vls_device_tag_relation dtr " +
		"INNER JOIN vls_tag_management tm ON dtr.tag_id = tm.id " +
		"WHERE dtr.device_id = #{deviceId} AND tm.is_deleted = 0 AND tm.is_active = 1 " +
		"ORDER BY tm.category_type, tm.sort_order")
	List<DeviceTagRelationDTO> selectTagsByDeviceId(@Param("deviceId") Long deviceId);

	/**
	 * According to labelIDGet device list
	 *
	 * @param tagId LabelID
	 * @return Device tag association list
	 */
	@Select("SELECT " +
		"dtr.id, dtr.device_id, dtr.tag_id, dtr.create_user, dtr.create_time, " +
		"di.device_name, di.device_id as device_code, di.status " +
		"FROM vls_device_tag_relation dtr " +
		"INNER JOIN vls_device_info di ON dtr.device_id = di.id " +
		"WHERE dtr.tag_id = #{tagId} AND di.is_deleted = 0 " +
		"ORDER BY di.device_name")
	List<Map<String, Object>> selectDevicesByTagId(@Param("tagId") Long tagId);

	/**
	 * Add device tag associations in batches
	 *
	 * @param deviceId equipmentID
	 * @param tagIds LabelIDlist
	 * @param createdBy Creator
	 * @return Insert quantity
	 */
	@Insert("<script>" +
		"INSERT INTO vls_device_tag_relation (device_id, tag_id, create_user) VALUES " +
		"<foreach collection='tagIds' item='tagId' separator=','>" +
		"(#{deviceId}, #{tagId}, #{createdBy})" +
		"</foreach>" +
		"</script>")
	int batchInsertDeviceTags(@Param("deviceId") Long deviceId,
							  @Param("tagIds") List<Long> tagIds,
							  @Param("createdBy") String createdBy);

	/**
	 * Remove all tag associations for a device
	 *
	 * @param deviceId equipmentID
	 * @return Delete quantity
	 */
	@Delete("DELETE FROM vls_device_tag_relation WHERE device_id = #{deviceId}")
	int deleteByDeviceId(@Param("deviceId") Long deviceId);

	/**
	 * Delete the specified label association of the device
	 *
	 * @param deviceId equipmentID
	 * @param tagIds LabelIDlist
	 * @return Delete quantity
	 */
	@Delete("<script>" +
		"DELETE FROM vls_device_tag_relation " +
		"WHERE device_id = #{deviceId} AND tag_id IN " +
		"<foreach collection='tagIds' item='tagId' open='(' separator=',' close=')'>" +
		"#{tagId}" +
		"</foreach>" +
		"</script>")
	int deleteDeviceTagsBatch(@Param("deviceId") Long deviceId, @Param("tagIds") List<Long> tagIds);

	/**
	 * Get device tag statistics
	 *
	 * @return Statistics
	 */
	@Select("SELECT " +
		"device_id, " +
		"COUNT(tag_id) as tag_count, " +
		"COUNT(CASE WHEN tm.category_type = 'own' THEN 1 END) as own_tag_count, " +
		"COUNT(CASE WHEN tm.category_type = 'public' THEN 1 END) as public_tag_count " +
		"FROM vls_device_tag_relation dtr " +
		"INNER JOIN vls_tag_management tm ON dtr.tag_id = tm.id " +
		"WHERE tm.is_deleted = 0 AND tm.is_active = 1 " +
		"GROUP BY device_id")
	List<Map<String, Object>> getDeviceTagStatistics();

	/**
	 * Get tag usage statistics
	 *
	 * @return Tag usage statistics
	 */
	@Select("SELECT " +
		"tm.id as tag_id, " +
		"tm.tag_name, " +
		"tm.category_type, " +
		"tm.level, " +
		"tm.tag_color, " +
		"COUNT(dtr.device_id) as device_count " +
		"FROM vls_tag_management tm " +
		"LEFT JOIN vls_device_tag_relation dtr ON tm.id = dtr.tag_id " +
		"WHERE tm.is_deleted = 0 AND tm.is_active = 1 " +
		"GROUP BY tm.id, tm.tag_name, tm.category_type, tm.level, tm.tag_color " +
		"ORDER BY tm.category_type, tm.level, device_count DESC")
	List<Map<String, Object>> getTagUsageStatistics();

	/**
	 * Query devices based on multiple tags(intersection)
	 *
	 * @param tagIds LabelIDlist
	 * @return equipmentIDlist
	 */
	@Select("<script>" +
		"SELECT device_id " +
		"FROM vls_device_tag_relation " +
		"WHERE tag_id IN " +
		"<foreach collection='tagIds' item='tagId' open='(' separator=',' close=')'>" +
		"#{tagId}" +
		"</foreach>" +
		"GROUP BY device_id " +
		"HAVING COUNT(DISTINCT tag_id) = #{tagIds.size}" +
		"</script>")
	List<Long> findDevicesByAllTags(@Param("tagIds") List<Long> tagIds);

	/**
	 * Query devices based on multiple tags(union)
	 *
	 * @param tagIds LabelIDlist
	 * @return equipmentIDlist
	 */
	@Select("<script>" +
		"SELECT DISTINCT device_id " +
		"FROM vls_device_tag_relation " +
		"WHERE tag_id IN " +
		"<foreach collection='tagIds' item='tagId' open='(' separator=',' close=')'>" +
		"#{tagId}" +
		"</foreach>" +
		"</script>")
	List<Long> findDevicesByAnyTags(@Param("tagIds") List<Long> tagIds);

	/**
	 * Check if the device already has the specified tag
	 *
	 * @param deviceId equipmentID
	 * @param tagId LabelID
	 * @return quantity
	 */
	@Select("SELECT COUNT(*) FROM vls_device_tag_relation WHERE device_id = #{deviceId} AND tag_id = #{tagId}")
	int checkDeviceTagExists(@Param("deviceId") Long deviceId, @Param("tagId") Long tagId);

	/**
	 * Get the label of the deviceIDlist
	 *
	 * @param deviceId equipmentID
	 * @return LabelIDlist
	 */
	@Select("SELECT tag_id FROM vls_device_tag_relation WHERE device_id = #{deviceId}")
	List<Long> selectTagIdsByDeviceId(@Param("deviceId") Long deviceId);

	/**
	 * Get the number of devices using a certain tag
	 *
	 * @param tagId LabelID
	 * @return Number of devices
	 */
	@Select("SELECT COUNT(*) FROM vls_device_tag_relation WHERE tag_id = #{tagId}")
	int countDevicesByTagId(@Param("tagId") Long tagId);

	/**
	 * According to labelIDRemove all related device tag associations
	 *
	 * @param tagId LabelID
	 * @return Delete quantity
	 */
	@Delete("DELETE FROM vls_device_tag_relation WHERE tag_id = #{tagId}")
	int deleteByTagId(@Param("tagId") Long tagId);

	/**
	 * According to labelIDGet device tag association list(used forTagManagementServiceImplcompatible)
	 *
	 * @param tagId LabelID
	 * @return Device tag association list
	 */
	@Select("SELECT " +
		"dtr.id, dtr.device_id, dtr.tag_id, dtr.create_user, dtr.create_time " +
		"FROM vls_device_tag_relation dtr " +
		"WHERE dtr.tag_id = #{tagId}")
	List<DeviceTagRelation> selectByTagId(@Param("tagId") Long tagId);

}
