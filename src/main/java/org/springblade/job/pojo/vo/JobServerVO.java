package org.springblade.job.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.job.pojo.entity.JobServer;

import java.io.Serial;

/**
 * task service table View entity class
 *
 * @author Oort
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JobServerVO extends JobServer {
	@Serial
	private static final long serialVersionUID = 1L;

}
