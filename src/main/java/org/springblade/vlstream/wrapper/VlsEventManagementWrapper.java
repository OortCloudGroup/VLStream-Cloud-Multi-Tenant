package org.springblade.vlstream.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.vlstream.pojo.entity.EventManagement;
import org.springblade.vlstream.pojo.vo.EventManagementVO;
import java.util.Objects;

/**
 * event management table Packaging,Returns the fields required by the view layer
 *
 * @author Oort
 * @since 2025-12-23
 */
public class VlsEventManagementWrapper extends BaseEntityWrapper<EventManagement, EventManagementVO>  {

	public static VlsEventManagementWrapper build() {
		return new VlsEventManagementWrapper();
 	}

	@Override
	public EventManagementVO entityVO(EventManagement vlsEventManagement) {
		EventManagementVO vlsEventManagementVO = Objects.requireNonNull(BeanUtil.copyProperties(vlsEventManagement, EventManagementVO.class));

		//User createUser = UserCache.getUser(vlsEventManagement.getCreateUser());
		//User updateUser = UserCache.getUser(vlsEventManagement.getUpdateUser());
		//vlsEventManagementVO.setCreateUserName(createUser.getName());
		//vlsEventManagementVO.setUpdateUserName(updateUser.getName());

		return vlsEventManagementVO;
	}

}
