package org.springblade.vlstream.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.vlstream.pojo.entity.ResourceSpec;
import org.springblade.vlstream.pojo.vo.ResourceSpecVO;

import java.util.Objects;

/**
 * Resource specification configuration table Packaging
 */
public class VlsResourceSpecWrapper extends BaseEntityWrapper<ResourceSpec, ResourceSpecVO> {

	public static VlsResourceSpecWrapper build() {
		return new VlsResourceSpecWrapper();
	}

	@Override
	public ResourceSpecVO entityVO(ResourceSpec resourceSpec) {
		if (resourceSpec == null) {
			return null;
		}
		return BeanUtil.copyProperties(resourceSpec, ResourceSpecVO.class);
	}
}
