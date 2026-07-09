package org.springblade.vlstream.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springblade.vlstream.pojo.entity.DeviceInfo;
import org.springblade.vlstream.pojo.vo.DeviceInfoVO;
import org.springblade.vlstream.excel.VlsDeviceInfoExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Equipment information table Mapper interface
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsDeviceInfoMapper extends BaseMapper<DeviceInfo> {

	/**
	 * Custom paging
	 *
	 * @param page          Paging parameters
	 * @param vlsDeviceInfo query parameters
	 * @return List<VlsDeviceInfoVO>
	 */
	List<DeviceInfoVO> selectVlsDeviceInfoPage(IPage page, DeviceInfoVO vlsDeviceInfo);

	/**
	 * Get export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsDeviceInfoExcel>
	 */
	List<VlsDeviceInfoExcel> exportVlsDeviceInfo(@Param("ew") Wrapper<DeviceInfo> queryWrapper);

	/**
	 * Query device information by page
	 *
	 * @param page       Pagination object
	 * @param deviceName device name or deviceID
	 * @param tag        device tag(Actual correspondencedevice_typeField)
	 * @param status     Device status
	 * @return Device information paginated list
	 */
	@Select("<script>" +
		"SELECT * FROM vls_device_info " +
		"WHERE is_deleted = 0 " +
		"<if test='deviceName != null and deviceName != \"\"'>" +
		"AND (device_name LIKE CONCAT('%', #{deviceName}, '%') OR device_id LIKE CONCAT('%', #{deviceName}, '%')) " +
		"</if>" +
		"<if test='tag != null and tag != \"\"'>" +
		"AND device_type = #{tag} " +
		"</if>" +
		"<if test='status != null and status != \"\"'>" +
		"AND status = #{status} " +
		"</if>" +
		"ORDER BY id DESC" +
		"</script>")
	IPage<DeviceInfo> selectDevicePage(Page<DeviceInfo> page,
									   @Param("deviceName") String deviceName,
									   @Param("tag") String tag,
									   @Param("status") String status);

	/**
	 * Query device information based on device number
	 *
	 * @param deviceId Device number
	 * @return Device information
	 */
	@Select("SELECT * FROM vls_device_info WHERE device_id = #{deviceId} AND is_deleted = 0")
	DeviceInfo selectByDeviceId(@Param("deviceId") String deviceId);

	/**
	 * Query device list based on status
	 *
	 * @param status Device status
	 * @return Device list
	 */
	@Select("SELECT * FROM vls_device_info WHERE status = #{status} AND is_deleted = 0")
	List<DeviceInfo> selectByStatus(@Param("status") String status);

	/**
	 * Query the device list based on device type
	 *
	 * @param deviceType Device type
	 * @return Device list
	 */
	@Select("SELECT * FROM vls_device_info WHERE device_type = #{deviceType} AND is_deleted = 0")
	List<DeviceInfo> selectByDeviceType(@Param("deviceType") String deviceType);

	/**
	 * Query device list based on label(actual querydevice_typeField)
	 *
	 * @param tag Label
	 * @return Device list
	 */
	@Select("SELECT * FROM vls_device_info WHERE device_type = #{tag} AND is_deleted = 0")
	List<DeviceInfo> selectByTag(@Param("tag") String tag);

	/**
	 * Update device status in batches
	 *
	 * @param deviceIds equipmentIDlist
	 * @param status    state
	 * @return Update quantity
	 */
	@Update("<script>" +
		"UPDATE vls_device_info SET status = #{status}, update_time = NOW() " +
		"WHERE id IN " +
		"<foreach collection='deviceIds' item='id' open='(' separator=',' close=')'>" +
		"#{id}" +
		"</foreach>" +
		"</script>")
	int updateStatusBatch(@Param("deviceIds") List<Long> deviceIds,
						  @Param("status") String status);

	/**
	 * Get device status statistics
	 *
	 * @return Statistical results
	 */
	@Select("SELECT status, COUNT(*) as count FROM vls_device_info WHERE is_deleted = 0 GROUP BY status")
	List<StatusStatistics> getStatusStatistics();

	/**
	 * Get device type statistics
	 *
	 * @return Statistical results
	 */
	@Select("SELECT device_type as type, COUNT(*) as count FROM vls_device_info WHERE is_deleted = 0 GROUP BY device_type")
	List<TypeStatistics> getTypeStatistics();

	/**
	 * Get equipment brand statistics
	 *
	 * @return Statistical results
	 */
	@Select("SELECT brand, COUNT(*) as count FROM vls_device_info WHERE is_deleted = 0 GROUP BY brand")
	List<BrandStatistics> getBrandStatistics();

	/**
	 * Get a list of all device types(for tag list)
	 *
	 * @return Device type list
	 */
	@Select("SELECT DISTINCT device_type FROM vls_device_info WHERE device_type IS NOT NULL AND device_type != '' AND is_deleted = 0")
	List<String> getAllTags();

	/**
	 * Get a list of all device brands
	 *
	 * @return Brand list
	 */
	@Select("SELECT DISTINCT brand FROM vls_device_info WHERE brand IS NOT NULL AND brand != '' AND is_deleted = 0")
	List<String> getAllBrands();

	/**
	 * according toIPAddress query equipment
	 *
	 * @param ipAddress IPaddress
	 * @return Device list
	 */
	@Select("SELECT * FROM vls_device_info WHERE ip_address = #{ipAddress} AND is_deleted = 0")
	List<DeviceInfo> selectByIpAddress(@Param("ipAddress") String ipAddress);

	/**
	 * Query devices based on location
	 *
	 * @param position Location
	 * @return Device list
	 */
	@Select("SELECT * FROM vls_device_info WHERE position LIKE CONCAT('%', #{position}, '%') AND is_deleted = 0")
	List<DeviceInfo> selectByPosition(@Param("position") String position);

	/**
	 * Check if the device number exists
	 *
	 * @param deviceId Device number
	 * @return quantity
	 */
	@Select("SELECT COUNT(*) FROM vls_device_info WHERE device_id = #{deviceId} AND is_deleted = 0")
	int countByDeviceId(@Param("deviceId") String deviceId);

	/**
	 * Status statistics inner class
	 */
	@Data
	class StatusStatistics {
		private String status;
		private Long count;
	}

	/**
	 * Type statistics inner class
	 */
	@Data
	class TypeStatistics {
		private String type;
		private Long count;
	}

	/**
	 * Brand statistics internal class
	 */
	@Data
	class BrandStatistics {
		private String brand;
		private Long count;
	}

	/**
	 * Device statistics internal class
	 */
	@Data
	class DeviceStatistics {
		private Long totalCount;
		private Long onlineCount;
		private Long offlineCount;
		private Long faultCount;
	}

}
