package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.vlstream.excel.VlsRemoteServersExcel;
import org.springblade.vlstream.pojo.entity.RemoteServers;
import org.springblade.vlstream.pojo.vo.RemoteServersVO;

import java.util.List;

/**
 * Remote server configuration table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsRemoteServersService extends BaseService<RemoteServers> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsRemoteServers query parameters
	 * @return IPage<VlsRemoteServersVO>
	 */
	IPage<RemoteServersVO> selectVlsRemoteServersPage(IPage<RemoteServersVO> page, RemoteServersVO vlsRemoteServers);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsRemoteServersExcel>
	 */
	List<VlsRemoteServersExcel> exportVlsRemoteServers(Wrapper<RemoteServers> queryWrapper);

	/**
	 * Query remote server configuration
	 *
	 * @param id Remote server configuration primary key
	 * @return Remote server configuration
	 */
	public RemoteServers selectRemoteServerById(Long id);

	/**
	 * Query the remote server configuration list
	 *
	 * @param remoteServer Remote server configuration
	 * @return Remote server configuration collection
	 */
	public List<RemoteServers> selectRemoteServerList(RemoteServers remoteServer);

	/**
	 * Add remote server configuration
	 *
	 * @param remoteServer Remote server configuration
	 * @return result
	 */
	public int insertRemoteServer(RemoteServers remoteServer);

	/**
	 * Modify remote server configuration
	 *
	 * @param remoteServer Remote server configuration
	 * @return result
	 */
	public int updateRemoteServer(RemoteServers remoteServer);

	/**
	 * Delete remote server configurations in batches
	 *
	 * @param ids Remote server configuration primary key set that needs to be deleted
	 * @return result
	 */
	public int deleteRemoteServerByIds(Long[] ids);

	/**
	 * Delete remote server configuration information
	 *
	 * @param id Remote server configuration primary key
	 * @return result
	 */
	public int deleteRemoteServerById(Long id);

}
