package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.springblade.vlstream.pojo.entity.DeviceInfo;
import org.springblade.vlstream.pojo.vo.DeviceInfoVO;
import org.springblade.vlstream.excel.VlsDeviceInfoExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * Equipment information table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsDeviceInfoService extends BaseService<DeviceInfo> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsDeviceInfo query parameters
	 * @return IPage<VlsDeviceInfoVO>
	 */
	IPage<DeviceInfoVO> selectVlsDeviceInfoPage(IPage<DeviceInfoVO> page, DeviceInfoVO vlsDeviceInfo);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsDeviceInfoExcel>
	 */
	List<VlsDeviceInfoExcel> exportVlsDeviceInfo(Wrapper<DeviceInfo> queryWrapper);

	/**
	 * Query device information by page
	 *
	 * @param page       Pagination object
	 * @param deviceName device name or deviceID
	 * @param tag        device tag(Actual correspondencedevice_typeField)
	 * @param status     Device status
	 * @return Device information paginated list
	 */
	IPage<DeviceInfo> getDevicePage(Page<DeviceInfo> page,
									String deviceName,
									String tag,
									String status);

	/**
	 * Query device information based on device number
	 *
	 * @param deviceId Device number
	 * @return Device information
	 */
	DeviceInfo getByDeviceId(String deviceId);

	/**
	 * Add device information
	 *
	 * @param deviceInfo Device information
	 * @return Is it successful?
	 */
	boolean addDevice(DeviceInfo deviceInfo);

	/**
	 * Update device information
	 *
	 * @param deviceInfo Device information
	 * @return Is it successful?
	 */
	boolean updateDevice(DeviceInfo deviceInfo);

	/**
	 * Dispatch comma separated algorithm ids to a device.
	 *
	 * @param algorithmId
	 * @param deviceIds
	 * @return true if update succeeded
	 */
	boolean dispatchAlgorithms(Long algorithmId, String deviceIds);

	/**
	 * Delete device information
	 *
	 * @param id equipmentID
	 * @return Is it successful?
	 */
	boolean deleteDevice(Long id);

	/**
	 * Delete device information in batches
	 *
	 * @param ids equipmentIDlist
	 * @return Is it successful?
	 */
	boolean deleteDeviceBatch(List<Long> ids);

	/**
	 * Update device status
	 *
	 * @param id     equipmentID
	 * @param status state
	 * @return Is it successful?
	 */
	boolean updateDeviceStatus(Long id, Integer status);

	/**
	 * Update device status in batches
	 *
	 * @param ids    equipmentIDlist
	 * @param status state
	 * @return Is it successful?
	 */
	boolean updateDeviceStatusBatch(List<Long> ids, String status);

	/**
	 * Get device list based on status
	 *
	 * @param status Device status
	 * @return Device list
	 */
	List<DeviceInfo> getDevicesByStatus(String status);

	/**
	 * Get device list based on device type
	 *
	 * @param deviceType Device type
	 * @return Device list
	 */
	List<DeviceInfo> getDevicesByType(String deviceType);

	/**
	 * Get list of devices based on location
	 *
	 * @param position Device location
	 * @return Device list
	 */
	List<DeviceInfo> getDevicesByPosition(String position);

	/**
	 * Check if the device number exists
	 *
	 * @param deviceId Device number
	 * @return exists
	 */
	boolean checkDeviceIdExists(String deviceId);

	/**
	 * Test device connections
	 *
	 * @param id equipmentID
	 * @return Connection result
	 */
	Map<String, Object> testDeviceConnection(Long id);

	/**
	 * Get device statistics
	 *
	 * @return Statistics
	 */
	Map<String, Object> getDeviceStatistics();

	/**
	 * Get a list of all device types(for tag list)
	 *
	 * @return Device type list
	 */
	List<String> getAllTags();

	/**
	 * Get a list of all device brands
	 *
	 * @return Brand list
	 */
	List<String> getAllBrands();

	/**
	 * Verify device configuration
	 *
	 * @param deviceInfo Device information
	 * @return Verification results
	 */
	Map<String, Object> validateDevice(DeviceInfo deviceInfo);

	/**
	 * Refresh device status
	 *
	 * @param deviceId equipmentID
	 * @return refresh results
	 */
	Map<String, Object> refreshDeviceStatus(Long deviceId);

	/**
	 * Import devices in batches
	 *
	 * @param deviceList Device list
	 * @return Import results
	 */
	Map<String, Object> batchImportDevices(List<DeviceInfo> deviceList);

	/**
	 * Export device information
	 *
	 * @param deviceIds equipmentIDlist, Export all devices when empty
	 * @return Export data
	 */
	List<DeviceInfo> exportDevices(List<Long> deviceIds);

	/**
	 * Get device configuration parameters
	 *
	 * @param deviceId equipmentID
	 * @return Configuration parameters
	 */
	Map<String, Object> getDeviceConfig(Long deviceId);

	/**
	 * Update device configuration parameters
	 *
	 * @param deviceId equipmentID
	 * @param config Configuration parameters
	 * @return Is it successful?
	 */
	boolean updateDeviceConfig(Long deviceId, Map<String, Object> config);

	/**
	 * PTZcontrol
	 *
	 * @param deviceId equipmentID
	 * @param command PTZOrder
	 * @param params parameter
	 * @return control results
	 */
	Map<String, Object> ptzControl(Long deviceId, String command, Map<String, Object> params);

	/**
	 * Get device video stream information
	 *
	 * @param deviceId equipmentID
	 * @return Video streaming information
	 */
	Map<String, Object> getVideoStreamInfo(Long deviceId);

	/**
	 * Device statistics
	 */
	@Data
	class DeviceStatistics {
		private Long totalCount;
		private Long onlineCount;
		private Long offlineCount;
		private Long faultCount;
	}


}
