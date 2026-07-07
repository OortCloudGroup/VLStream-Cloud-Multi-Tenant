package org.springblade.modules.system.pojo.vo;

import lombok.Data;
import org.springblade.core.tool.node.TreeNode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * GrantTreeVO
 *
 * @author Chill
 */
@Data
public class GrantTreeVO implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private List<TreeNode> menu;

	private List<TreeNode> dataScope;

	private List<TreeNode> apiScope;

}
