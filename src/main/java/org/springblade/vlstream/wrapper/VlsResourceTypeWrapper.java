package org.springblade.vlstream.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.vlstream.pojo.entity.ResourceType;
import org.springblade.vlstream.pojo.vo.ResourceTypeVO;

import java.util.Objects;

/**
 * Resource type configuration table Packaging
 */
public class VlsResourceTypeWrapper extends BaseEntityWrapper<ResourceType, ResourceTypeVO> {

	public static VlsResourceTypeWrapper build() {
		return new VlsResourceTypeWrapper();
	}

	@Override
	public ResourceTypeVO entityVO(ResourceType resourceType) {
		if (resourceType == null) {
			return null;
		}
		return BeanUtil.copyProperties(resourceType, ResourceTypeVO.class);
	}
}
