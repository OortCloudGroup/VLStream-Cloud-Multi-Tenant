package org.springblade.modules.system.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * CheckedTreeVO
 *
 * @author Chill
 */
@Data
public class CheckedTreeVO {

	private List<String> menu;

	private List<String> dataScope;

	private List<String> apiScope;

}
