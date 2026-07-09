package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springblade.vlstream.pojo.dto.DeviceTagRelationDTO;
import org.springblade.vlstream.pojo.entity.DeviceTagRelation;
import org.springblade.vlstream.pojo.vo.DeviceTagRelationVO;
import org.springblade.vlstream.excel.VlsDeviceTagRelationExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * Device tag association table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsDeviceTagRelationService extends BaseService<DeviceTagRelation> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsDeviceTagRelation query parameters
	 * @return IPage<VlsDeviceTagRelationVO>
	 */
	IPage<DeviceTagRelationVO> selectVlsDeviceTagRelationPage(IPage<DeviceTagRelationVO> page, DeviceTagRelationVO vlsDeviceTagRelation);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsDeviceTagRelationExcel>
	 */
	List<VlsDeviceTagRelationExcel> exportVlsDeviceTagRelation(Wrapper<DeviceTagRelation> queryWrapper);

	/**
	 * Set device label(Overwrite original label)
	 *
	 * @param deviceId equipmentID
	 * @param tagIds LabelIDlist
	 * @param createdBy Creator
	 * @return Is it successful?
	 */
	boolean setDeviceTags(Long deviceId, List<Long> tagIds, String createdBy);

	/**
	 * Add device label(Append to existing tag)
	 *
	 * @param deviceId equipmentID
	 * @param tagIds LabelIDlist
	 * @param createdBy Creator
	 * @return Is it successful?
	 */
	boolean addDeviceTags(Long deviceId, List<Long> tagIds, String createdBy);

	/**
	 * Remove device label
	 *
	 * @param deviceId equipmentID
	 * @param tagIds LabelIDlist
	 * @return Is it successful?
	 */
	boolean removeDeviceTags(Long deviceId, List<Long> tagIds);

	/**
	 * Clear all tags from device
	 *
	 * @param deviceId equipmentID
	 * @return Is it successful?
	 */
	boolean clearDeviceTags(Long deviceId);

	/**
	 * Get all tags of the device
	 *
	 * @param deviceId equipmentID
	 * @return Tag information list
	 */
	List<DeviceTagRelationDTO> getDeviceTags(Long deviceId);

	/**
	 * Get the label of the deviceIDlist
	 *
	 * @param deviceId equipmentID
	 * @return LabelIDlist
	 */
	List<Long> getDeviceTagIds(Long deviceId);

	/**
	 * Get a list of devices with a specified label
	 *
	 * @param tagId LabelID
	 * @return Device information list
	 */
	List<Map<String, Object>> getDevicesByTag(Long tagId);

	/**
	 * Query devices based on multiple tags(intersection - All tags must be included)
	 *
	 * @param tagIds LabelIDlist
	 * @return equipmentIDlist
	 */
	List<Long> findDevicesByAllTags(List<Long> tagIds);

	/**
	 * Query devices based on multiple tags(union - Contains any tag)
	 *
	 * @param tagIds LabelIDlist
	 * @return equipmentIDlist
	 */
	List<Long> findDevicesByAnyTags(List<Long> tagIds);

	/**
	 * Set device labels in batches
	 *
	 * @param deviceTagMap equipmentID -> LabelIDList mapping
	 * @param createdBy Creator
	 * @return Number of devices successfully provisioned
	 */
	int batchSetDeviceTags(Map<Long, List<Long>> deviceTagMap, String createdBy);

	/**
	 * Copy device labels to other devices
	 *
	 * @param sourceDeviceId source deviceID
	 * @param targetDeviceIds target deviceIDlist
	 * @param createdBy Creator
	 * @return Is it successful?
	 */
	boolean copyDeviceTags(Long sourceDeviceId, List<Long> targetDeviceIds, String createdBy);

	/**
	 * Get device tag statistics
	 *
	 * @return Statistics
	 */
	List<Map<String, Object>> getDeviceTagStatistics();

	/**
	 * Get tag usage statistics
	 *
	 * @return Tag usage statistics
	 */
	List<Map<String, Object>> getTagUsageStatistics();

	/**
	 * Check if the device has the specified label
	 *
	 * @param deviceId equipmentID
	 * @param tagId LabelID
	 * @return exists
	 */
	boolean hasDeviceTag(Long deviceId, Long tagId);

	/**
	 * Get the number of devices labeled
	 *
	 * @param tagId LabelID
	 * @return Number of devices
	 */
	int getTagDeviceCount(Long tagId);

	/**
	 * Verification tagIDIs the list valid?
	 *
	 * @param tagIds LabelIDlist
	 * @return Verification results
	 */
	Map<String, Object> validateTagIds(List<Long> tagIds);

	/**
	 * Get device tag details(Contains tag hierarchy)
	 *
	 * @param deviceId equipmentID
	 * @return Label details
	 */
	Map<String, Object> getDeviceTagDetails(Long deviceId);

	/**
	 * Get device list based on tag type
	 *
	 * @param categoryType Tag type(own/public)
	 * @param level Tag hierarchy(1/2)
	 * @return Device information
	 */
	List<Map<String, Object>> getDevicesByTagCategory(String categoryType, Integer level);

	/**
	 * Sync tag usage count
	 * renewtag_managementin the tableusage_countField
	 *
	 * @return Is it successful?
	 */
	boolean syncTagUsageCount();

}
