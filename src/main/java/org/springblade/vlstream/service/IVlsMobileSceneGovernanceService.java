package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.vlstream.pojo.entity.MobileSceneGovernance;
import org.springblade.vlstream.pojo.vo.MobileSceneGovernanceLoopVO;

import java.util.List;

/**
 * Mobile terminal scene management main task list Service category
 */
public interface IVlsMobileSceneGovernanceService extends BaseService<MobileSceneGovernance> {

	/**
	 * Add real-time management
	 */
	boolean saveImmediate(MobileSceneGovernance mobileSceneGovernance);

	/**
	 * Added cycle management, and generate sub-loop tasks
	 */
	boolean saveLoop(MobileSceneGovernance mobileSceneGovernance);

	/**
	 * Query real-time governance list
	 */
	IPage<MobileSceneGovernance> listImmediate(IPage<MobileSceneGovernance> page);

	/**
	 * Query cycle management list(Contains sub-loop tasks)
	 */
	IPage<MobileSceneGovernanceLoopVO> listLoop(IPage<MobileSceneGovernance> page);
}
