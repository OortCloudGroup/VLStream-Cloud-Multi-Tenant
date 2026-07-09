package org.springblade.vlstream.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.enums.YesNoEnum;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.vlstream.enums.AlgorithmRepositoryTypeEnum;
import org.springblade.vlstream.excel.VlsAlgorithmRepositoryExcel;
import org.springblade.vlstream.mapper.VlsAlgorithmMapper;
import org.springblade.vlstream.mapper.VlsAlgorithmRepositoryMapper;
import org.springblade.vlstream.pojo.entity.AlgorithmRepository;
import org.springblade.vlstream.pojo.vo.AlgorithmRepositoryVO;
import org.springblade.vlstream.service.IVlsAlgorithmRepositoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Algorithm warehouse table Service implementation class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VlsAlgorithmRepositoryServiceImpl extends BaseServiceImpl<VlsAlgorithmRepositoryMapper, AlgorithmRepository> implements IVlsAlgorithmRepositoryService {

	private final VlsAlgorithmRepositoryMapper algorithmRepositoryMapper;
	private final VlsAlgorithmMapper algorithmMapper;

	@Override
	public IPage<AlgorithmRepositoryVO> selectVlsAlgorithmRepositoryPage(IPage<AlgorithmRepositoryVO> page, AlgorithmRepositoryVO vlsAlgorithmRepository) {
		return page.setRecords(baseMapper.selectVlsAlgorithmRepositoryPage(page, vlsAlgorithmRepository));
	}

	@Override
	public List<VlsAlgorithmRepositoryExcel> exportVlsAlgorithmRepository(Wrapper<AlgorithmRepository> queryWrapper) {
		List<VlsAlgorithmRepositoryExcel> vlsAlgorithmRepositoryList = baseMapper.exportVlsAlgorithmRepository(queryWrapper);
		//vlsAlgorithmRepositoryList.forEach(vlsAlgorithmRepository -> {
		//	vlsAlgorithmRepository.setTypeName(DictCache.getValue(DictEnum.YES_NO, VlsAlgorithmRepositoryEntity.getType()));
		//});
		return vlsAlgorithmRepositoryList;
	}

	@Override
	public IPage<AlgorithmRepository> selectRepositoryPage(Page<AlgorithmRepository> page,
														   String name,
														   String repositoryType,
														   String status) {
		log.info("Paging query algorithm warehouse list, parameter: name={}, repositoryType={}, status={}", name, repositoryType, status);
		return algorithmRepositoryMapper.selectRepositoryPage(page, name, repositoryType, status);
	}

	@Override
	public List<AlgorithmRepository> getEnabledRepositories() {
		log.info("Query所有enablealgorithmstorehouse");
		return algorithmRepositoryMapper.selectEnabledRepositories();
	}

	@Override
	public List<AlgorithmRepository> getByRepositoryType(String repositoryType) {
		log.info("Query algorithm warehouse based on type: {}", repositoryType);
		return algorithmRepositoryMapper.selectByRepositoryType(repositoryType);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean createRepository(AlgorithmRepository repository) {
		log.info("Create algorithm warehouse: {}", repository.getName());

		// Check if names are duplicates
		QueryWrapper<AlgorithmRepository> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", repository.getName());
		if (count(queryWrapper) > 0) {
			log.warn("Algorithm warehouse name already exists: {}", repository.getName());
			return false;
		}

		// Set default value
		if (repository.getAlgorithmCount() == null) {
			repository.setAlgorithmCount(0);
		}
		if (repository.getRepositoryType() == null) {
			repository.setRepositoryType(AlgorithmRepositoryTypeEnum.extended);
		}
		if (repository.getStatus() == null) {
			repository.setStatus(YesNoEnum.YES.getCode());
		}

		return save(repository);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateRepository(AlgorithmRepository repository) {
		log.info("Update algorithm repository: ID={}, Name={}", repository.getId(), repository.getName());

		// Check whether the algorithm library is preset for the base(Modification of some fields is not allowed)
		AlgorithmRepository existing = getById(repository.getId());
		if (existing != null && "basic".equals(existing.getRepositoryType())) {
			// The basic preset algorithm library only allows modification of comments and status
			repository.setName(existing.getName());
			repository.setRepositoryType(existing.getRepositoryType());
		}

		return updateById(repository);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteRepository(Long id) {
		log.info("Delete algorithm repository: ID={}", id);

		// Check whether the algorithm library is preset for the base(Delete not allowed)
		AlgorithmRepository repository = getById(id);
		if (repository != null && "basic".equals(repository.getRepositoryType())) {
			log.warn("Deletion of basic preset algorithm libraries is not allowed: ID={}", id);
			return false;
		}

		return removeById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean batchDeleteRepositories(List<Long> ids) {
		log.info("Batch deletion of algorithm warehouse: IDs={}", ids);

		// Filter out the basic preset algorithm library
		List<AlgorithmRepository> repositories = listByIds(ids);
		List<Long> allowedIds = repositories.stream()
			.filter(repo -> !"basic".equals(repo.getRepositoryType()))
			.map(AlgorithmRepository::getId)
			.collect(Collectors.toList());

		if (allowedIds.isEmpty()) {
			log.warn("There is no algorithm repository to delete");
			return false;
		}

		return removeByIds(allowedIds);
	}

	@Override
	public boolean updateRepositoryStatus(Long id, String status) {
		log.info("Update algorithm warehouse status: ID={}, Status={}", id, status);

		UpdateWrapper<AlgorithmRepository> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", id)
			.set("status", status);

		return update(updateWrapper);
	}

	@Override
	public boolean batchUpdateRepositoryStatus(List<Long> ids, String status) {
		log.info("Batch update algorithm warehouse status: IDs={}, Status={}", ids, status);

		UpdateWrapper<AlgorithmRepository> updateWrapper = new UpdateWrapper<>();
		updateWrapper.in("id", ids)
			.set("status", status);

		return update(updateWrapper);
	}

	@Override
	public Long countRepositories() {
		log.info("Statistical algorithm warehouse quantity");
		return algorithmRepositoryMapper.countRepositories();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateAlgorithmCount(Long repositoryId) {
		log.info("Update the number of algorithms in the algorithm warehouse: ID={}", repositoryId);

		Long count = algorithmMapper.countByRepositoryId(repositoryId);

		UpdateWrapper<AlgorithmRepository> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", repositoryId)
			.set("algorithm_count", count);

		update(updateWrapper);
	}

}
