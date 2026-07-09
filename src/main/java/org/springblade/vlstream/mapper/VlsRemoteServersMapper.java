package org.springblade.vlstream.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.vlstream.excel.VlsRemoteServersExcel;
import org.springblade.vlstream.pojo.entity.RemoteServers;
import org.springblade.vlstream.pojo.vo.RemoteServersVO;

import java.util.List;

/**
 * Remote server configuration table Mapper interface
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsRemoteServersMapper extends BaseMapper<RemoteServers> {

	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsRemoteServers query parameters
	 * @return List<VlsRemoteServersVO>
	 */
	List<RemoteServersVO> selectVlsRemoteServersPage(IPage page, RemoteServersVO vlsRemoteServers);

	/**
	 * Get export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsRemoteServersExcel>
	 */
	List<VlsRemoteServersExcel> exportVlsRemoteServers(@Param("ew") Wrapper<RemoteServers> queryWrapper);

	/**
	 * Query the remote server configuration list
	 */
	List<RemoteServers> selectRemoteServerList(RemoteServers remoteServer);

	/**
	 * Query remote server configuration details
	 */
	RemoteServers selectRemoteServerById(Long id);

	/**
	 * Add remote server configuration
	 */
	int insertRemoteServer(RemoteServers remoteServer);

	/**
	 * Modify remote server configuration
	 */
	int updateRemoteServer(RemoteServers remoteServer);

	/**
	 * Delete remote server configuration
	 */
	int deleteRemoteServerById(Long id);

	/**
	 * Delete remote server configurations in batches
	 */
	int deleteRemoteServerByIds(Long[] ids);

	/**
	 * Query enabled server configuration
	 */
	RemoteServers selectActiveServer();

	/**
	 * Count the number of servers
	 */
	int count();

	/**
	 * Create table(if does not exist)
	 */
	void createTableIfNotExists();

}
