package org.springblade.modules.system.wrapper;

import org.springblade.common.cache.RegionCache;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.pojo.entity.Region;
import org.springblade.modules.system.pojo.vo.RegionVO;

import java.util.List;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class RegionWrapper extends BaseEntityWrapper<Region, RegionVO> {

	public static RegionWrapper build() {
		return new RegionWrapper();
	}

	@Override
	public RegionVO entityVO(Region region) {
		RegionVO regionVO = Objects.requireNonNull(BeanUtil.copyProperties(region, RegionVO.class));
		Region parentRegion = RegionCache.getByCode(region.getParentCode());
		regionVO.setParentName(parentRegion.getName());
		return regionVO;
	}

	public List<RegionVO> listNodeLazyVO(List<RegionVO> list) {
		return ForestNodeMerger.merge(list);
	}

}
