package org.springblade.modules.system.wrapper;

import org.springblade.common.cache.UserCache;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.RecordData;
import org.springblade.modules.system.pojo.entity.User;
import org.springblade.modules.system.pojo.vo.RecordDataVO;
import java.util.Objects;

/**
 * 数据审计表 包装类,返回视图层所需的字段
 *
 * @author Oort
 */
public class RecordDataWrapper extends BaseEntityWrapper<RecordData, RecordDataVO>  {

	public static RecordDataWrapper build() {
		return new RecordDataWrapper();
 	}

	@Override
	public RecordDataVO entityVO(RecordData recordData) {
		RecordDataVO recordDataVO = Objects.requireNonNull(BeanUtil.copyProperties(recordData, RecordDataVO.class));

		User user = UserCache.getUser(recordData.getRecordUser());
		if (Func.isNotEmpty(user)) {
			recordDataVO.setRecordUserName(user.getAccount());
		}

		return recordDataVO;
	}

}
