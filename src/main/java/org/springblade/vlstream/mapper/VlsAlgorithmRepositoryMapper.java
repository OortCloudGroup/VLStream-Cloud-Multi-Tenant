package org.springblade.vlstream.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Select;
import org.springblade.vlstream.pojo.entity.AlgorithmRepository;
import org.springblade.vlstream.pojo.vo.AlgorithmRepositoryVO;
import org.springblade.vlstream.excel.VlsAlgorithmRepositoryExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Algorithm warehouse table Mapper interface
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsAlgorithmRepositoryMapper extends BaseMapper<AlgorithmRepository> {

	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAlgorithmRepository query parameters
	 * @return List<VlsAlgorithmRepositoryVO>
	 */
	List<AlgorithmRepositoryVO> selectVlsAlgorithmRepositoryPage(IPage page, AlgorithmRepositoryVO vlsAlgorithmRepository);

	/**
	 * Get export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAlgorithmRepositoryExcel>
	 */
	List<VlsAlgorithmRepositoryExcel> exportVlsAlgorithmRepository(@Param("ew") Wrapper<AlgorithmRepository> queryWrapper);

	/**
	 * Paging query algorithm warehouse list
	 */
	@Select("SELECT r.*, " +
		"(SELECT COUNT(*) FROM algorithm a WHERE a.repository_id = r.id AND a.is_deleted = 0) as algorithm_count " +
		"FROM vls_algorithm_repository r " +
		"WHERE r.is_deleted = 0 " +
		"AND (#{name} IS NULL OR r.name LIKE CONCAT('%', #{name}, '%')) " +
		"AND (#{repositoryType} IS NULL OR r.repository_type = #{repositoryType}) " +
		"AND (#{status} IS NULL OR r.status = #{status}) " +
		"ORDER BY r.id ASC")
	IPage<AlgorithmRepository> selectRepositoryPage(Page<AlgorithmRepository> page,
													@Param("name") String name,
													@Param("repositoryType") String repositoryType,
													@Param("status") String status);

	/**
	 * Query所有enablealgorithmstorehouse
	 */
	@Select("SELECT * FROM vls_algorithm_repository WHERE is_deleted = 0 AND status = 'enabled' ORDER BY id")
	List<AlgorithmRepository> selectEnabledRepositories();

	/**
	 * Query algorithm warehouse based on type
	 */
	@Select("SELECT * FROM vls_algorithm_repository WHERE is_deleted = 0 AND repository_type = #{repositoryType} ORDER BY id")
	List<AlgorithmRepository> selectByRepositoryType(@Param("repositoryType") String repositoryType);

	/**
	 * Statistical algorithm warehouse quantity
	 */
	@Select("SELECT COUNT(*) FROM vls_algorithm_repository WHERE is_deleted = 0")
	Long countRepositories();

}
