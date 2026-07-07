package org.springblade.job.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.job.pojo.entity.JobInfo;

import java.io.Serial;

/**
 * 任务信息表 视图实体类
 *
 * @author Oort
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JobInfoVO extends JobInfo {
	@Serial
	private static final long serialVersionUID = 1L;

}
